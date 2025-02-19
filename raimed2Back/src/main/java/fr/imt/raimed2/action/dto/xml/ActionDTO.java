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
    private ActionSpontaneousPatientSpeechDTO actionSpontaneousPatientSpeechDTO;

    @JacksonXmlProperty(localName = "actionOpenedQuestion")
    private ActionOpenedQuestionDTO actionOpenedQuestionDTO;

    public String toString() {
        return "ActionDTO(type=" + this.getType() + ", primaryElement=" + this.getPrimaryElement() + ")";
    }
}
