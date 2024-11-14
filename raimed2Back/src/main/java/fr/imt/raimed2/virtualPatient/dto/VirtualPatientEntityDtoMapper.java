package fr.imt.raimed2.virtualPatient.dto;

import fr.imt.raimed2.virtualPatient.dto.response.VirtualPatientDto;
import fr.imt.raimed2.virtualPatient.dto.response.VirtualPatientListDto;
import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public interface VirtualPatientEntityDtoMapper {

    @Mapping(target = "createdBy", source = "createdBy.id")
    VirtualPatientDto virtualPatientEntityToVirtualPatientDto(VirtualPatient virtualPatient);
    // java(virtualPatient.getCreatedBy() != null " + " ? virtualPatient.getCreatedBy().getFirstname() + " " + virtualPatient.getCreatedBy().getLastname() : "XML Import")
    @Mapping(target = "createdBy", expression = "java(virtualPatient.getCreatedBy() != null ? virtualPatient.getCreatedBy().getFirstname() + \" \" + virtualPatient.getCreatedBy().getLastname() : \"XML Import\")")
    VirtualPatientListDto virtualPatientEntityToVirtualPatientListDto(VirtualPatient virtualPatient);
    @Mapping(target = "result", source = "result")
    List<VirtualPatientListDto> virtualPatientEntityToVirtualPatientListDto(List<VirtualPatient> virtualPatients);

}