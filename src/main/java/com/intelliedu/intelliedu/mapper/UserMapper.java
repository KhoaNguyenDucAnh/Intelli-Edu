package com.intelliedu.intelliedu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.intelliedu.intelliedu.dto.UserDto;
import com.intelliedu.intelliedu.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  @Mapping(source = "password", target = "password", ignore = true)
  public User toUser(UserDto userDto);
}
