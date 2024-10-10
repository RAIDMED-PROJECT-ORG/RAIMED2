package fr.imt.raimed2.diagnostic.repository;

import fr.imt.raimed2.diagnostic.model.DiagnosticValidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticValidationRepository extends JpaRepository<DiagnosticValidation, Long> {
}
