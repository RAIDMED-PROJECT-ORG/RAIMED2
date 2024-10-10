package fr.imt.raimed2.diagnostic.repository;

import fr.imt.raimed2.diagnostic.model.Hypothesis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HypothesisRepository extends JpaRepository<Hypothesis, UUID> {

    List<Hypothesis> findAllByDiagnosticId(Long diagnosticId);

}
