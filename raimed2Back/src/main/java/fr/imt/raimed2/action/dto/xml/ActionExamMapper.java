package fr.imt.raimed2.action.dto.xml;

import fr.imt.raimed2.action.model.ActionExam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionExamMapper {
    ActionExam actionExamDTOtoDao(ActionExamDTO actionExamDTO);
}
