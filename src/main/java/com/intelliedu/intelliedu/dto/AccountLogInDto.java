package com.intelliedu.intelliedu.dto;

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
public class AccountLogInDto {

  @NotNull
  @NotEmpty
  private String username;

  @NotNull
  @NotEmpty
  private String password;
}
