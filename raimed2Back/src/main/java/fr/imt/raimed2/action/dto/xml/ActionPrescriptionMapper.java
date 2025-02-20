package fr.imt.raimed2.action.dto.xml;

import fr.imt.raimed2.action.model.ActionPrescription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionPrescriptionMapper {
    ActionPrescription actionPrescriptionDTOtoDao(ActionPrescriptionDTO actionPrescriptionDTO);
}
