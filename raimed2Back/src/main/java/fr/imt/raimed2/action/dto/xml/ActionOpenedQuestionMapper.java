package fr.imt.raimed2.action.dto.xml;

import fr.imt.raimed2.action.model.ActionOpenedQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActionOpenedQuestionMapper {
    @Mapping(target = "question", source = "questionLinked")
    ActionOpenedQuestion actionOpenedQuestionDtoToDao(ActionOpenedQuestionDTO actionOpenedQuestionDTO);

}
