package fr.imt.raimed2.action.model;

import fr.imt.raimed2.precision.model.Precision;
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
@DiscriminatorValue("PRECISION")
public class ActionPrecision extends Action {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "precision_id")
    private Precision precision;
}
