package com.intelliedu.intelliedu.constraint.impl;

import com.intelliedu.intelliedu.constraint.EmailValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * EmailValidValidator
 */
public class EmailValidValidator implements ConstraintValidator<EmailValid, String> {

  @Override
  public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
    return false;
  }
}
