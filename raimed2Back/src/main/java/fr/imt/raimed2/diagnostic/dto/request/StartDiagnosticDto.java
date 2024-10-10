package fr.imt.raimed2.diagnostic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class StartDiagnosticDto {

    @NotNull
    Long virtualPatientId;

}
