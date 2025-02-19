package fr.imt.raimed2.virtualPatient.dto.response;

import fr.imt.raimed2.virtualPatient.model.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VirtualPatientListDto {

    private Long id;

    private Integer age;

    private Gender gender;

    private String createdBy;

    private String createdAt;

    private String result;

}
