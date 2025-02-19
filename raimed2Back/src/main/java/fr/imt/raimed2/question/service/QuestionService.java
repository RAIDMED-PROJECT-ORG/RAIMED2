package fr.imt.raimed2.question.service;

import com.ctc.wstx.util.StringUtil;
import fr.imt.raimed2.action.dto.xml.ActionDtoMapper;
import fr.imt.raimed2.question.dto.request.CreateQuestionDto;
import fr.imt.raimed2.question.dto.request.UpdateQuestionDto;
import fr.imt.raimed2.question.dto.xml.QuestionLinkedDTO;
import fr.imt.raimed2.question.dto.xml.QuestionLinkedMapper;
import fr.imt.raimed2.question.model.Filter;
import fr.imt.raimed2.question.model.Question;
import fr.imt.raimed2.question.model.QuestionType;
import fr.imt.raimed2.question.repository.QuestionRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionLinkedMapper questionLinkedMapper;
    private final ActionDtoMapper actionDtoMapper;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(QuestionService.class);

    public List<Question> getAllQuestion(@Nullable QuestionType questionType) {
        if (questionType == null) {
            return questionRepository.findAll();
        }
        return questionRepository.findAllByType(questionType);
    }

    /**
     * Get all the questions
     * @param questionType The type of the question
     * @return The list of questions
     */
    public List<Question> getAllQuestion(@Nullable QuestionType questionType, UUID teacherId, boolean admin) {
        List<Question> questionsList;

        if (admin) return questionRepository.findAll();

        if (questionType == null){
            questionsList = questionRepository.findAllByIsMutualTrue();
            questionsList.addAll(questionRepository.findAllByTeacherId(teacherId));
        }
        else {
            questionsList = questionRepository.findAllByIsMutualTrueAndType(questionType);
            questionsList.addAll(questionRepository.findAllByTeacherIdAndType(teacherId, questionType));
        }
        return questionsList;
    }

    /**
     * Get all the questions
     * @param questionType The type of the question
     * @return The list of questions
     */
    public List<Question> getAllQuestion(@Nullable QuestionType questionType, UUID teacherId, boolean admin, @Nullable String gender) {
        if (StringUtils.isEmpty(gender)) return getAllQuestion(questionType, teacherId, admin);

        List<Question> questionsList;
        if (admin) return questionRepository.findAllByFilter(Filter.valueOf(gender));

        if (questionType == null){
            questionsList = questionRepository.findAllByIsMutualTrueAndFilter(Filter.valueOf(gender));
            questionsList.addAll(questionRepository.findAllByTeacherIdAndFilter(teacherId, Filter.valueOf(gender)));
        }
        else {
            questionsList = questionRepository.findAllByIsMutualTrueAndTypeAndFilter(questionType, Filter.valueOf(gender));
            questionsList.addAll(questionRepository.findAllByTeacherIdAndTypeAndFilter(teacherId, questionType, Filter.valueOf(gender)));
        }
        return questionsList;
    }

    /**
     * Create a question and save it in the database
     * @param createQuestionDto Dto containing the information of the question
     * @return The created question
     */
    public Question createQuestion(CreateQuestionDto createQuestionDto) {
        try{
            Question createQuestion = Question.builder()
                    .filter(createQuestionDto.getFilter())
                    .content(createQuestionDto.getContent())
                    .answer(createQuestionDto.getAnswer())
                    .teacherId(createQuestionDto.getTeacherId())
                    .type(createQuestionDto.getType())
                    .build();
            return questionRepository.save(createQuestion);
        }catch (DataIntegrityViolationException ex){
            System.out.println("Question not saved because it already exists with content: " + createQuestionDto.getContent());
            return null;
        }
    }

    /**
     * Create multiples questions and save them in the database
     * @param createQuestionDtos Dto List containing the information of the questions
     * @return The created questions list
     */
    public List<Question> createMultipleQuestions(List<CreateQuestionDto> createQuestionDtos) {
        List<Question> questionsList = new ArrayList<>();
        for (CreateQuestionDto createQuestionDto : createQuestionDtos) {
            questionsList.add(createQuestion(createQuestionDto));
        }
        return questionsList;
    }

    /**
     * Update a question
     * @param id The id of the question
     * @param updateQuestionDto Dto containing the information of the question
     * @return The updated question
     * @throws NoSuchElementException If the question does not exist
     */
    public Question updateQuestion(Long id, UpdateQuestionDto updateQuestionDto) throws NoSuchElementException {
        Question question = questionRepository.findById(id).orElseThrow();
        question.setFilter(updateQuestionDto.getFilter());
        question.setContent(updateQuestionDto.getContent());
        question.setType(updateQuestionDto.getType());
        return questionRepository.save(question);
    }

    /**
     * Delete a question
     * @param id The id of the question
     */
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    /**
     * Save a question
     * @param actionClosedQuestionsDTO The DTO object corresponding to the question
     * @return The question saved
     */
    public Question save(QuestionLinkedDTO questionLinkedDTO){
        logger.info("Saving question linked: {}", questionLinkedDTO.toString());
        // If QuestionLinked already exist in the question then we don't create it, we reuse it
        List<Question> questions = this.getAllQuestion(null);
        logger.info("Questions: {}", questions.toString());
        boolean isAlreadyInTheQuestions = questions.stream().anyMatch(n -> n.getContent().equals(questionLinkedDTO.getContent()));
        Question questionToSave = null;
        logger.info("Is already in the questions: {}", isAlreadyInTheQuestions);
        if (!isAlreadyInTheQuestions){
            logger.info("Question not already in the questions");
            questionToSave = questionRepository.save(questionLinkedMapper.questionLinkedDtoToQuestion(questionLinkedDTO));
        }else {
            logger.info("Question already in the questions");
            // We have to link the existing question to the new ActionClosedQuestion
            Optional<Question> questionAlreadyExisting = questions.stream().filter(n -> n.getContent().equals(questionLinkedDTO.getContent())).findFirst();
            logger.info("Question already existing: {}", questionAlreadyExisting.toString());
            if(questionAlreadyExisting.isPresent()){
                questionToSave = questionAlreadyExisting.get();
            }
        }
        logger.info("Question to save: {}", questionToSave.toString());
        return questionToSave;
    }

}
