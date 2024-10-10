package fr.imt.raimed2.diagnostic.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class HypothesisDto {

    private UUID id;

    private Date date;

    private String value;

}
