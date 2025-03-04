package fr.imt.raimed2.action.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.imt.raimed2.diagnostic.model.Event;
import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "action")
@Table(name = "raimed_action")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Action {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "primary_element", nullable = true)
    private String primaryElement;

    @Column(name="type", nullable = false, updatable = false, insertable = false)
    private String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "virtual_patient_id", nullable = false)
    private VirtualPatient virtualPatient;

    @OneToMany(mappedBy = "action", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

}
