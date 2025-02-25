package fr.imt.raimed2.action.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import fr.imt.raimed2.precision.dto.xml.PrecisionDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActionPrecisionDTO implements Serializable {

    @JacksonXmlProperty
    private PrecisionDTO precision;

}
