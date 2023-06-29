package com.intelliedu.intelliedu.dto;

import com.intelliedu.intelliedu.validator.PasswordMatches;
import com.intelliedu.intelliedu.validator.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@PasswordMatches
public class AccountDto {

  private Long id;

  @NotNull
  @NotEmpty
  @ValidEmail
  private String email;

  @NotNull
  @NotEmpty
  private String username;

  @NotNull
  @NotEmpty
  private String password;
  private String matchingPassword;
}
