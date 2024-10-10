package fr.imt.raimed2.diagnostic.repository;

import fr.imt.raimed2.diagnostic.model.Diagnostic;
import fr.imt.raimed2.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnosticRepository extends JpaRepository<Diagnostic, Long> {

    List<Diagnostic> findAllByUser(User user);

}
