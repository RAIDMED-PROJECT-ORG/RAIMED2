package fr.imt.raimed2.diagnostic.dto.response;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class SyndromDto {

    private UUID id;

    private Date date;

    private String value;

}
