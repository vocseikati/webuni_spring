package hu.webuni.hr.katka.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException e,
                                                             WebRequest request) {
    FieldError fieldErrors = e.getBindingResult().getFieldError();

    ErrorResponse response = new ErrorResponse.ErrorResponseBuilder()
        .statusCode(BAD_REQUEST.value())
        .timeStamp(LocalDateTime.now())
        .message(Objects.requireNonNull(fieldErrors).getDefaultMessage())
        .build();

    return ResponseEntity.status(BAD_REQUEST).body(response);
  }
}
