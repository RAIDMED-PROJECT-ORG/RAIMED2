package fr.imt.raimed2.precision.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PrecisionDTO implements Serializable {
    @JacksonXmlProperty
    private String question;

    @JacksonXmlProperty
    private String answer;
}
