package fr.imt.raimed2.action.dto.xml;

import fr.imt.raimed2.action.model.Action;
import fr.imt.raimed2.action.model.ActionClosedQuestion;
import fr.imt.raimed2.question.dto.xml.QuestionLinkedMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",uses = QuestionLinkedMapper.class)
public interface ActionDtoMapper {

    @Mapping(target = "closedAnswer", source = "actionClosedQuestionDTO.closedAnswer")
    @Mapping(target = "question", source = "actionClosedQuestionDTO.questionLinked")
    ActionClosedQuestion actionDtoToAction(ActionDTO actionDTO);
}
