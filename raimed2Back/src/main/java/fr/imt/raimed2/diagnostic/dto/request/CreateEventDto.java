package fr.imt.raimed2.diagnostic.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEventDto {

    private String typeAction;

    private UUID actionId;

}
