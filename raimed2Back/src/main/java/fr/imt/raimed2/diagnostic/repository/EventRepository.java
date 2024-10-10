package fr.imt.raimed2.diagnostic.repository;

import fr.imt.raimed2.diagnostic.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findAllByDiagnosticId(Long diagnosticId);

}
