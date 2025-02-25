package fr.imt.raimed2.precision.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePrecisionDTO {
    @NotNull(message = "Question is required")
    String question;

    @NotNull(message = "Answer is required")
    String answer;
}
