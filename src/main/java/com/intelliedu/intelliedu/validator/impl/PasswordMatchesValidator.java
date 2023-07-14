package com.intelliedu.intelliedu.validator.impl;

import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.validator.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  @Override
  public void initialize(PasswordMatches constraintAnnotation) {}

  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context) {
    AccountRegistrationDto accountRegistrationDto = (AccountRegistrationDto) obj;
    try {
      return accountRegistrationDto
          .getPassword()
          .equals(accountRegistrationDto.getConfirmPassword());
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }
}
