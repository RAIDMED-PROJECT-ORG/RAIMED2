package fr.imt.raimed2.virtualPatient.service;

import fr.imt.raimed2.action.dto.xml.ActionDTO;
import fr.imt.raimed2.action.model.Action;
import fr.imt.raimed2.action.repository.ActionRepository;
import fr.imt.raimed2.action.service.ActionService;
import fr.imt.raimed2.diagnostic.model.Diagnostic;
import fr.imt.raimed2.diagnostic.repository.DiagnosticRepository;
import fr.imt.raimed2.virtualPatient.dto.xml.VirtualPatientDTO;
import fr.imt.raimed2.virtualPatient.dto.xml.VirtualPatientXMLMapper;
import fr.imt.raimed2.virtualPatient.model.Gender;
import fr.imt.raimed2.virtualPatient.model.VirtualPatient;
import fr.imt.raimed2.virtualPatient.repository.VirtualPatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VirtualPatientService {

    private final VirtualPatientRepository virtualPatientRepository;
    private final VirtualPatientXMLMapper virtualPatientXMLMapper;
    private final ActionService actionService;
    private final ActionRepository actionRepository;

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
        VirtualPatient virtualPatient = virtualPatientXMLMapper.virtualPatientXMLToDao(virtualPatientDTOXML);
        if (virtualPatient.getActions() != null) {
            virtualPatient.getActions().forEach(action -> action.setVirtualPatient(virtualPatient));
        }
        try {
            VirtualPatient saved = virtualPatientRepository.save(virtualPatient);
            if (virtualPatientDTOXML.getActions().getAction() == null) {
                return;
            }
            for (ActionDTO actionDTO : virtualPatientDTOXML.getActions().getAction()) {
                if (actionDTO.getActionClosedQuestionDTO() != null) {
                    actionService.saveActionClosedQuestion(saved, actionDTO);
                }
                if (actionDTO.getActionSpontaneousPatientSpeech() != null) {
                    actionService.saveActionSpontaneousPatientSpeech(saved, actionDTO);
                }
                if (actionDTO.getActionPrescriptionDTO() != null) {
                    actionService.saveActionPrescription(saved, actionDTO);
                }
                if(actionDTO.getActionPrecisionDTO() != null){
                    actionService.saveActionPrecision(saved, actionDTO);
                }
                if(actionDTO.getActionOpenedQuestionDTO() != null){
                    actionService.saveActionOpenedQuestion(saved, actionDTO);
                }
                if (actionDTO.getActionAuscultationDTO() != null || actionDTO.getActionInspectionDTO() != null || actionDTO.getActionPalpationDTO() != null || actionDTO.getActionPercussionDTO() != null) {
                    actionService.saveActionExamen(saved, actionDTO);
                }
            }
        } catch (DataIntegrityViolationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Transactional
    public void editVirtualPatientXML(VirtualPatientDTO virtualPatientDTOXML) {
        this.deleteVirtualPatientById(virtualPatientDTOXML.getId());
        virtualPatientDTOXML.setId(null);
        this.addVirtualPatientXML(virtualPatientDTOXML);
    }
}