package fr.imt.raimed2.prescription.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePrescriptionDTO {

    @NotNull(message = "Prescription type is required")
    private String type;

    @NotNull(message = "Prescription name is required")
    private String name;

    @NotNull(message = "Prescription result is required")
    private String result;

    UUID teacherId;

    boolean isMutual;
}
