package fr.imt.raimed2.diagnostic.dto.response;

import fr.imt.raimed2.diagnostic.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiagnosticEntityDtoMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "virtualPatient.id", target = "virtualPatientId")
    DiagnosticDto diagnosticEntityToDiagnosticDto(Diagnostic diagnostic);
    List<DiagnosticDto> diagnosticEntityToDiagnosticDto(List<Diagnostic> diagnostic);

    EventDto eventEntityToEventDto(Event event);

    List<EventDto> eventEntityToEventDto(List<Event> event);

    @Mapping(source = "action.id", target = "actionId")
    InterpretationDto interpretationEntityToInterpretationDto(Interpretation interpretation);

    List<InterpretationDto> interpretationEntityToInterpretationDto(List<Interpretation> interpretation);

    SyndromDto syndromEntityToSyndromDto(Syndrom syndrom);

    List<SyndromDto> syndromEntityToSyndromDto(List<Syndrom> syndrom);

    HypothesisDto hypothesisEntityToHypothesisDto(Hypothesis hypothesis);

    List<HypothesisDto> hypothesisEntityToHypothesisDto(List<Hypothesis> hypothesis);
}
