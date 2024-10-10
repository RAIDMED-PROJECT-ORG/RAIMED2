package fr.imt.raimed2.diagnostic.model;


import fr.imt.raimed2.user.model.User;
import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "diagnostic")
@Table(name = "raimed_diagnostic")
public class Diagnostic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DiagnosticStatus status;

    @ManyToOne
    @JoinColumn(name = "virtual_patient_id", nullable = false)
    private VirtualPatient virtualPatient;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

}
