package com.intelliedu.intelliedu.dto;

import com.intelliedu.intelliedu.constraint.EmailValid;
import com.intelliedu.intelliedu.constraint.PasswordMatch;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatch
public class AccountRegistrationDto {

  @NotEmpty(message = "Email must not be empty")
  @EmailValid
  private String email;

  @NotEmpty(message = "Username must not be empty")
  private String username;

  @NotEmpty(message = "Password must not be empty")
  private String password;

  @NotEmpty(message = "Confirm password must not be empty")
  private String confirmPassword;
}
