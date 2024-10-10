package fr.imt.raimed2.exception;

import fr.imt.raimed2.exception.dto.response.Raimed2ErrorDetailsDto;
import fr.imt.raimed2.user.exception.EmailAlreadyInUseException;
import fr.imt.raimed2.user.exception.UsernameAlreadyInUseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHander {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.put(((FieldError) error).getField(), errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<String> handleUsernameAlreadyInUseException(UsernameAlreadyInUseException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> handleEmailAlreadyInUseException(EmailAlreadyInUseException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Raimed2ErrorDetailsDto> handleNoSuchElementException(
            NoSuchElementException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Raimed2ErrorDetailsDto.builder()
                        .message("Resource not found")
                        .details(exception.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Raimed2ErrorDetailsDto> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                Raimed2ErrorDetailsDto.builder()
                        .message("Method not allowed")
                        .details("The method " + exception.getMethod() + " is not allowed for this endpoint")
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Raimed2ErrorDetailsDto> handleAccessDeniedException(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Raimed2ErrorDetailsDto.builder()
                        .message("Access denied")
                        .details("You don't have the permission to access this resource")
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(NonValidRequestBody.class)
    public ResponseEntity<Raimed2ErrorDetailsDto> handleNonValidRequestBody(NonValidRequestBody exception, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(
                Raimed2ErrorDetailsDto.builder()
                        .message("Invalid request body")
                        .details(exception.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }
}
