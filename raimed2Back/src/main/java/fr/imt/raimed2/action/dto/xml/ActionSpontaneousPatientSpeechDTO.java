package fr.imt.raimed2.action.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionSpontaneousPatientSpeechDTO {
    @JacksonXmlProperty(localName = "speech")
    private String speech;
}
