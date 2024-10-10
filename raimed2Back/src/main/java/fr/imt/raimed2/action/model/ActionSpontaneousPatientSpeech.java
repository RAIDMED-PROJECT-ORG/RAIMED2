package fr.imt.raimed2.action.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("SPONTANEOUS_PATIENT_SPEECH")
public class ActionSpontaneousPatientSpeech extends Action {

    @Column(name = "speech")
    private String speech;

}
