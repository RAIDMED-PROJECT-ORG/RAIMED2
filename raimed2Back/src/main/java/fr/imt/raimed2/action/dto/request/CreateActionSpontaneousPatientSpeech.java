package fr.imt.raimed2.action.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateActionSpontaneousPatientSpeech {

    @NotBlank(message = "Patient speech is required")
    public String speech;

    @NotBlank(message = "Primary element is required")
    public String primaryElement;

}
