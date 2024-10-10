package fr.imt.raimed2.diagnostic.dto.request;

import fr.imt.raimed2.diagnostic.dto.response.InterpretationDto;
import fr.imt.raimed2.diagnostic.dto.response.PrimaryElementDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CreateSyndromDto {

    @NotBlank
    private String value;

    @Size(max = 10)
    private Set<InterpretationDto> interpretations;

    @Size(max = 10)
    private Set<PrimaryElementDto> primaryElements;

}
