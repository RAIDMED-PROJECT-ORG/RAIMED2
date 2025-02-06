package fr.imt.raimed2.question.controller;

import fr.imt.raimed2.question.dto.request.CreateQuestionDto;
import fr.imt.raimed2.question.dto.request.UpdateQuestionDto;
import fr.imt.raimed2.question.model.Question;
import fr.imt.raimed2.question.model.QuestionType;
import fr.imt.raimed2.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestion(@RequestParam(required = false) UUID teacherId, @RequestParam(required = false) String type, @RequestParam(required = false) String gender) {
        boolean admin = teacherId == null;
        QuestionType questionType = (type == null) ? null : QuestionType.valueOf(type.toUpperCase());
        return ResponseEntity.ok().body(questionService.getAllQuestion(questionType, teacherId, admin, gender));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody CreateQuestionDto createQuestionDto) {
        return ResponseEntity
                .created(URI.create("/api/v1/question"))
                .body(this.questionService.createQuestion(createQuestionDto));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TEACHER')")
    @PostMapping("/multiples")
    public ResponseEntity<List<Question>> createMultipleQuestions(@Valid @RequestBody List<CreateQuestionDto> createQuestionDtos) {
        return ResponseEntity.created(URI.create("/api/v1/question/multiples")).body(this.questionService.createMultipleQuestions(createQuestionDtos));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody UpdateQuestionDto updateQuestionDto
    ) {
        try {
            return ResponseEntity.ok().body(this.questionService.updateQuestion(id, updateQuestionDto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        this.questionService.deleteQuestion(id);
    }
}