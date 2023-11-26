package com.intelliedu.intelliedu.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountLogInDto {

  @NotEmpty(message = "Email must not be empty")
  private String email;

  @NotEmpty(message = "Password must not be empty")
  private String password;
}
