package fr.imt.raimed2.diagnostic.model;

import fr.imt.raimed2.action.model.Action;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "event")
@Table(name = "raimed_event")
public class Event {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "diagnostic_id", nullable = false)
    private Diagnostic diagnostic;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false)
    private Action action;

}
