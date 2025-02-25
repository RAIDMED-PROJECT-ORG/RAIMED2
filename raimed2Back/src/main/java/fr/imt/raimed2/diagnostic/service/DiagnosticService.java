package fr.imt.raimed2.diagnostic.service;

import fr.imt.raimed2.action.model.Action;
import fr.imt.raimed2.action.model.ActionClosedQuestion;
import fr.imt.raimed2.action.model.ActionOpenedQuestion;
import fr.imt.raimed2.action.model.ActionPrescription;
import fr.imt.raimed2.action.service.ActionService;
import fr.imt.raimed2.diagnostic.dto.request.CreateEventDto;
import fr.imt.raimed2.diagnostic.model.*;
import fr.imt.raimed2.diagnostic.repository.*;
import fr.imt.raimed2.prescription.model.Prescription;
import fr.imt.raimed2.prescription.service.PrescriptionService;
import fr.imt.raimed2.question.model.Question;
import fr.imt.raimed2.question.model.QuestionType;
import fr.imt.raimed2.question.service.QuestionService;
import fr.imt.raimed2.user.model.Role;
import fr.imt.raimed2.user.model.User;
import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import fr.imt.raimed2.virtualPatient.repository.VirtualPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DiagnosticService {

    private final DiagnosticRepository diagnosticRepository;

    private final VirtualPatientRepository virtualPatientRepository;

    private final EventRepository eventRepository;

    private final InterpretationRepository interpretationRepository;

    private final SyndromRepository syndromRepository;

    private final HypothesisRepository hypothesisRepository;

    private final QuestionService questionService;

    private final PrescriptionService prescriptionService;

    private final ActionService actionService;

    /**
     * Get all the diagnostics of a user
     * @param user The user
     * @return The list of diagnostics
     */
    public List<Diagnostic> getAllDiagnosticByUser(User user) {
        return diagnosticRepository.findAllByUser(user);
    }

    /**
     * Get a diagnostic by its id
     * @param id The id of the diagnostic
     * @param user The user that is going to retrieve the diagnostic
     * @return The diagnostic
     * @throws NoSuchElementException If the diagnostic does not exist
     * @throws AccessDeniedException If the user is not the owner of the diagnostic and is a student
     */
    public Diagnostic getDiagnosticById(Long id, User user) throws NoSuchElementException, AccessDeniedException {
        Diagnostic diagnostic = diagnosticRepository.findById(id).orElseThrow();
        if (!diagnostic.getUser().getId().equals(user.getId()) && user.getRole() == Role.STUDENT) {
            throw new AccessDeniedException("User with role Student can only retrieve its diagnostics");
        }
        return diagnostic;
    }

    /**
     * Get all the events of a diagnostic
     * @param diagnosticId The id of the diagnostic
     * @param user The user that is/have doing/done the diagnostic
     * @return The list of events
     * @throws NoSuchElementException If the diagnostic does not exist
     * @throws AccessDeniedException If the user is not the owner of the diagnostic and is a student
     */
    public List<Event> getAllEventByDiagnosticId(Long diagnosticId, User user) throws NoSuchElementException, AccessDeniedException {
        Diagnostic diagnostic = this.diagnosticRepository.findById(diagnosticId).orElseThrow();

        // If the user is not the owner of the diagnostic and is a student, must not retrieve the events
        if (!diagnostic.getUser().getId().equals(user.getId()) && user.getRole() == Role.STUDENT) {
            throw new AccessDeniedException("User with role Student can only retrieve events made during its diagnostics");
        }

        return this.eventRepository.findAllByDiagnosticId(diagnostic.getId());
    }

    /**
     * Get all the interpretations of a diagnostic
     * @param diagnosticId The id of the diagnostic
     * @param user The user that is/have doing/done the diagnostic
     * @return The list of interpretations
     * @throws NoSuchElementException If the diagnostic does not exist
     * @throws AccessDeniedException If the user is not the owner of the diagnostic and is a student
     */
    public List<Interpretation> getAllInterpretationByDiagnosticId(Long diagnosticId, User user) throws NoSuchElementException, AccessDeniedException {
        Diagnostic diagnostic = this.diagnosticRepository.findById(diagnosticId).orElseThrow();

        // If the user is not the owner of the diagnostic and is a student, must not retrieve the interpretations
        if (!diagnostic.getUser().getId().equals(user.getId()) && user.getRole() == Role.STUDENT) {
            throw new AccessDeniedException("User with role Student can only retrieve interpretations made during its diagnostics");
        }

        return this.interpretationRepository.findAllByDiagnosticId(diagnostic.getId());
    }

    /**
     * Get all the syndroms of a diagnostic
     * @param diagnosticId The id of the diagnostic
     * @param user The user that is/have doing/done the diagnostic
     * @return The list of syndroms
     * @throws NoSuchElementException If the diagnostic does not exist
     * @throws AccessDeniedException If the user is not the owner of the diagnostic and is a student
     */
    public List<Syndrom> getAllSyndromsByDiagnosticId(Long diagnosticId, User user) throws NoSuchElementException, AccessDeniedException {
        Diagnostic diagnostic = this.diagnosticRepository.findById(diagnosticId).orElseThrow();

        // If the user is not the owner of the diagnostic and is a student, must not retrieve the syndroms
        if (!diagnostic.getUser().getId().equals(user.getId()) && user.getRole() == Role.STUDENT) {
            throw new AccessDeniedException("User with role Student can only retrieve syndroms made during its diagnostics");
        }

        return this.syndromRepository.findAllByDiagnosticId(diagnostic.getId());
    }

    public List<Hypothesis> getAllHypothesisByDiagnosticId(Long diagnosticId, User user) throws NoSuchElementException, AccessDeniedException {
        Diagnostic diagnostic = this.diagnosticRepository.findById(diagnosticId).orElseThrow();

        // If the user is not the owner of the diagnostic and is a student, must not retrieve the hypothesis
        if (!diagnostic.getUser().getId().equals(user.getId()) && user.getRole() == Role.STUDENT) {
            throw new AccessDeniedException("User with role Student can only retrieve hypothesis made during its diagnostics");
        }

        return this.hypothesisRepository.findAllByDiagnosticId(diagnostic.getId());
    }


    /**
     * Get all the closed questions that are not yet answered by the virtual patient in the given diagnostic
     * @param diagnosticId The id of the diagnostic
     * @return The list of closed questions available for the virtual patient
     * @throws NoSuchElementException If the diagnostic does not exist
     */
    public List<Question> getDiagnosticClosedQuestion(Long diagnosticId) throws NoSuchElementException {
        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticId).orElseThrow();
        List<Question> returnedQuestions = new ArrayList<>();

        // Retrieve all closed questions of the virtual patient
        List<ActionClosedQuestion> closedQuestions = actionService.getAllClosedQuestionOfVirtualPatient(
            diagnostic.getVirtualPatient().getId()
        );

        // Retrieve all asked closed questions of the diagnostic
        List<ActionClosedQuestion> askedCloseQuestions = actionService.getAllClosedQuestionOfDiagnosticEvents(
            eventRepository.findAllByDiagnosticId(diagnosticId)
        );

        // Keep only the questions that are already defined in the virtual patient and not already asked in the diagnostic
        closedQuestions.removeIf(
            question -> (
                askedCloseQuestions.stream().anyMatch(q -> q.getQuestion().getId().equals(question.getQuestion().getId()))
            )
        );

        for(ActionClosedQuestion question : closedQuestions) {
            returnedQuestions.add(question.getQuestion());
        }

        return returnedQuestions;
    }

    /**
     * Get all the opened questions that are not yet answered by the virtual patient in the given diagnostic
     * @param diagnosticId The id of the diagnostic
     * @return The list of opened questions available for the virtual patient
     * @throws NoSuchElementException If the diagnostic does not exist
     */
    public List<Question> getDiagnosticOpenedQuestion(Long diagnosticId) throws NoSuchElementException {
        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticId).orElseThrow();
        List<Question> returnedQuestions = new ArrayList<>();

        // Retrieve all opened questions of the virtual patient
        List<ActionOpenedQuestion> openedQuestions = actionService.getAllOpenedQuestionOfVirtualPatient(
            diagnostic.getVirtualPatient().getId()
        );
        // Retrieve all asked opened questions of the diagnostic
        List<ActionOpenedQuestion> askedOpenedQuestions = actionService.getAllOpenedQuestionOfDiagnosticEvents(
            eventRepository.findAllByDiagnosticId(diagnosticId)
        );
        // Keep only the questions that are already defined in the virtual patient and not already asked in the diagnostic
        openedQuestions.removeIf(
            question -> (
                    askedOpenedQuestions.stream().anyMatch(q -> q.getQuestion().getId().equals(question.getQuestion().getId()))
            )
        );

        for(ActionOpenedQuestion question : openedQuestions){
            returnedQuestions.add(question.getQuestion());
        }

        return returnedQuestions;
    }

    public List<Prescription> getDiagnosticPrescription(Long diagnosticId, String type) throws NoSuchElementException {
        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticId).orElseThrow();
        List<Prescription> returnedPrescriptions = new ArrayList<>();

        List<ActionPrescription> prescriptions = actionService.getAllPrescriptionOfVirtualPatient(
                diagnostic.getVirtualPatient().getId(), type
        );
        List<ActionPrescription> askedPrescriptions = actionService.getAllPrescriptionsOfDiagnosticEvents(
                eventRepository.findAllByDiagnosticId(diagnosticId)
        );

        // Keep only the questions that are already defined in the virtual patient and not already asked in the diagnostic
        prescriptions.removeIf(
                prescription -> (
                        askedPrescriptions.stream().anyMatch(p -> p.getPrescription().getId().equals(prescription.getPrescription().getId()))
                )
        );

        for(ActionPrescription prescription : prescriptions){
            returnedPrescriptions.add(prescription.getPrescription());
        }

        return returnedPrescriptions;
    }

    public List<Action> getDiagnosticExam(Long diagnosticId) {
        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticId).orElseThrow();

        List<Action> exams = actionService.getAllExamOfVirtualPatient(
                diagnostic.getVirtualPatient().getId()
        );
        List<Action> askedExam = actionService.getAllExamsOfDiagnosticEvents(
                eventRepository.findAllByDiagnosticId(diagnosticId)
        );

        exams.removeIf(
                action -> (
                        askedExam.stream().anyMatch(a -> action.getId().equals(a.getId()))
                )
        );

        return exams;
    }

    /**
     * Start a diagnostic of a virtual patient for the given user
     * @param user The user that is going to start the diagnostic
     * @param virtualPatientId The id of the virtual patient
     * @return The new diagnostic entity
     * @throws NoSuchElementException If the virtual patient does not exist
     */
    public Diagnostic startDiagnosticOfVirtualPatient(User user, Long virtualPatientId) throws NoSuchElementException {
        VirtualPatient virtualPatient = virtualPatientRepository.findById(virtualPatientId).orElseThrow();
        Diagnostic newDiagnostic = Diagnostic.builder()
                .startDate(new Date())
                .status(DiagnosticStatus.IN_PROGRESS)
                .user(user)
                .virtualPatient(virtualPatient)
                .build();
        return diagnosticRepository.save(newDiagnostic);
    }

    /**
     * Add an event to a diagnostic
     * @param diagnosticId The id of the diagnostic
     * @param createEventDto The event to add
     * @return The list of events of the diagnostic
     * @throws NoSuchElementException If the diagnostic does not exist, or the action does not exist
     */
    public List<Event> addEventToDiagnostic(
            Long diagnosticId,
            CreateEventDto createEventDto
    ) throws NoSuchElementException {
        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticId).orElseThrow();
        VirtualPatient virtualPatient = diagnostic.getVirtualPatient();
        Set<Action> actions = virtualPatient.getActions();

        Action action = actions.stream()
                .filter(a -> a.getId().equals(createEventDto.getActionId()))
                .findFirst()
                .orElseThrow();

        eventRepository.save(Event.builder()
                .date(new Date())
                .action(action)
                .diagnostic(diagnostic)
                .build()
        );
        return eventRepository.findAllByDiagnosticId(diagnosticId);
    }
}