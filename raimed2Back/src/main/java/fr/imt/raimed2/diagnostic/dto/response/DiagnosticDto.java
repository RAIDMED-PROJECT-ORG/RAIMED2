package fr.imt.raimed2.diagnostic.dto.response;

import fr.imt.raimed2.diagnostic.model.DiagnosticStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DiagnosticDto {

    private Long id;

    private Date startDate;

    private UUID userId;

    private DiagnosticStatus status;

    private Long virtualPatientId;

}
