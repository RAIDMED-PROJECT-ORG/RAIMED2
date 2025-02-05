package fr.imt.raimed2.prescription.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePrescriptionDTO {

    @NotNull(message = "Prescription type is required")
    private String type;

    @NotNull(message = "Prescription name is required")
    private String name;

    @NotNull(message = "Prescription result is required")
    private String result;
}
