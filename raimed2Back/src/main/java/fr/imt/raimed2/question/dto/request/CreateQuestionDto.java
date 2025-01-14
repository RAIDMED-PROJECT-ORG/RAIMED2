package fr.imt.raimed2.question.dto.request;

import fr.imt.raimed2.question.model.Filter;
import fr.imt.raimed2.question.model.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateQuestionDto {

    @NotNull(message = "Question filter is required")
    Filter filter;

    @NotBlank(message = "Question content is required")
    String content;

    @NotBlank(message = "Question answer is required")
    String answer;

    Long teacherId;

    @NotNull(message = "Question type is required")
    QuestionType type;

}
