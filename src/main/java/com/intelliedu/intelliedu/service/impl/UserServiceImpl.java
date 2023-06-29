package com.intelliedu.intelliedu.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.intelliedu.intelliedu.config.UserConfig;
import com.intelliedu.intelliedu.dto.UserDto;
import com.intelliedu.intelliedu.entity.User;
import com.intelliedu.intelliedu.mapper.UserMapper;
import com.intelliedu.intelliedu.repository.UserRepo;
import com.intelliedu.intelliedu.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserRepo userRepo;

  @Override
  public void registerUser(UserDto userDto) {
    if (userRepo.findByEmail(userDto.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, UserConfig.CONFLICT);
    }

    User user = userMapper.toUser(userDto);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    user.setRole(Arrays.asList("USER"));

    userRepo.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepo.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(UserConfig.NOT_FOUND));

    boolean enabled = true;
    boolean userNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean userNonLocked = true;

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), enabled, userNonExpired,
        credentialsNonExpired, userNonLocked, getAuthorities(user.getRole()));
  }

  private static List<GrantedAuthority> getAuthorities(List<String> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String role : roles) {
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }

  @Override
  public void loginUser(UserDto userDto) {

  }
}
