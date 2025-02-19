package fr.imt.raimed2.question.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class QuestionLinkedDTO implements Serializable {
    @JacksonXmlProperty
    private String type;
    @JacksonXmlProperty
    private String filter;
    @JacksonXmlProperty
    private String content;
    @JacksonXmlProperty
    private UUID teacherId;
    @JacksonXmlProperty
    private String answer;
    @JacksonXmlProperty
    private boolean isMutual;
}
