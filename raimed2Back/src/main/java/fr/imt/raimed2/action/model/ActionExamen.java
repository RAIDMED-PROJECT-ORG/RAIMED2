package fr.imt.raimed2.action.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("EXAMEN")
public class ActionExamen extends Action {

    @Column(name = "zone")
    private String zone;

    @Column(name = "signs")
    private String signs;
}