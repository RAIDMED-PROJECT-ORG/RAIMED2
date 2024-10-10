package fr.imt.raimed2.diagnostic.repository;

import fr.imt.raimed2.diagnostic.model.Interpretation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InterpretationRepository  extends JpaRepository<Interpretation, UUID> {

    List<Interpretation> findAllByDiagnosticId(Long diagnosticId);

}
