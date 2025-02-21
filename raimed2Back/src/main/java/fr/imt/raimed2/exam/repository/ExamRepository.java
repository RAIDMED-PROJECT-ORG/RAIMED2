package fr.imt.raimed2.exam.repository;

import fr.imt.raimed2.exam.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query("SELECT e FROM exam e JOIN e.signs s WHERE e.zone = :zone AND s IN :signs")
    Exam findByZoneAndSigns(@Param("zone") String zone, @Param("signs") List<String> signs);
}
