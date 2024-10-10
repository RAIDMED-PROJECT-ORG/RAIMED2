package fr.imt.raimed2.action.repository;

import fr.imt.raimed2.action.model.ActionOpenedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActionOpenedQuestionRepository extends JpaRepository<ActionOpenedQuestion, UUID> {

    List<ActionOpenedQuestion> findAllByIdIn(List<UUID> ids);

    List<ActionOpenedQuestion> findAllByVirtualPatientId(Long virtualPatientId);
}
