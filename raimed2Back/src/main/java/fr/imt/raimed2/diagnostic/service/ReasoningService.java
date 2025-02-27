package fr.imt.raimed2.diagnostic.service;

import fr.imt.raimed2.action.model.Action;
import fr.imt.raimed2.action.model.ActionExamen;
import fr.imt.raimed2.diagnostic.dto.request.CreateHypothesisDto;
import fr.imt.raimed2.diagnostic.dto.request.CreateInterpretationDto;
import fr.imt.raimed2.diagnostic.dto.request.CreateSyndromDto;
import fr.imt.raimed2.diagnostic.dto.request.ValidateDiagnosticDto;
import fr.imt.raimed2.diagnostic.dto.response.InterpretationDto;
import fr.imt.raimed2.diagnostic.dto.response.PrimaryElementDto;
import fr.imt.raimed2.diagnostic.dto.response.SyndromDto;
import fr.imt.raimed2.diagnostic.model.*;
import fr.imt.raimed2.diagnostic.repository.*;
import fr.imt.raimed2.exception.NonValidRequestBody;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReasoningService {

    private final DiagnosticRepository diagnosticRepository;

    private final EventRepository eventRepository;

    private final InterpretationRepository interpretationRepository;

    private final SyndromRepository syndromRepository;

    private final HypothesisRepository hypothesisRepository;

    private final DiagnosticValidationRepository diagnosticValidationRepository;


    /**
     * Add an interpretation to a diagnostic
     * @param diagnosticId The id of the diagnostic
     * @param createInterpretationDto The interpretation to add
     * @return The list of interpretations of the diagnostic
     * @throws NoSuchElementException If the diagnostic does not exist, or the action does not exist
     */
    public List<Interpretation> addInterpretationToDiagnostic(
            Long diagnosticId,
            CreateInterpretationDto createInterpretationDto
    ) throws NoSuchElementException {
        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticId).orElseThrow();
        Set<Action> actions = diagnostic.getVirtualPatient().getActions();

        Action action = actions.stream()
                .filter(a -> isMatchingAction(a, createInterpretationDto))
                .findFirst()
                .orElseThrow();

        interpretationRepository.save(Interpretation.builder()
                .date(new Date())
                .value(createInterpretationDto.getValue())
                .action(action)
                .diagnostic(diagnostic)
                .build()
        );

        return interpretationRepository.findAllByDiagnosticId(diagnosticId);
    }

    private boolean isMatchingAction(Action action, CreateInterpretationDto dto) {
        if (!action.getId().equals(dto.getActionId())) {
            return false;
        }

        if (action instanceof ActionExamen) {
            return ((ActionExamen) action).getSigns().equals(dto.getPrimaryElement());
        }

        return action.getPrimaryElement().equals(dto.getPrimaryElement());
    }


    /**
     * Add a syndrom to a diagnostic
     * @param diagnosticId The id of the diagnostic
     * @param createSyndromDto The syndrom to add
     * @return The list of syndroms of the diagnostic
     * @throws NoSuchElementException If the diagnostic does not exist
     * @throws NonValidRequestBody If the syndrom is not composed of two primary elements or interpretations
     */
    public List<Syndrom> addSyndromToDiagnostic(
            Long diagnosticId,
            CreateSyndromDto createSyndromDto
    ) throws NoSuchElementException, NonValidRequestBody {
        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticId).orElseThrow();

        // Retrieve all the primary elements that are part of the syndrom that have been made during the diagnostic
        Set<Action> actions = eventRepository.findAllByDiagnosticId(diagnosticId).stream()
                .map(Event::getAction)
                .filter(action -> (
                        createSyndromDto.getPrimaryElements().stream()
                                .map(PrimaryElementDto::getActionId)
                                .anyMatch(id -> id.equals(action.getId())
                                )
                ))
                .collect(Collectors.toSet());

        // Retrieve all the interpretations that are part of the syndrom that have been made during the diagnostic
        Set<Interpretation> interpretations = interpretationRepository.findAllByDiagnosticId(diagnosticId).stream()
                .filter(interpretation -> (
                        createSyndromDto.getInterpretations().stream()
                                .map(InterpretationDto::getId)
                                .anyMatch(id -> id.equals(interpretation.getId())
                                )
                ))
                .collect(Collectors.toSet());

        // Check that the syndrom is composed of two primary elements or interpretations
        if (actions.size() + interpretations.size() < 2) {
            throw new NonValidRequestBody("A syndrom must be composed of at least 2 elements");
        }

        Syndrom syndrom = Syndrom.builder()
                .date(new Date())
                .value(createSyndromDto.getValue())
                .actions(actions)
                .diagnostic(diagnostic)
                .interpretations(interpretations)
                .build();

        syndromRepository.save(syndrom);

        return syndromRepository.findAllByDiagnosticId(diagnosticId);
    }

    /**
     * Add a hypothesis to a diagnostic
     * @param diagnosticId The id of the diagnostic
     * @param createHypothesisDto The hypothesis to add
     * @return The list of hypothesis of the diagnostic
     * @throws NoSuchElementException If the diagnostic does not exist
     * @throws NonValidRequestBody If the hypothesis is not composed of three primary elements, interpretations or syndroms
     */
    public List<Hypothesis> addHypothesisToDiagnostic(
            Long diagnosticId,
            CreateHypothesisDto createHypothesisDto
    ) throws NoSuchElementException, NonValidRequestBody {
        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticId).orElseThrow();

        // Retrieve all the primary elements that are part of the hypothesis that have been made during the diagnostic
        Set<Action> actions = eventRepository.findAllByDiagnosticId(diagnosticId).stream()
                .map(Event::getAction)
                .filter(action -> (
                        createHypothesisDto.getPrimaryElements().stream()
                                .map(PrimaryElementDto::getActionId)
                                .anyMatch(id -> id.equals(action.getId())
                                )
                ))
                .collect(Collectors.toSet());

        // Retrieve all the interpretations that are part of the hypothesis that have been made during the diagnostic
        Set<Interpretation> interpretations = interpretationRepository.findAllByDiagnosticId(diagnosticId).stream()
                .filter(interpretation -> (
                        createHypothesisDto.getInterpretations().stream()
                                .map(InterpretationDto::getId)
                                .anyMatch(id -> id.equals(interpretation.getId())
                                )
                ))
                .collect(Collectors.toSet());

        // Retrieve all the syndroms that are part of the hypothesis that have been made during the diagnostic
        Set<Syndrom> syndroms = syndromRepository.findAllByDiagnosticId(diagnosticId).stream()
                .filter(syndrom -> (
                        createHypothesisDto.getSyndroms().stream()
                                .map(SyndromDto::getId)
                                .anyMatch(id -> id.equals(syndrom.getId())
                                )
                ))
                .collect(Collectors.toSet());

        // Check that the hypothesis is composed of three primary elements, interpretations or syndroms
        if (actions.size() + interpretations.size() + syndroms.size() < 3) {
            throw new NonValidRequestBody("An hypothesis must be at least composed of three primary elements, interpretations or syndroms");
        }

        Hypothesis hypothesis = Hypothesis.builder()
                .date(new Date())
                .value(createHypothesisDto.getValue())
                .actions(actions)
                .diagnostic(diagnostic)
                .interpretations(interpretations)
                .syndroms(syndroms)
                .build();

        hypothesisRepository.save(hypothesis);

        return hypothesisRepository.findAllByDiagnosticId(diagnosticId);
    }


    /**
     * Validate a diagnostic
     * @param diagnosticId The id of the diagnostic
     * @param validateDiagnosticDto The validation of the diagnostic
     * @return The diagnostic
     */
    @Transactional
    public Diagnostic validateDiagnostic(Long diagnosticId, ValidateDiagnosticDto validateDiagnosticDto) {
        Diagnostic diagnostic = diagnosticRepository.findById(diagnosticId).orElseThrow();
        if (diagnostic.getStatus() != DiagnosticStatus.IN_PROGRESS) {
            throw new NonValidRequestBody("The diagnostic is not in progress");
        }
        Hypothesis hypothesis = hypothesisRepository.findById(validateDiagnosticDto.getHypothesisId()).orElseThrow();
        DiagnosticValidation diagnosticValidation = DiagnosticValidation.builder()
                .date(new Date())
                .diagnostic(diagnostic)
                .degreeOfCertainty(validateDiagnosticDto.getDegreeOfCertainty())
                .hypothesis(hypothesis)
                .build();
        diagnosticValidationRepository.save(diagnosticValidation);
        if (diagnostic.getVirtualPatient().getResult().equalsIgnoreCase(hypothesis.getValue())) {
            diagnostic.setStatus(DiagnosticStatus.SUCCESS);
        } else {
            diagnostic.setStatus(DiagnosticStatus.FAILURE);
        }
        diagnosticRepository.save(diagnostic);
        return diagnostic;
    }

}
