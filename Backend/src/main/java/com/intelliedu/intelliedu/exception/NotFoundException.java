package com.intelliedu.intelliedu.exception;

/**
 * NotFoundException
 */
public class NotFoundException extends RuntimeException {

  public NotFoundException(Class<?> errorClass, String id) {
    super(String.format("%s with id %s not found", errorClass.getSimpleName(), id));
  }
}
