package com.intelliedu.intelliedu.validator.impl;

import com.intelliedu.intelliedu.dto.AccountDto;
import com.intelliedu.intelliedu.validator.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  @Override
  public void initialize(PasswordMatches constraintAnnotation) {}

  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context) {
    AccountDto accountDto = (AccountDto) obj;
    return accountDto.getPassword().equals(accountDto.getMatchingPassword());
  }
}
