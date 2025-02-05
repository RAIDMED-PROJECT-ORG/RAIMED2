package fr.imt.raimed2.prescription.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "prescription")
@Table(name = "raimed_prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PrescriptionType type;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "result")
    private String result;

    @Column(name = "teacher_id")
    private UUID teacherId;

    @Column(name = "is_mutual")
    private boolean isMutual;

}
