package com.intelliedu.intelliedu.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.intelliedu.intelliedu.dto.UserDto;
import com.intelliedu.intelliedu.entity.User;
import com.intelliedu.intelliedu.mapper.UserMapper;
import com.intelliedu.intelliedu.repository.UserRepository;
import com.intelliedu.intelliedu.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void registerUser(UserDto userDto) {
    User user = userMapper.toUser(userDto);

    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(""));

    boolean enabled = true;
    boolean userNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean userNonLocked = true;

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(), user.getPassword(), enabled, userNonExpired,
        credentialsNonExpired, userNonLocked, getAuthorities(user.getRoles()));
  }

  private static List<GrantedAuthority> getAuthorities(List<String> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String role : roles) {
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }
}
