package fr.imt.raimed2.action.model;

import fr.imt.raimed2.prescription.model.Prescription;
import fr.imt.raimed2.symptome.model.Symptome;
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
@DiscriminatorValue("SYMPTOME")
public class ActionSymptome extends Action {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "symptome_id")
    private Symptome symptome;
}
