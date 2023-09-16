package com.intelliedu.intelliedu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.intelliedu.intelliedu.dto.AccountRegistrationDto;
import com.intelliedu.intelliedu.dto.AccountResponseDto;
import com.intelliedu.intelliedu.entity.Account;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

  @Mapping(source = "password", target = "password", ignore = true)
  public Account toAccount(AccountRegistrationDto accountRegistrationDto);

  public AccountResponseDto toAccountResponseDto(Account account);
}
