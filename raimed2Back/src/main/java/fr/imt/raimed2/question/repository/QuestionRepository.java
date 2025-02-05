package fr.imt.raimed2.question.repository;

import fr.imt.raimed2.question.model.Question;
import fr.imt.raimed2.question.model.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByIsMutualTrue();

    List<Question> findAllByType(QuestionType type);

    List<Question> findAllByIsMutualTrueAndType(QuestionType type);

    List<Question> findAllByTeacherId(UUID teacherId);

    List<Question> findAllByTeacherIdAndType(UUID teacherId, QuestionType type);
}
