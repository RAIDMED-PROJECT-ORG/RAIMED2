package fr.imt.raimed2.action.repository;

import fr.imt.raimed2.action.model.Action;
import fr.imt.raimed2.action.model.ActionPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActionRepository extends JpaRepository<Action, UUID> {

    List<Action> findAllByVirtualPatientIdAndType(Long virtualPatientId, String type);

    List<Action> findAllByVirtualPatientIdAndTypeAndPrimaryElement(Long virtualPatientId, String type, String primaryElement);

    List<Action> findAllByIdIn(List<UUID> ids);
}
