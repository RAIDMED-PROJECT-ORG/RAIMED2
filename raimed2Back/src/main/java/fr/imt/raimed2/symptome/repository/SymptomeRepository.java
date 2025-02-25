package fr.imt.raimed2.symptome.repository;

import fr.imt.raimed2.symptome.model.Symptome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomeRepository extends JpaRepository<Symptome, Long> {
    Symptome findOneByQuestionAndAnswer(String question, String answer);
}
