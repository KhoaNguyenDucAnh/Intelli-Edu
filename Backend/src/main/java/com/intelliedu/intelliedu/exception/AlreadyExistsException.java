package com.intelliedu.intelliedu.exception;

/**
 * AlreadyExistsException
 */
public class AlreadyExistsException extends RuntimeException {

  public AlreadyExistsException(Class<?> errorClass, String field, String value) {
    super(String.format("%s with %s %s already exists", errorClass.getSimpleName(), field, value));
  }
}
