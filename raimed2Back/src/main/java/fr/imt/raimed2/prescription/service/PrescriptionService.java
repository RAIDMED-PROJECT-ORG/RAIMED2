// PrescriptionService.java
package fr.imt.raimed2.prescription.service;

import fr.imt.raimed2.prescription.dto.request.CreatePrescriptionDTO;
import fr.imt.raimed2.prescription.dto.request.UpdatePrescriptionDTO;
import fr.imt.raimed2.prescription.dto.xml.PrescriptionDTO;
import fr.imt.raimed2.prescription.dto.xml.PrescriptionMapper;
import fr.imt.raimed2.prescription.model.Prescription;
import fr.imt.raimed2.prescription.repository.PrescriptionRepository;
import fr.imt.raimed2.prescription.model.PrescriptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;

    public List<Prescription> getAllPrescription(@Nullable PrescriptionType prescriptionType) {
        if (prescriptionType == null){
            return prescriptionRepository.findAll();
        }
        return prescriptionRepository.findAllByType(prescriptionType);
    }

    /**
     * Get all the prescriptions
     * @param prescriptionType The type of the prescription
     * @return The list of prescriptions
     */
    public List<Prescription> getAllPrescription(@Nullable PrescriptionType prescriptionType, Long teacherId, boolean admin) {
        List<Prescription> prescriptionsList;

        if (admin) return prescriptionRepository.findAll();

        if (prescriptionType == null){
            prescriptionsList = prescriptionRepository.findAllByIsMutualTrue();
            prescriptionsList.addAll(prescriptionRepository.findAllByTeacherId(teacherId));
        }
        else {
            prescriptionsList = prescriptionRepository.findAllByIsMutualTrueAndType(prescriptionType);
            prescriptionsList.addAll(prescriptionRepository.findAllByTeacherIdAndType(teacherId, prescriptionType));
        }
        return prescriptionsList;
    }

    /**
     * Create a prescription and save it in the database
     * @param createPrescriptionDto Dto containing the information of the prescription
     * @return The created prescription
     */
    public Prescription createPrescription(CreatePrescriptionDTO createPrescriptionDto) {
        Prescription createPrescription = Prescription.builder()
                .type(PrescriptionType.valueOf(createPrescriptionDto.getType()))
                .name(createPrescriptionDto.getName())
                .result(createPrescriptionDto.getResult())
                .teacherId(createPrescriptionDto.getTeacherId())
                .isMutual(createPrescriptionDto.isMutual())
                .build();
        return prescriptionRepository.save(createPrescription);
    }

    /**
     * Create multiple prescriptions
     * @param createPrescriptionDTOs The DTOs containing the information of the prescriptions
     * @return The list of created prescriptions
     */
    public List<Prescription> createMultiplePrescriptions(List<CreatePrescriptionDTO> createPrescriptionDTOs) {
        List<Prescription> prescriptionList = new ArrayList<>();
        for (CreatePrescriptionDTO createPrescriptionDTO : createPrescriptionDTOs) {
            prescriptionList.add(this.createPrescription(createPrescriptionDTO));
        }
        return prescriptionList;
    }

    /**
     * Delete a prescription by its id
     * @param id The id of the prescription
     */
    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    /**
     * Update a prescription
     * @param id The id of the prescription
     * @param updatePrescriptionDTO DTO containing the information of the prescription
     * @return The updated prescription
     * @throws NoSuchElementException If the prescription does not exist
     */
    public Prescription updatePrescription(Long id, UpdatePrescriptionDTO updatePrescriptionDTO) throws NoSuchElementException {
        Prescription prescription = prescriptionRepository.findById(id).orElseThrow();
        prescription.setType(PrescriptionType.valueOf(updatePrescriptionDTO.getType()));
        prescription.setName(updatePrescriptionDTO.getName());
        prescription.setResult(updatePrescriptionDTO.getResult());
        return prescriptionRepository.save(prescription);
    }

    /**
     * Save a prescription
     * @param prescriptionDTO The DTO containing the information of the prescription
     * @return The saved prescription
     */
    public Prescription save(PrescriptionDTO prescriptionDTO) {
        List<Prescription> prescriptions = this.getAllPrescription(null);

        boolean isAlreadyInThePrescriptions = prescriptions.stream().anyMatch(prescription -> prescription.getName().equals(prescriptionDTO.getName()));
        Prescription prescriptionToSave = null;

        if(!isAlreadyInThePrescriptions){
            prescriptionToSave = prescriptionRepository.save(prescriptionMapper.prescriptionLinkedDtoToPrescription(prescriptionDTO));
        } else {
            Optional<Prescription> prescriptionOptional = prescriptions.stream().filter(prescription -> prescription.getName().equals(prescriptionDTO.getName())).findFirst();
            if (prescriptionOptional.isPresent()) {
                prescriptionToSave = prescriptionOptional.get();
            }
        }
        return prescriptionToSave;
    }

}