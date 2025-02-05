package fr.imt.raimed2.virtualPatient.service;

import fr.imt.raimed2.action.dto.xml.ActionDTO;
import fr.imt.raimed2.action.service.ActionService;
import fr.imt.raimed2.virtualPatient.dto.xml.VirtualPatientDTO;
import fr.imt.raimed2.virtualPatient.dto.xml.VirtualPatientXMLMapper;
import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import fr.imt.raimed2.virtualPatient.repository.VirtualPatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class VirtualPatientService {

    private final VirtualPatientRepository virtualPatientRepository;
    private final VirtualPatientXMLMapper virtualPatientXMLMapper;
    private final ActionService actionService;

    public List<VirtualPatient> getAllVirtualPatient() {
        return virtualPatientRepository.findAll();
    }

    public VirtualPatient getVirtualPatientById(Long id) throws NoSuchElementException {
        return virtualPatientRepository.findById(id).orElseThrow();
    }

    @Transactional
    public boolean deleteVirtualPatientById(Long id) throws NoSuchElementException {
        virtualPatientRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void addVirtualPatientXML(VirtualPatientDTO virtualPatientDTOXML) {

        VirtualPatient saved = virtualPatientRepository.save(virtualPatientXMLMapper.virtualPatientXMLToDao(virtualPatientDTOXML));

        for(ActionDTO actionDTO: virtualPatientDTOXML.getActions().getAction()){
            if(actionDTO.getActionClosedQuestionDTO() != null) {
                actionService.saveActionClosedQuestion(saved, actionDTO);
            }

            if(actionDTO.getActionSpontaneousPatientSpeech() != null){
                actionService.saveActionSpontaneousPatientSpeech(saved, actionDTO);
            }

            if(actionDTO.getActionOpenedQuestionDTO() != null){
                actionService.saveActionOpenedQuestion(saved, actionDTO);
            }

            // Prescriptions
            if(actionDTO.getActionPrescription() != null){
                actionService.saveActionPrescription(saved, actionDTO);
            }
        }
    }
}