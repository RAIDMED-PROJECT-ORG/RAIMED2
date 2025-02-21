package fr.imt.raimed2.exam.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import fr.imt.raimed2.exam.model.ExamType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ExamDTO implements Serializable {
    @JacksonXmlProperty
    private ExamType type;

    @JacksonXmlProperty
    private String zone;

    @JacksonXmlProperty
    private List<String> signs;
}
