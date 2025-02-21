package fr.imt.raimed2.exam.service;

import fr.imt.raimed2.exam.dto.xml.ExamDTO;
import fr.imt.raimed2.exam.dto.xml.ExamLinkedMapper;
import fr.imt.raimed2.exam.model.Exam;
import fr.imt.raimed2.exam.repository.ExamRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final ExamLinkedMapper examLinkedMapper;

    public Exam getPrescriptionByZoneAndSigns(String zone, List<String> signs) {
        if (StringUtils.isEmpty(zone)) {
            return null;
        }

        return examRepository.findByZoneAndSigns(zone, signs);
    }

    public Exam save(ExamDTO examDTO) {
        return examRepository.save(examLinkedMapper.examDTOToExam(examDTO));
    }
}
