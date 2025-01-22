package fr.imt.raimed2.diagnostic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.imt.raimed2.action.model.Action;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("INTERPRETATION")
public class Interpretation extends Reasoning {

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "action_id")
    private Action action;

}
