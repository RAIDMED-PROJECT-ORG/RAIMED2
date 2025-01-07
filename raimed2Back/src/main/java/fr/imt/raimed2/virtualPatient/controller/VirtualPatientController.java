package fr.imt.raimed2.virtualPatient.controller;

import fr.imt.raimed2.action.dto.request.CreateActionSpontaneousPatientSpeech;
import fr.imt.raimed2.action.service.ActionService;
import fr.imt.raimed2.virtualPatient.dto.VirtualPatientEntityDtoMapper;
import fr.imt.raimed2.virtualPatient.dto.response.VirtualPatientDto;
import fr.imt.raimed2.virtualPatient.dto.response.VirtualPatientListDto;
import fr.imt.raimed2.virtualPatient.dto.xml.VirtualPatientDTO;
import fr.imt.raimed2.virtualPatient.service.VirtualPatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/virtual-patient")
@RequiredArgsConstructor
public class VirtualPatientController {

    private final VirtualPatientService virtualPatientService;

    private final ActionService actionService;

    private final VirtualPatientEntityDtoMapper virtualPatientEntityDtoMapper;


    @GetMapping
    public List<VirtualPatientListDto> getAllVirtualPatient() {
        List<fr.imt.raimed2.virtualPatient.model.VirtualPatient> virtualPatients = virtualPatientService.getAllVirtualPatient();
        return virtualPatientEntityDtoMapper.virtualPatientEntityToVirtualPatientListDto(virtualPatients);
    }

    @GetMapping("/{id}")
    public VirtualPatientDto getVirtualPatientById(@PathVariable Long id) {
        fr.imt.raimed2.virtualPatient.model.VirtualPatient virtualPatient = virtualPatientService.getVirtualPatientById(id);
        return virtualPatientEntityDtoMapper.virtualPatientEntityToVirtualPatientDto(virtualPatient);
    }

    //@PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping("/{id}/action")
    public void addSpontaneousPatientSpeechActionToVirtualPatient(
            @PathVariable Long id,
            @Valid @RequestBody CreateActionSpontaneousPatientSpeech createActionSpontaneousPatientSpeech
    ) {
        actionService.addSpontaneousPatientSpeechAction(id, createActionSpontaneousPatientSpeech);
    }

    @PostMapping("/xml")
    public void addVirtualPatientXML(@RequestBody VirtualPatientDTO virtualPatientDTO){
        System.out.println(virtualPatientDTO);
        virtualPatientService.addVirtualPatientXML(virtualPatientDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteVirtualPatientById(@PathVariable Long id) {
        try{
            return virtualPatientService.deleteVirtualPatientById(id);
        }catch (NoSuchElementException ex){
            return false;
        }
    }

}

