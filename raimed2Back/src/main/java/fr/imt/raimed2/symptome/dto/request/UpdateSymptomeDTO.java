package fr.imt.raimed2.symptome.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSymptomeDTO {
    @NotNull(message = "Question is required")
    String question;

    @NotNull(message = "Answer is required")
    String answer;
}
