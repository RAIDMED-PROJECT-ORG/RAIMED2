package fr.imt.raimed2.action.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import fr.imt.raimed2.prescription.dto.xml.PrescriptionDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActionPrescriptionDTO implements Serializable {
    @JacksonXmlProperty
    private PrescriptionDTO prescription;
}
