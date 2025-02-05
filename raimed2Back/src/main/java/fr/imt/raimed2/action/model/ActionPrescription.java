package fr.imt.raimed2.action.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.imt.raimed2.prescription.model.Prescription;
import fr.imt.raimed2.question.model.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("PRESCRIPTION")
public class ActionPrescription extends Action {

    @Column(name="result")
    private String result;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="prescription_id")
    private Prescription prescription;

}
