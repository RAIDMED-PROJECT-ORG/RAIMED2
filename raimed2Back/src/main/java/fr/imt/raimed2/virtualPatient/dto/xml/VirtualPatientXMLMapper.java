package fr.imt.raimed2.virtualPatient.dto.xml;

import fr.imt.raimed2.action.dto.xml.ActionDtoMapper;
import fr.imt.raimed2.question.dto.xml.QuestionLinkedMapper;
import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ActionDtoMapper.class)
public interface VirtualPatientXMLMapper {

    @Mapping(target = "actions", source = "actions.action")
    VirtualPatient virtualPatientXMLToDao(VirtualPatientDTO virtualPatientDTO);
}
