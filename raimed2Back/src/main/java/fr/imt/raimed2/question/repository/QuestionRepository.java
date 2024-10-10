package fr.imt.raimed2.question.repository;

import fr.imt.raimed2.question.model.Question;
import fr.imt.raimed2.question.model.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByType(QuestionType type);

}
