package fr.imt.raimed2.prescription.service;

import fr.imt.raimed2.prescription.dto.xml.PrescriptionDTO;
import fr.imt.raimed2.prescription.dto.xml.PrescriptionLinkedMapper;
import fr.imt.raimed2.prescription.model.Prescription;
import fr.imt.raimed2.prescription.repository.PrescriptionRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionLinkedMapper prescriptionLinkedMapper;

    public Prescription getPrescriptionByContentAndResult(String content, String result){
        if(StringUtils.isEmpty(content) || StringUtils.isEmpty(result)){
            return null;
        }
        return prescriptionRepository.findOneByContentAndResult(content, result);
    }

    public Prescription save(PrescriptionDTO prescriptionDTO) {
        Prescription prescriptionToSave = null;
        prescriptionToSave = prescriptionRepository.save(prescriptionLinkedMapper.prescriptionDTOToPrescription(prescriptionDTO));
        return prescriptionToSave;
    }
}
