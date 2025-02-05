package fr.imt.raimed2.action.repository;

import fr.imt.raimed2.action.model.ActionPrescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActionPrescriptionRepository extends JpaRepository<ActionPrescription, UUID> {

    List<ActionPrescription> findAllByIdIn(List<UUID> ids);

    List<ActionPrescription> findAllByVirtualPatientId(Long virtualPatientId);
}
