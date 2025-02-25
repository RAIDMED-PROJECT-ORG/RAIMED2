package fr.imt.raimed2.symptome.dto.xml;


import fr.imt.raimed2.symptome.model.Symptome;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SymptomeMapper {
    Symptome symptomeDTOtoSymptome(SymptomeDTO symptomeDTO);
}
