package fr.imt.raimed2.virtualPatient.service;

import fr.imt.raimed2.action.dto.xml.ActionDTO;
import fr.imt.raimed2.action.dto.xml.ActionsDTO;
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
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VirtualPatientService.class);

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
        logger.info("Retrieving virtual patient...");
        VirtualPatient virtualPatient = virtualPatientXMLMapper.virtualPatientXMLToDao(virtualPatientDTOXML);

        logger.info("Linking actions to virtual patient");
        if (virtualPatient.getActions() != null) {
            virtualPatient.getActions().forEach(action -> action.setVirtualPatient(virtualPatient));
        }

        logger.info("Saving virtual patient...");
        VirtualPatient saved = virtualPatientRepository.save(virtualPatient);

        logger.info("Saving actions responses...");
        for(ActionDTO actionDTO: virtualPatientDTOXML.getActions().getAction()){
            logger.info("Action: {}", actionDTO.toString());
            if(actionDTO.getActionClosedQuestionDTO() != null) {
                logger.info("Action closed question: {}", actionDTO.getActionClosedQuestionDTO().toString());
                actionService.saveActionClosedQuestion(saved, actionDTO);
            }

            if(actionDTO.getActionSpontaneousPatientSpeech() != null){
                logger.info("Action spontaneous patient speech: {}", actionDTO.getActionSpontaneousPatientSpeech().toString());
                actionService.saveActionSpontaneousPatientSpeech(saved, actionDTO);
            }

            if(actionDTO.getActionOpenedQuestionDTO() != null){
                logger.info("Action opened question: {}", actionDTO.getActionOpenedQuestionDTO().toString());
                actionService.saveActionOpenedQuestion(saved, actionDTO);
            }

        }
    }
}