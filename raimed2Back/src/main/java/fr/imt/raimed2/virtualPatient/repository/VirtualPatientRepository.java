package fr.imt.raimed2.virtualPatient.repository;

import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VirtualPatientRepository extends JpaRepository<VirtualPatient, Long> {
    @EntityGraph(attributePaths = {"actions"})
    Optional<VirtualPatient> findById(Long id);
}
