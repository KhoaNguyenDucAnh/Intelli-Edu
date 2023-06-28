package com.intelliedu.intelliedu.validator.impl;

import com.intelliedu.intelliedu.dto.UserDto;
import com.intelliedu.intelliedu.validator.PasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  @Override
  public void initialize(PasswordMatches constraintAnnotation) {}

  @Override
  public boolean isValid(Object obj, ConstraintValidatorContext context) {
    UserDto userDto = (UserDto) obj;
    return userDto.getPassword().equals(userDto.getMatchingPassword());
  }
}
