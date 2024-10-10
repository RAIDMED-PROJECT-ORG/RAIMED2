package fr.imt.raimed2.action.repository;

import fr.imt.raimed2.action.model.ActionClosedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActionClosedQuestionRepository extends JpaRepository<ActionClosedQuestion, UUID>{

    List<ActionClosedQuestion> findAllByIdIn(List<UUID> ids);

    List<ActionClosedQuestion> findAllByVirtualPatientId(Long virtualPatientId);
}