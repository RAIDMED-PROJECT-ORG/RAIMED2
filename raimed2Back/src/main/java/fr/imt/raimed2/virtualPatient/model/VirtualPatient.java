package fr.imt.raimed2.virtualPatient.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JsonIgnore
    @OneToMany(mappedBy = "virtualPatient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Action> actions;

    @JsonIgnore
    @OneToMany(mappedBy = "virtualPatient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Diagnostic> diagnostics;

    public String toString() {
        return "VirtualPatient(id=" + this.getId() + ", age=" + this.getAge() + ")";
    }
}
