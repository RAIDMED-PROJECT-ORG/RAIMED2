package fr.imt.raimed2.virtualPatient.dto.response;

import fr.imt.raimed2.action.model.Action;
import fr.imt.raimed2.user.model.User;
import fr.imt.raimed2.virtualPatient.model.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class VirtualPatientDto {

    private Long id;

    private Integer age;

    private Gender gender;

    private Date createdAt;

    private UUID createdBy;

    private String result;

    private List<Action> actions;

}