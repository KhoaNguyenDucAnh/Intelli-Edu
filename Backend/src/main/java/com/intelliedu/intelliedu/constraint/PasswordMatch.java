package com.intelliedu.intelliedu.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.intelliedu.intelliedu.constraint.impl.PasswordMatchValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * password
 */
@Constraint(validatedBy = PasswordMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordMatch {
  String message() default "Passwords do not match";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
