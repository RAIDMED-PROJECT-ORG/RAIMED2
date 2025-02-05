package fr.imt.raimed2.prescription.repository;

import fr.imt.raimed2.prescription.model.Prescription;
import fr.imt.raimed2.prescription.model.PrescriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findAllByIsMutualTrue();

    List<Prescription> findAllByType(PrescriptionType type);

    List<Prescription> findAllByIsMutualTrueAndType(PrescriptionType type);

    List<Prescription> findAllByTeacherId(Long teacherId);

    List<Prescription> findAllByTeacherIdAndType(Long teacherId, PrescriptionType type);
}
