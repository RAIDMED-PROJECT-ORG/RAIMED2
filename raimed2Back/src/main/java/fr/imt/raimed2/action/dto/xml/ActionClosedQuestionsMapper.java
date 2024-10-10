package fr.imt.raimed2.action.dto.xml;

import fr.imt.raimed2.action.model.ActionClosedQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActionClosedQuestionsMapper {
    @Mapping(target = "question", source = "questionLinked")
    ActionClosedQuestion actionClosedQuestionsDtoToDao(ActionClosedQuestionDTO actionClosedQuestionDTO);

}

