package fr.imt.raimed2.prescription.dto.request;

import fr.imt.raimed2.prescription.model.PrescriptionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePrescriptionDTO {

    @NotNull(message = "Content is required")
    String content;

    @NotNull(message = "Result is required")
    String result;

    @NotNull(message = "Prescription type is required")
    PrescriptionType prescriptionType;

}
