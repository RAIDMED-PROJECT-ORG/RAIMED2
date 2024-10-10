package fr.imt.raimed2.action.model;

import fr.imt.raimed2.question.model.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("CLOSED_QUESTION")
public class ActionClosedQuestion extends Action {

    @Column(name = "closed_answer")
    private String closedAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
