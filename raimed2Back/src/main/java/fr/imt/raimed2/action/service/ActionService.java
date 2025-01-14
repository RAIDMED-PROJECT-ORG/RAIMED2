package fr.imt.raimed2.action.service;

import fr.imt.raimed2.action.dto.request.CreateActionSpontaneousPatientSpeech;
import fr.imt.raimed2.action.dto.xml.*;
import fr.imt.raimed2.action.model.ActionClosedQuestion;
import fr.imt.raimed2.action.model.ActionOpenedQuestion;
import fr.imt.raimed2.action.model.ActionSpontaneousPatientSpeech;
import fr.imt.raimed2.action.repository.ActionClosedQuestionRepository;
import fr.imt.raimed2.action.repository.ActionOpenedQuestionRepository;
import fr.imt.raimed2.action.repository.ActionRepository;
import fr.imt.raimed2.diagnostic.model.Event;
import fr.imt.raimed2.question.model.Question;
import fr.imt.raimed2.question.service.QuestionService;
import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import fr.imt.raimed2.virtualPatient.repository.VirtualPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ActionService {

    private final ActionRepository actionRepository;

    private final ActionClosedQuestionRepository actionClosedQuestionRepository;

    private final ActionOpenedQuestionRepository actionOpenedQuestionRepository;

    private final VirtualPatientRepository virtualPatientRepository;

    private final QuestionService questionService;

    private final ActionClosedQuestionsMapper actionClosedQuestionsMapper;

    private final ActionSpontaneousPatientSpeechMapper actionSpontaneousPatientSpeechMapper;

    private final ActionOpenedQuestionMapper actionOpenedQuestionMapper;

    /**
     * Add a spontaneous patient speech action to the virtual patient
     * @param virtualPatientId The id of the virtual patient
     * @param createActionSpontaneousPatientSpeech The dto containing the information of the action
     * @throws NoSuchElementException If the virtual patient does not exist
     */
    public void addSpontaneousPatientSpeechAction(
            Long virtualPatientId,
            CreateActionSpontaneousPatientSpeech createActionSpontaneousPatientSpeech
    ) throws NoSuchElementException {
        VirtualPatient virtualPatient = virtualPatientRepository.findById(virtualPatientId).orElseThrow();
        actionRepository.save(
            ActionSpontaneousPatientSpeech.builder()
                .speech(createActionSpontaneousPatientSpeech.getSpeech())
                .primaryElement(createActionSpontaneousPatientSpeech.getPrimaryElement())
                .virtualPatient(virtualPatient)
                .build()
        );
    }

    /**
     * Get all the closed questions that are set as actions for the given virtual patient
     * @param virtualPatientId The id of the virtual patient
     * @return The list of closed questions of the virtual patient
     */
    public List<ActionClosedQuestion> getAllClosedQuestionOfVirtualPatient(Long virtualPatientId) {
        return actionClosedQuestionRepository.findAllByVirtualPatientId(virtualPatientId);
    }

    /**
     * Get all the opened questions that are set as actions for the given virtual patient
     * @param virtualPatientId The id of the virtual patient
     * @return The list of opened questions of the virtual patient
     */
    public List<ActionOpenedQuestion> getAllOpenedQuestionOfVirtualPatient(Long virtualPatientId) {
        return actionOpenedQuestionRepository.findAllByVirtualPatientId(virtualPatientId);
    }

    /**
     * Get all the closed questions that have already been answered by the virtual patient in the given list of events
     * @param events The list of events
     * @return The list of closed questions of the diagnostic events
     */
    public List<ActionClosedQuestion> getAllClosedQuestionOfDiagnosticEvents(List<Event> events) {
        return actionClosedQuestionRepository.findAllByIdIn(
            events.stream().map(event -> event.getAction().getId()).toList()
        );
    }

    /**
     * Get all the opened questions that have already been answered by the virtual patient in the given list of events
     * @param events The list of events
     * @return The list of opened questions of the diagnostic events
     */
    public List<ActionOpenedQuestion> getAllOpenedQuestionOfDiagnosticEvents(List<Event> events) {
        return actionOpenedQuestionRepository.findAllByIdIn(
            events.stream().map(event -> event.getAction().getId()).toList()
        );
    }

    /**
     * Save a ActionClosedQuestion
     * @param virtualPatient The virtual patient linked to the ActionClosedQuestion
     * @param actionDTO The action in form of DTO
     * @return The ActionClosedQuestion saved object
     */
      //TODO
//    public ActionClosedQuestion saveActionClosedQuestion(VirtualPatient virtualPatient, ActionDTO actionDTO){
//        Question question = questionService.save(actionDTO.getActionClosedQuestionDTO().getQuestionLinked());
//        ActionClosedQuestion actionClosedQuestion = actionClosedQuestionsMapper.actionClosedQuestionsDtoToDao(actionDTO.getActionClosedQuestionDTO());
//        actionClosedQuestion.setQuestion(question);
//        actionClosedQuestion.setPrimaryElement(actionDTO.getPrimaryElement());
//        actionClosedQuestion.setVirtualPatient(virtualPatient);
//        return actionClosedQuestionRepository.save(actionClosedQuestion);
//    }

    /**
     * Save a ActionSpontaneousPatientSpeech
     * @param virtualPatient The virtual patient linked to the ActionClosedQuestion
     * @param actionDTO The action in form of DTO
     * @return The ActionSpontaneousPatientSpeech saved object
     */
    public ActionSpontaneousPatientSpeech saveActionSpontaneousPatientSpeech(VirtualPatient virtualPatient, ActionDTO actionDTO){
        ActionSpontaneousPatientSpeech actionSpontaneousPatientSpeech = actionSpontaneousPatientSpeechMapper.actionSpontaneousPatientSpeechDtoToDao(actionDTO.getActionSpontaneousPatientSpeech());
        actionSpontaneousPatientSpeech.setVirtualPatient(virtualPatient);
        actionSpontaneousPatientSpeech.setPrimaryElement(actionDTO.getPrimaryElement());
        actionSpontaneousPatientSpeech.setType(actionDTO.getType());

        return actionRepository.save(actionSpontaneousPatientSpeech);
    }

    /**
     * Save a ActionOpenedQuestion
     * @param virtualPatient The virtual patient linked to the ActionClosedQuestion
     * @param actionDTO The action in form of DTO
     * @return The ActionOpenedQuestion saved object
     */
     //TODO
//    public ActionOpenedQuestion saveActionOpenedQuestion(VirtualPatient virtualPatient, ActionDTO actionDTO) {
//        Question question = questionService.save(actionDTO.getActionOpenedQuestionDTO().getQuestionLinked());
//        ActionOpenedQuestion actionOpenedQuestion = actionOpenedQuestionMapper.actionOpenedQuestionDtoToDao(actionDTO.getActionOpenedQuestionDTO());
//        actionOpenedQuestion.setQuestion(question);
//        actionOpenedQuestion.setPrimaryElement(actionDTO.getPrimaryElement());
//        actionOpenedQuestion.setVirtualPatient(virtualPatient);
//        return actionOpenedQuestionRepository.save(actionOpenedQuestion);
//    }
}