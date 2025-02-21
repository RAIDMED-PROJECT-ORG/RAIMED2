package fr.imt.raimed2.exam.dto.xml;

import fr.imt.raimed2.exam.model.Exam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExamLinkedMapper {
    Exam examDTOToExam(ExamDTO examDTO);
}