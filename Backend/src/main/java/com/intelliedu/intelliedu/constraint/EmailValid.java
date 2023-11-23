package com.intelliedu.intelliedu.constraint;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.intelliedu.intelliedu.constraint.impl.EmailValidValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * EmailValid
 */
@Constraint(validatedBy = EmailValidValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EmailValid {
  String message() default "Invalid email";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
