package fr.imt.raimed2.diagnostic.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateInterpretationDto {

    @NotBlank
    private String value;

    @NotNull
    private UUID actionId;

    @NotBlank
    private String primaryElement;

}
