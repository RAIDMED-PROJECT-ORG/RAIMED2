package fr.imt.raimed2.action.dto.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import fr.imt.raimed2.question.dto.xml.QuestionLinkedDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActionOpenedQuestionDTO implements Serializable {
    @JacksonXmlProperty
    private String openedAnswer;
    @JacksonXmlProperty
    private QuestionLinkedDTO questionLinked;
}
