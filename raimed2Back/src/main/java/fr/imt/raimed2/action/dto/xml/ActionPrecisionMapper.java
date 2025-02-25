package fr.imt.raimed2.action.dto.xml;

import fr.imt.raimed2.action.model.ActionPrecision;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionPrecisionMapper {
    ActionPrecision actionPrecisionDTOtoDao(ActionPrecisionDTO actionPrecisionDTO);
}
