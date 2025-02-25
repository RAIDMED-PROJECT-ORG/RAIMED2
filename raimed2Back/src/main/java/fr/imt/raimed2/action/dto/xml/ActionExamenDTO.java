package fr.imt.raimed2.action.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActionExamenDTO implements Serializable {
    @JacksonXmlProperty
    private String zone;

    @JacksonXmlProperty(localName = "signs")
    private String signs;

}