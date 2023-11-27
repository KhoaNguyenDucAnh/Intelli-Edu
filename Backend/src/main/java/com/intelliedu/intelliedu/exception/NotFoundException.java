package com.intelliedu.intelliedu.exception;

import java.util.UUID;

/**
 * NotFoundException
 */
public class NotFoundException extends RuntimeException {

  public NotFoundException(Class<?> errorClass, String id) {
    super(String.format("%s with id %s not found", errorClass.getSimpleName(), id));
  }

  public NotFoundException(Class<?> errorClass, UUID id) {
    this(errorClass, id.toString());
  }
}
