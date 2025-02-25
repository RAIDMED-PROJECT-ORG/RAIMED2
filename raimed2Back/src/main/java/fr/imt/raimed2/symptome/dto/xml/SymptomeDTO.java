package fr.imt.raimed2.symptome.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SymptomeDTO implements Serializable {
    @JacksonXmlProperty
    private String question;

    @JacksonXmlProperty
    private String answer;
}
