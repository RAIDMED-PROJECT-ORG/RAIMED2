package fr.imt.raimed2.action.dto.xml;

import fr.imt.raimed2.action.model.ActionPrescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActionPrescriptionMapper {
    @Mapping(target = "prescription", source = "prescriptionDTO")
    ActionPrescription actionPrescriptionDtoToDao(ActionPrescriptionDTO actionPrescriptionDTO);
}
