package fr.imt.raimed2.prescription.repository;

import fr.imt.raimed2.prescription.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Prescription findOneByContentAndResult(String content, String result);
}
