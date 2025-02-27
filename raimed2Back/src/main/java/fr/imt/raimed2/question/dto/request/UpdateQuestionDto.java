package fr.imt.raimed2.question.dto.request;

import fr.imt.raimed2.question.model.Filter;
import fr.imt.raimed2.question.model.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateQuestionDto {

    @NotNull(message = "Question filter is required")
    Filter filter;

    @NotBlank(message = "Question content is required")
    String content;

    String answer;

    UUID teacherId;

    @NotNull(message = "Question type is required")
    QuestionType type;

}
