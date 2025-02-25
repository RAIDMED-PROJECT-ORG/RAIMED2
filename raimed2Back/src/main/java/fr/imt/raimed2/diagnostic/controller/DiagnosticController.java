package fr.imt.raimed2.diagnostic.controller;

import fr.imt.raimed2.action.model.Action;
import fr.imt.raimed2.diagnostic.dto.request.*;
import fr.imt.raimed2.diagnostic.dto.response.*;
import fr.imt.raimed2.diagnostic.model.*;
import fr.imt.raimed2.diagnostic.service.DiagnosticService;
import fr.imt.raimed2.diagnostic.service.ReasoningService;
import fr.imt.raimed2.prescription.model.Prescription;
import fr.imt.raimed2.question.model.Question;
import fr.imt.raimed2.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diagnostic")
@RequiredArgsConstructor
public class DiagnosticController {

    private final DiagnosticService diagnosticService;

    private final ReasoningService reasoningService;

    private final DiagnosticEntityDtoMapper diagnosticEntityDtoMapper;

    @GetMapping("/user")
    public ResponseEntity<List<DiagnosticDto>> getAllDiagnosticByUser(@AuthenticationPrincipal User user) {
        List<Diagnostic> userAllDiagnostic = diagnosticService.getAllDiagnosticByUser(user);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.diagnosticEntityToDiagnosticDto(userAllDiagnostic));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticDto> getDiagnosticById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        Diagnostic diagnostic = this.diagnosticService.getDiagnosticById(id, user);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.diagnosticEntityToDiagnosticDto(diagnostic));
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<String> getDiagnosticResult(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        Diagnostic diagnostic = this.diagnosticService.getDiagnosticById(id, user);
        return ResponseEntity.ok().body(diagnostic.getVirtualPatient().getResult());
    }

    @GetMapping("/{id}/event")
    private ResponseEntity<List<EventDto>> getDiagnosticEvents(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        List<Event> events = this.diagnosticService.getAllEventByDiagnosticId(id, user);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.eventEntityToEventDto(events));
    }

    @GetMapping("/{id}/interpretation")
    private ResponseEntity<List<InterpretationDto>> getDiagnosticInterpretations(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        List<Interpretation> interpretations = this.diagnosticService.getAllInterpretationByDiagnosticId(id, user);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.interpretationEntityToInterpretationDto(interpretations));
    }

    @GetMapping("/{id}/syndrom")
    private ResponseEntity<List<SyndromDto>> getDiagnosticSyndroms(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        List<Syndrom> syndroms = this.diagnosticService.getAllSyndromsByDiagnosticId(id, user);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.syndromEntityToSyndromDto(syndroms));
    }

    @GetMapping("/{id}/hypothesis")
    private ResponseEntity<List<HypothesisDto>> getDiagnosticHypothesis(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        List<Hypothesis> hypothesis = this.diagnosticService.getAllHypothesisByDiagnosticId(id, user);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.hypothesisEntityToHypothesisDto(hypothesis));
    }

    @GetMapping("/{id}/closedQuestion")
    private ResponseEntity<List<Question>> getDiagnosticClosedQuestion(@PathVariable Long id) {
        List<Question> questions = this.diagnosticService.getDiagnosticClosedQuestion(id);
        return ResponseEntity.ok().body(questions);
    }

    @GetMapping("/{id}/prescription/{type}")
    private ResponseEntity<List<Prescription>> getDiagnosticPrescription(@PathVariable Long id, @PathVariable String type) {
        List<Prescription> prescriptions = this.diagnosticService.getDiagnosticPrescription(id, type.toUpperCase());
        return ResponseEntity.ok().body(prescriptions);
    }

    @GetMapping("/{id}/exam/{type}")
    private ResponseEntity<List<Action>> getDiagnosticExam(@PathVariable Long id, @PathVariable String type) {
        List<Action> exams = this.diagnosticService.getDiagnosticExam(id);
        return ResponseEntity.ok().body(exams);
    }

    @GetMapping("/{id}/openedQuestion")
    private ResponseEntity<List<Question>> getDiagnosticOpenedQuestion(@PathVariable Long id) {
        List<Question> questions = this.diagnosticService.getDiagnosticOpenedQuestion(id);
        return ResponseEntity.ok().body(questions);
    }

    @PostMapping("/start")
    private ResponseEntity<DiagnosticDto> startDiagnosticOfVirtualPatient(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody StartDiagnosticDto startDiagnosticDto
    ) {
        Diagnostic diagnostic = this.diagnosticService.startDiagnosticOfVirtualPatient(user, startDiagnosticDto.getVirtualPatientId());
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.diagnosticEntityToDiagnosticDto(diagnostic));
    }

    @PostMapping("/{id}/event")
    private ResponseEntity<List<EventDto>> addEventToDiagnostic(
            @PathVariable Long id,
            @Valid @RequestBody CreateEventDto createEventDto
    ) {
        List<Event> events = this.diagnosticService.addEventToDiagnostic(id, createEventDto);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.eventEntityToEventDto(events));
    }

    @PostMapping("/{id}/interpretation")
    private ResponseEntity<List<InterpretationDto>> addInterpretationToDiagnostic(
            @PathVariable Long id,
            @Valid @RequestBody CreateInterpretationDto createInterpretationDto
    ) {
        List<Interpretation> interpretations = this.reasoningService.addInterpretationToDiagnostic(id, createInterpretationDto);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.interpretationEntityToInterpretationDto(interpretations));
    }

    @PostMapping("/{id}/syndrom")
    private ResponseEntity<List<SyndromDto>> addSyndromToDiagnostic(
            @PathVariable Long id,
            @Valid @RequestBody CreateSyndromDto createSyndromDto
    ) {
        List<Syndrom> syndroms = this.reasoningService.addSyndromToDiagnostic(id, createSyndromDto);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.syndromEntityToSyndromDto(syndroms));
    }

    @PostMapping("/{id}/hypothesis")
    private ResponseEntity<List<HypothesisDto>> addHypothesisToDiagnostic(
            @PathVariable Long id,
            @Valid @RequestBody CreateHypothesisDto createHypothesisDto
    ) {
        List<Hypothesis> hypothesis = this.reasoningService.addHypothesisToDiagnostic(id, createHypothesisDto);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.hypothesisEntityToHypothesisDto(hypothesis));
    }

    @PostMapping("/{id}/validation")
    private ResponseEntity<DiagnosticDto> addValidationToDiagnostic(
            @PathVariable Long id,
            @Valid @RequestBody ValidateDiagnosticDto validateDiagnosticDto
    ) {
        Diagnostic diagnostic = this.reasoningService.validateDiagnostic(id, validateDiagnosticDto);
        return ResponseEntity.ok().body(diagnosticEntityDtoMapper.diagnosticEntityToDiagnosticDto(diagnostic));
    }

}