package fr.imt.raimed2.diagnostic.model;

import fr.imt.raimed2.action.model.Action;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("SYNDROM")
public class Syndrom extends Reasoning {

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToMany
    @JoinTable(
        name = "reasoning_linked_actions",
        joinColumns = @JoinColumn(name = "reasoning_id"),
        inverseJoinColumns = @JoinColumn(name = "action_id"))
    private Set<Action> actions;

    @ManyToMany
    @JoinTable(
        name = "reasoning_linked_interpretations",
        joinColumns = @JoinColumn(name = "reasoning_id"),
        inverseJoinColumns = @JoinColumn(name = "interpretation_id"))
    private Set<Interpretation> interpretations;

}
