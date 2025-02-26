package fr.imt.raimed2.action.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("PERCUSSION")
public class ActionPercussionDTO {
    @JacksonXmlProperty
    private String zone;
    @JacksonXmlProperty
    private String signs;
}
