package fr.imt.raimed2.prescription.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "result")
    private String result;
}
