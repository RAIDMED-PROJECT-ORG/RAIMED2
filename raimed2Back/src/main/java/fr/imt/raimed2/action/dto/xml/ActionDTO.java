package fr.imt.raimed2.action.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActionDTO implements Serializable {
    @JacksonXmlProperty
    private String type;

    @JacksonXmlProperty
    private String primaryElement;

    @JacksonXmlProperty(localName = "actionClosedQuestion")
    private ActionClosedQuestionDTO actionClosedQuestionDTO;

    @JacksonXmlProperty(localName = "actionSpontaneousPatientSpeech")
    private ActionSpontaneousPatientSpeechDTO actionSpontaneousPatientSpeech;

    @JacksonXmlProperty(localName = "actionOpenedQuestion")
    private ActionOpenedQuestionDTO actionOpenedQuestionDTO;

    @JacksonXmlProperty(localName = "actionPrescription")
    private ActionPrescriptionDTO actionPrescriptionDTO;

    @JacksonXmlProperty(localName = "actionInspection")
    private ActionInspectionDTO actionInspectionDTO;

    @JacksonXmlProperty(localName = "actionPalpation")
    private ActionPalpationDTO actionPalpationDTO;

    @JacksonXmlProperty(localName = "actionAuscultation")
    private ActionAuscultationDTO actionAuscultationDTO;

    @JacksonXmlProperty(localName = "actionPercussion")
    private ActionPercussionDTO actionPercussionDTO;

    @JacksonXmlProperty(localName = "actionPrecision")
    private ActionPrecisionDTO actionPrecisionDTO;
}
