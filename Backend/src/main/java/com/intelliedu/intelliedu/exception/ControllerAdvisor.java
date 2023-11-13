package com.intelliedu.intelliedu.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
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

import com.intelliedu.intelliedu.dto.ErrorDto;

/**
 * ControllerAdvisor
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  private Map<String, Object> createResponse(List<String> message, HttpStatus status) {
    List<ErrorDto> errorDto = message.stream().map(m -> new ErrorDto(status.value(), status.getReasonPhrase(), m)).toList();
    return Map.of(
      "timestamp", LocalDateTime.now(),
      "errors", errorDto
    );
  }

  private Map<String, Object> createResponse(String message, HttpStatus status) {
    return createResponse(Arrays.asList(message), status);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(createResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
    return ResponseEntity.badRequest().body(createResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    return ResponseEntity.badRequest().body(createResponse(ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList(), HttpStatus.valueOf(status.value())));
  }
}
