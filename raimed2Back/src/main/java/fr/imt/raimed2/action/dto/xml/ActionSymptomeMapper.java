package fr.imt.raimed2.action.dto.xml;

import fr.imt.raimed2.action.model.ActionSymptome;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionSymptomeMapper {
    ActionSymptome actionSymptomeDTOtoDao(ActionSymptomeDTO actionSymptomeDTO);
}
