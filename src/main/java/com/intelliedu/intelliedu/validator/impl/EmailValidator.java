package com.intelliedu.intelliedu.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.intelliedu.intelliedu.validator.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private Pattern pattern;
  private Matcher matcher;
  private static final String EMAIL_PATTERN =
      "^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$";

  @Override
  public void initialize(ValidEmail constraintAnnotation) {}

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    return (validateEmail(email));
  }

  private boolean validateEmail(String email) {
    pattern = Pattern.compile(EMAIL_PATTERN);
    matcher = pattern.matcher(email);
    return matcher.matches();
  }
}
