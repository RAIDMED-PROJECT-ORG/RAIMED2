package fr.imt.raimed2.action.repository;

import fr.imt.raimed2.action.model.ActionPrecision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActionPrecisionRepository extends JpaRepository<ActionPrecision, UUID> {
}
