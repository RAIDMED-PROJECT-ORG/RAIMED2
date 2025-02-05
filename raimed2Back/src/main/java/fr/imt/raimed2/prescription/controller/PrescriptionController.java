package fr.imt.raimed2.prescription.controller;

import fr.imt.raimed2.prescription.dto.request.CreatePrescriptionDTO;
import fr.imt.raimed2.prescription.dto.request.UpdatePrescriptionDTO;
import fr.imt.raimed2.prescription.model.Prescription;
import fr.imt.raimed2.prescription.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescription(@RequestParam UUID teacherId) {
        if (teacherId == null) return ResponseEntity.ok().body(prescriptionService.getAllPrescription(null, null, true));
        else return ResponseEntity.ok().body(prescriptionService.getAllPrescription(null, teacherId, false));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody CreatePrescriptionDTO createPrescriptionDTO) {
        return ResponseEntity.ok().body(this.prescriptionService.createPrescription(createPrescriptionDTO));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/multiples")
    public ResponseEntity<List<Prescription>> createMultiplePrescriptions(@RequestBody List<CreatePrescriptionDTO> createPrescriptionDTOs) {
        return ResponseEntity.ok().body(this.prescriptionService.createMultiplePrescriptions(createPrescriptionDTOs));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable Long id,
            @RequestBody UpdatePrescriptionDTO updatePrescriptionDTO
    ) {
        return ResponseEntity.ok().body(this.prescriptionService.updatePrescription(id, updatePrescriptionDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable Long id) {
        this.prescriptionService.deletePrescription(id);
    }
}