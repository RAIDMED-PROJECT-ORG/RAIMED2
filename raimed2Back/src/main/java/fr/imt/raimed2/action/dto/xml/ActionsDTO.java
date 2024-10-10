package fr.imt.raimed2.action.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ActionsDTO implements Serializable {

    @JacksonXmlProperty
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ActionDTO> action;
}
