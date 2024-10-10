package fr.imt.raimed2.exception.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Raimed2ErrorDetailsDto {

    private final String message;

    private final String details;

    private final String path;

}
