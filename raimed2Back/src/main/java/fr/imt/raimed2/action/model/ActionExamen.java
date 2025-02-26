package fr.imt.raimed2.action.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // Une seule table pour toutes les sous-classes
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class ActionExamen extends Action {

    @Column(name = "zone")
    private String zone;

    @Column(name = "signs")
    private String signs;
}