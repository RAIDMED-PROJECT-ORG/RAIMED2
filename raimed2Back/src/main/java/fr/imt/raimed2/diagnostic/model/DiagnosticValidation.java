package fr.imt.raimed2.diagnostic.model;

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
@DiscriminatorValue("DIAGNOSTIC_VALIDATION")
public class DiagnosticValidation extends Reasoning {

    @Column(name = "degree_of_certainty")
    private Integer degreeOfCertainty;

    @OneToOne
    @JoinColumn(name = "hypothesis_id")
    private Hypothesis hypothesis;

}
