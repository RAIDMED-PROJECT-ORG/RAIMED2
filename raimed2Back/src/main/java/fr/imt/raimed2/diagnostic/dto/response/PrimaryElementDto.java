package fr.imt.raimed2.diagnostic.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PrimaryElementDto {

    String value;

    UUID actionId;

}
