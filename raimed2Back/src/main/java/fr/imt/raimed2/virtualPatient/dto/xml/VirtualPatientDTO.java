package fr.imt.raimed2.virtualPatient.dto.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import fr.imt.raimed2.action.dto.xml.ActionsDTO;
import fr.imt.raimed2.user.dto.response.UserDetailsDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JacksonXmlRootElement(localName = "VirtualPatient")
@Getter
@Setter
public class VirtualPatientDTO implements Serializable {

    @JacksonXmlProperty
    private Integer age;

    @JacksonXmlProperty
    private String gender;

    @JacksonXmlProperty
    private Date createdAt;

    @JacksonXmlProperty
    private UserDetailsDto createdBy;

    @JacksonXmlProperty(localName = "actions")
    private ActionsDTO actions;

    @JacksonXmlProperty(localName = "result")
    private String result;

}