package fr.imt.raimed2.action.repository;

import fr.imt.raimed2.action.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActionRepository extends JpaRepository<Action, UUID> {
    void deleteAllByVirtualPatientId(Long virtualPatientId);
}
