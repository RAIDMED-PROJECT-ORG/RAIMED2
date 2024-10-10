package fr.imt.raimed2.virtualPatient.model;

import fr.imt.raimed2.action.model.Action;
import fr.imt.raimed2.diagnostic.model.Diagnostic;
import fr.imt.raimed2.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "virtualPatient")
@Table(name = "raimed_virtual_patient")
public class VirtualPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "virtualPatient")
    private Set<Action> actions;

    @OneToMany(mappedBy = "virtualPatient")
    private Set<Diagnostic> diagnostics;

}
