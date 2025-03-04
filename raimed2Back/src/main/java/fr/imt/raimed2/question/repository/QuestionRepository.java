package fr.imt.raimed2.question.repository;

import fr.imt.raimed2.question.model.Filter;
import fr.imt.raimed2.question.model.Question;
import fr.imt.raimed2.question.model.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByIsMutualTrue();

    List<Question> findAllByIsMutualTrueAndFilter(Filter gender);

    List<Question> findAllByFilter(Filter gender);

    List<Question> findAllByType(QuestionType type);

    List<Question> findAllByTypeAndFilter(QuestionType type, Filter gender);

    List<Question> findAllByIsMutualTrueAndType(QuestionType type);

    List<Question> findAllByIsMutualTrueAndTypeAndFilter(QuestionType type, Filter gender);

    List<Question> findAllByTeacherId(UUID teacherId);

    List<Question> findAllByTeacherIdAndFilter(UUID teacherId, Filter gender);

    List<Question> findAllByTeacherIdAndType(UUID teacherId, QuestionType type);

    List<Question> findAllByTeacherIdAndTypeAndFilter(UUID teacherId, QuestionType type, Filter filter);

    Question findOneByContent(String content);
}
