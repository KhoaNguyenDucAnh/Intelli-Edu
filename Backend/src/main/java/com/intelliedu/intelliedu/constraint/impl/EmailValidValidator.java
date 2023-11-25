package com.intelliedu.intelliedu.constraint.impl;

import java.util.regex.Pattern;

import com.intelliedu.intelliedu.constraint.EmailValid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * EmailValidValidator
 */
public class EmailValidValidator implements ConstraintValidator<EmailValid, String> {

  @Override
  public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
    return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(arg0).matches();
  }
}
