package fr.imt.raimed2.exam.dto.request;

import fr.imt.raimed2.exam.model.ExamType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateExamDTO {
    @NotNull(message = "Zone is required")
    String zone;

    @NotNull(message = "Signs is required")
    List<String> signs;

    @NotNull(message = "Exam type is required")
    ExamType examType;
}
