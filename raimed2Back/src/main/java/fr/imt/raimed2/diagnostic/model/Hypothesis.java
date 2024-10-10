package fr.imt.raimed2.diagnostic.model;

import fr.imt.raimed2.action.model.Action;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("HYPOTHESIS")
public class Hypothesis extends Reasoning {

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

    @ManyToMany
    @JoinTable(
        name = "reasoning_linked_syndroms",
        joinColumns = @JoinColumn(name = "reasoning_id"),
        inverseJoinColumns = @JoinColumn(name = "syndrom_id"))
    private Set<Syndrom> syndroms;

}
