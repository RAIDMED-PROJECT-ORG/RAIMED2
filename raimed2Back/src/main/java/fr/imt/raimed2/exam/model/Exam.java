package fr.imt.raimed2.exam.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "exam")
@Table(name = "raimed_exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ExamType type;

    @Column(name = "zone", nullable = false)
    private String zone;

    @ElementCollection
    @CollectionTable(name = "raimed_exam_signs", joinColumns = @JoinColumn(name = "exam_id"))
    @Column(name = "sign")
    private List<String> signs;
}
