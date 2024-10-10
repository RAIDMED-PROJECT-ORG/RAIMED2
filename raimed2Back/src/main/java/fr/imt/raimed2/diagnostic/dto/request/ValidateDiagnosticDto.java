package fr.imt.raimed2.diagnostic.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ValidateDiagnosticDto {

    @Min(0)
    @Max(100)
    @NotNull
    private Integer degreeOfCertainty;

    @NotNull
    private UUID hypothesisId;

}
