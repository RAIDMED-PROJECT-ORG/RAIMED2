package fr.imt.raimed2.precision.repository;

import fr.imt.raimed2.precision.model.Precision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrecisionRepository extends JpaRepository<Precision, Long> {
    Precision findOneByQuestionAndAnswer(String question, String answer);
}
