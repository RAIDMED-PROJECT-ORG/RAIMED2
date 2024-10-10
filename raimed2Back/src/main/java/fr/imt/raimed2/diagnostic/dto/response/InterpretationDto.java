package fr.imt.raimed2.diagnostic.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InterpretationDto {

    private UUID id;

    private String value;

    private UUID actionId;

}
