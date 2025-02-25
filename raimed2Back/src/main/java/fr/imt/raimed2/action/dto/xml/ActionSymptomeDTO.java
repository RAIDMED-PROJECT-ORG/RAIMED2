package fr.imt.raimed2.action.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import fr.imt.raimed2.symptome.dto.xml.SymptomeDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActionSymptomeDTO implements Serializable {

    @JacksonXmlProperty
    private SymptomeDTO symptome;

}
