package fr.imt.raimed2.action.dto.xml;

import fr.imt.raimed2.action.model.ActionClosedQuestion;
import fr.imt.raimed2.action.model.ActionSpontaneousPatientSpeech;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActionSpontaneousPatientSpeechMapper {
    ActionSpontaneousPatientSpeech actionSpontaneousPatientSpeechDtoToDao(ActionSpontaneousPatientSpeechDTO actionSpontaneousPatientSpeechDTO);

}
