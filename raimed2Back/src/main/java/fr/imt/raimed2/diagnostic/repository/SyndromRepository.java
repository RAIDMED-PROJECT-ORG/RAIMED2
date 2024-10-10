package fr.imt.raimed2.diagnostic.repository;

import fr.imt.raimed2.diagnostic.model.Syndrom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SyndromRepository extends JpaRepository<Syndrom, UUID> {

    List<Syndrom> findAllByDiagnosticId(Long diagnosticId);

}
