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
@DiscriminatorValue("OPENED_QUESTION")
public class ActionOpenedQuestion extends Action {

    @Column(name = "opened_answer")
    private String openedAnswer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}
