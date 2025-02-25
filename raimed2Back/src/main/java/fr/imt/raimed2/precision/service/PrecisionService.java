package fr.imt.raimed2.precision.service;

import fr.imt.raimed2.precision.dto.xml.PrecisionDTO;
import fr.imt.raimed2.precision.dto.xml.PrecisionMapper;
import fr.imt.raimed2.precision.model.Precision;
import fr.imt.raimed2.precision.repository.PrecisionRepository;
import fr.imt.raimed2.prescription.model.Prescription;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrecisionService {
    private final PrecisionRepository precisionRepository;
    private final PrecisionMapper precisionMapper;

    public Precision getPrecisionByQuestionAndAnswer(String question, String answer) {
        if(StringUtils.isEmpty(question) || StringUtils.isEmpty(answer)){
            return null;
        }
        return precisionRepository.findOneByQuestionAndAnswer(question, answer);
    }

    public Precision save(PrecisionDTO precisionDTO) {
        Precision precisionToSave = null;
        precisionToSave = precisionRepository.save(precisionMapper.precisionDTOtoPrecision(precisionDTO));
        return precisionRepository.save(precisionMapper.precisionDTOtoPrecision(precisionDTO));
    }
}
