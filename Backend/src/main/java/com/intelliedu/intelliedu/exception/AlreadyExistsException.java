package com.intelliedu.intelliedu.exception;

import java.util.UUID;

/**
 * AlreadyExistsException
 */
public class AlreadyExistsException extends RuntimeException {

  public AlreadyExistsException(Class<?> errorClass, String field, String value) {
    super(String.format("%s with %s %s already exists", errorClass.getSimpleName(), field, value));
  }

  public AlreadyExistsException(Class<?> errorClass, UUID id) {
    this(errorClass, "id", id.toString());
  }
}
