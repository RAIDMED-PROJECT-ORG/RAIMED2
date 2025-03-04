package fr.imt.raimed2.action.service;

import fr.imt.raimed2.action.dto.request.CreateActionSpontaneousPatientSpeech;
import fr.imt.raimed2.action.dto.xml.*;
import fr.imt.raimed2.action.model.*;
import fr.imt.raimed2.action.repository.*;
import fr.imt.raimed2.diagnostic.model.Event;
import fr.imt.raimed2.precision.model.Precision;
import fr.imt.raimed2.precision.service.PrecisionService;
import fr.imt.raimed2.prescription.model.Prescription;
import fr.imt.raimed2.prescription.model.PrescriptionType;
import fr.imt.raimed2.prescription.service.PrescriptionService;
import fr.imt.raimed2.question.model.Question;
import fr.imt.raimed2.question.service.QuestionService;
import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import fr.imt.raimed2.virtualPatient.repository.VirtualPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ActionService {

    private final ActionRepository actionRepository;

    private final ActionClosedQuestionRepository actionClosedQuestionRepository;

    private final ActionOpenedQuestionRepository actionOpenedQuestionRepository;

    private final ActionPrescriptionRepository actionPrescriptionRepository;

    private final ActionPrecisionRepository actionPrecisionRepository;

    private final VirtualPatientRepository virtualPatientRepository;

    private final QuestionService questionService;

    private final PrecisionService precisionService;

    private final PrescriptionService prescriptionService;

    private final ActionClosedQuestionsMapper actionClosedQuestionsMapper;

    private final ActionSpontaneousPatientSpeechMapper actionSpontaneousPatientSpeechMapper;

    private final ActionOpenedQuestionMapper actionOpenedQuestionMapper;

    private final ActionPrescriptionMapper actionPrescriptionMapper;

    private final ActionPrecisionMapper actionPrecisionMapper;

    /**
     * Add a spontaneous patient speech action to the virtual patient
     *
     * @param virtualPatientId                     The id of the virtual patient
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
                        .primaryElement(!"undefined".equals(createActionSpontaneousPatientSpeech.getPrimaryElement()) ? createActionSpontaneousPatientSpeech.getPrimaryElement() : null)
                        .virtualPatient(virtualPatient)
                        .build()
        );
    }

    /**
     * Get all the closed questions that are set as actions for the given virtual patient
     *
     * @param virtualPatientId The id of the virtual patient
     * @return The list of closed questions of the virtual patient
     */
    public List<ActionClosedQuestion> getAllClosedQuestionOfVirtualPatient(Long virtualPatientId) {
        return actionClosedQuestionRepository.findAllByVirtualPatientId(virtualPatientId);
    }

    /**
     * Get all the opened questions that are set as actions for the given virtual patient
     *
     * @param virtualPatientId The id of the virtual patient
     * @return The list of opened questions of the virtual patient
     */
    public List<ActionOpenedQuestion> getAllOpenedQuestionOfVirtualPatient(Long virtualPatientId) {
        return actionOpenedQuestionRepository.findAllByVirtualPatientId(virtualPatientId);
    }

    public List<ActionPrescription> getAllPrescriptionOfVirtualPatient(Long virtualPatientId, String type) {
        PrescriptionType prescriptionType = PrescriptionType.valueOf(type.toUpperCase());
        return actionPrescriptionRepository.findAllByVirtualPatientIdAndPrescription_Type(virtualPatientId, prescriptionType);
    }

    public List<Action> getAllPrecisionOfVirtualPatient(Long virtualPatientId) {
        return actionRepository.findAllByVirtualPatientIdAndType(virtualPatientId, "PRECISION");
    }

    /**
     * @param type le type de l'exam (Inspection, Palpation, Percussion, Auscultation). Il est ensuite passé comme
     *             paramètre en tant que "primaryElement" car ce type est stocké ici.
     */
    public List<Action> getAllExamOfVirtualPatient(Long virtualPatientId, String type) {
        return actionRepository.findAllByVirtualPatientIdAndTypeAndPrimaryElement(virtualPatientId, "EXAMEN", type);
    }

    /**
     * Get all the closed questions that have already been answered by the virtual patient in the given list of events
     *
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
     *
     * @param events The list of events
     * @return The list of opened questions of the diagnostic events
     */
    public List<ActionOpenedQuestion> getAllOpenedQuestionOfDiagnosticEvents(List<Event> events) {
        return actionOpenedQuestionRepository.findAllByIdIn(
                events.stream().map(event -> event.getAction().getId()).toList()
        );
    }

    public List<ActionPrescription> getAllPrescriptionsOfDiagnosticEvents(List<Event> events) {
        return actionPrescriptionRepository.findAllByIdIn(
                events.stream().map(event -> event.getAction().getId()).toList()
        );
    }

    public List<Action> getAllPrecisionsOfDiagnosticEvents(List<Event> events) {
        return actionRepository.findAllByIdIn(
                events.stream().map(event -> event.getAction().getId()).toList()
        );
    }

    public List<Action> getAllExamsOfDiagnosticEvents(List<Event> events) {
        return actionRepository.findAllByIdIn(
                events.stream().map(event -> event.getAction().getId()).toList()
        );
    }

    /**
     * Save a ActionClosedQuestion
     *
     * @param virtualPatient The virtual patient linked to the ActionClosedQuestion
     * @param actionDTO      The action in form of DTO
     * @return The ActionClosedQuestion saved object
     */
    public ActionClosedQuestion saveActionClosedQuestion(VirtualPatient virtualPatient, ActionDTO actionDTO) {
        Question question = questionService.getQuestionByContent(actionDTO.getActionClosedQuestionDTO().getQuestionLinked().getContent());
        if (question == null)
            question = questionService.save(actionDTO.getActionClosedQuestionDTO().getQuestionLinked());
        ActionClosedQuestion actionClosedQuestion = actionClosedQuestionsMapper.actionClosedQuestionsDtoToDao(actionDTO.getActionClosedQuestionDTO());
        actionClosedQuestion.setQuestion(question);
        if (!Objects.equals(actionDTO.getPrimaryElement(), "undefined")) {
            actionClosedQuestion.setPrimaryElement(actionDTO.getPrimaryElement());
        }
        actionClosedQuestion.setVirtualPatient(virtualPatient);
        return actionClosedQuestionRepository.save(actionClosedQuestion);
    }

    /**
     * Save a ActionSpontaneousPatientSpeech
     *
     * @param virtualPatient The virtual patient linked to the ActionClosedQuestion
     * @param actionDTO      The action in form of DTO
     * @return The ActionSpontaneousPatientSpeech saved object
     */
    public ActionSpontaneousPatientSpeech saveActionSpontaneousPatientSpeech(VirtualPatient virtualPatient, ActionDTO actionDTO) {
        ActionSpontaneousPatientSpeech actionSpontaneousPatientSpeech = actionSpontaneousPatientSpeechMapper.actionSpontaneousPatientSpeechDtoToDao(actionDTO.getActionSpontaneousPatientSpeech());
        actionSpontaneousPatientSpeech.setVirtualPatient(virtualPatient);
        actionSpontaneousPatientSpeech.setPrimaryElement(actionDTO.getPrimaryElement());
        actionSpontaneousPatientSpeech.setType(actionDTO.getType());
        return actionRepository.save(actionSpontaneousPatientSpeech);
    }

    public ActionPrescription saveActionPrescription(VirtualPatient virtualPatient, ActionDTO actionDTO) {
        Prescription prescription = prescriptionService.getPrescriptionByContentAndResult(actionDTO.getActionPrescriptionDTO().getPrescription().getContent(), actionDTO.getActionPrescriptionDTO().getPrescription().getResult());
        if (prescription == null)
            prescription = prescriptionService.save(actionDTO.getActionPrescriptionDTO().getPrescription());
        ActionPrescription actionPrescription = actionPrescriptionMapper.actionPrescriptionDTOtoDao(actionDTO.getActionPrescriptionDTO());
        actionPrescription.setVirtualPatient(virtualPatient);
        actionPrescription.setPrescription(prescription);
        actionPrescription.setPrimaryElement(actionDTO.getPrimaryElement());
        actionPrescription.setType(actionDTO.getType());
        return actionRepository.save(actionPrescription);
    }

    public ActionPrecision saveActionPrecision(VirtualPatient virtualPatient, ActionDTO actionDTO) {
        Precision precision = precisionService.getPrecisionByQuestionAndAnswer(actionDTO.getActionPrecisionDTO().getPrecision().getQuestion(), actionDTO.getActionPrecisionDTO().getPrecision().getAnswer());
        if (precision == null) precision = precisionService.save(actionDTO.getActionPrecisionDTO().getPrecision());
        ActionPrecision actionPrecision = actionPrecisionMapper.actionPrecisionDTOtoDao(actionDTO.getActionPrecisionDTO());
        actionPrecision.setVirtualPatient(virtualPatient);
        actionPrecision.setPrecision(precision);
        actionPrecision.setPrimaryElement(actionDTO.getPrimaryElement());
        actionPrecision.setType(actionDTO.getType());
        return actionRepository.save(actionPrecision);
    }

    /**
     * Save a ActionOpenedQuestion
     *
     * @param virtualPatient The virtual patient linked to the ActionClosedQuestion
     * @param actionDTO      The action in form of DTO
     * @return The ActionOpenedQuestion saved object
     */
    public ActionOpenedQuestion saveActionOpenedQuestion(VirtualPatient virtualPatient, ActionDTO actionDTO) {
        Question question = questionService.getQuestionByContent(actionDTO.getActionOpenedQuestionDTO().getQuestionLinked().getContent());
        if (question == null)
            question = questionService.save(actionDTO.getActionOpenedQuestionDTO().getQuestionLinked());
        ActionOpenedQuestion actionOpenedQuestion = actionOpenedQuestionMapper.actionOpenedQuestionDtoToDao(actionDTO.getActionOpenedQuestionDTO());
        actionOpenedQuestion.setQuestion(question);
        if (!Objects.equals(actionDTO.getPrimaryElement(), "undefined")) {
            actionOpenedQuestion.setPrimaryElement(actionDTO.getPrimaryElement());
        }
        actionOpenedQuestion.setVirtualPatient(virtualPatient);
        return actionOpenedQuestionRepository.save(actionOpenedQuestion);
    }

    public void saveActionExamen(VirtualPatient virtualPatient, ActionDTO actionDTO) {
        ActionExamen actionExamen = new ActionExamen();
        actionExamen.setVirtualPatient(virtualPatient);
        actionExamen.setPrimaryElement(actionDTO.getPrimaryElement());
        actionExamen.setType(actionDTO.getType());
        switch (actionDTO.getType()) {
            case "INSPECTION":
                actionExamen.setZone(actionDTO.getActionInspectionDTO().getZone());
                actionExamen.setSigns(actionDTO.getActionInspectionDTO().getSigns());
                break;
            case "PERCUSSION":
                actionExamen.setZone(actionDTO.getActionPercussionDTO().getZone());
                actionExamen.setSigns(actionDTO.getActionPercussionDTO().getSigns());
                break;
            case "PALPATION":
                actionExamen.setZone(actionDTO.getActionPalpationDTO().getZone());
                actionExamen.setSigns(actionDTO.getActionPalpationDTO().getSigns());
                break;
            case "AUSCULTATION":
                actionExamen.setZone(actionDTO.getActionAuscultationDTO().getZone());
                actionExamen.setSigns(actionDTO.getActionAuscultationDTO().getSigns());
                break;
            default:
                throw new IllegalArgumentException("Unknown action type: " + actionDTO.getType());
        }

        actionRepository.save(actionExamen);
    }
}