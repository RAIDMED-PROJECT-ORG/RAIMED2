package fr.imt.raimed2.symptome.service;

import fr.imt.raimed2.symptome.dto.xml.SymptomeDTO;
import fr.imt.raimed2.symptome.dto.xml.SymptomeMapper;
import fr.imt.raimed2.symptome.model.Symptome;
import fr.imt.raimed2.symptome.repository.SymptomeRepository;
import io.micrometer.common.util.StringUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SymptomeService {
    private final SymptomeRepository symptomeRepository;
    private final SymptomeMapper symptomeMapper;

    public Symptome getSymptomeByQuestionAndAnswer(String question, String answer) {
        if(StringUtils.isEmpty(question) || StringUtils.isEmpty(answer)){
            return null;
        }
        return symptomeRepository.findOneByQuestionAndAnswer(question, answer);
    }

    public Symptome save(SymptomeDTO symptomeDTO) {
        return symptomeRepository.save(symptomeMapper.symptomeDTOtoSymptome(symptomeDTO));
    }
}
