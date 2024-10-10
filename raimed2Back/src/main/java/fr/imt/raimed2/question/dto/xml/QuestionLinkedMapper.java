package fr.imt.raimed2.question.dto.xml;

import fr.imt.raimed2.question.model.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionLinkedMapper {
    Question questionLinkedDtoToQuestion(QuestionLinkedDTO questionLinkedDTO);
}
