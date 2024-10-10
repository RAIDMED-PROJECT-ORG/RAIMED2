package fr.imt.raimed2.question.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "question")
@Table(name = "raimed_question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private QuestionType type;

    @Column(name = "content", nullable = false, unique = true)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "filter", nullable = false)
    private Filter filter;

}
