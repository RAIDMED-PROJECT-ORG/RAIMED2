package fr.imt.raimed2.precision.dto.xml;


import fr.imt.raimed2.precision.model.Precision;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrecisionMapper {
    Precision precisionDTOtoPrecision(PrecisionDTO precisionDTO);
}
