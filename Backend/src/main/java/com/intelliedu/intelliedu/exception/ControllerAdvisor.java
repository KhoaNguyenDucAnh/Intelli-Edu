package com.intelliedu.intelliedu.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ControllerAdvisor
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  private ResponseEntity<Object> createResponse(RuntimeException e, HttpStatus status) {
    return ResponseEntity
      .status(status)
      .body(
        Map.of(
          "timestamp", LocalDateTime.now(),
          "status", status.value(),
          "error", status.getReasonPhrase(),
          "message", e.getMessage()
        )
      );
  }

  private ResponseEntity<Object> createResponse(List<String> message, HttpStatus status) {
    return ResponseEntity
      .status(status)
      .body(
        Map.of(
          "timestamp", LocalDateTime.now(),
          "status", status.value(),
          "error", status.getReasonPhrase(),
          "message", message
        )
      );
  }

  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException e) {
    return createResponse(e, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
    return createResponse(e, HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return createResponse(e.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList(), HttpStatus.valueOf(status.value()));
  }
}
