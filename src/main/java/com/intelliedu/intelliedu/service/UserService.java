package com.intelliedu.intelliedu.service;

import com.intelliedu.intelliedu.dto.UserDto;

public interface UserService {

  public void registerUser(UserDto userDto);

  public void loginUser(UserDto userDto);
}
