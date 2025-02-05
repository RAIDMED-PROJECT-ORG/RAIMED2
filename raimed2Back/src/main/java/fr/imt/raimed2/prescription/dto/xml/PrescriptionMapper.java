package fr.imt.raimed2.prescription.dto.xml;

import fr.imt.raimed2.prescription.model.Prescription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    Prescription prescriptionLinkedDtoToPrescription(PrescriptionDTO prescriptionLinkedDTO);
}
