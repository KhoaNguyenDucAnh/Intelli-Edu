package com.intelliedu.intelliedu.constraint.impl;

import com.intelliedu.intelliedu.constraint.PasswordMatch;
import com.intelliedu.intelliedu.dto.AccountRegistrationDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * passwordValidator
 */
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, AccountRegistrationDto> {

  @Override
  public boolean isValid(AccountRegistrationDto dto, ConstraintValidatorContext context) {
    return dto.getPassword().equals(dto.getConfirmPassword());
  } 
}
