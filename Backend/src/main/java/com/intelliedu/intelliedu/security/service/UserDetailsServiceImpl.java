package com.intelliedu.intelliedu.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.repository.AccountRepo;

/**
 * UserDetailServiceImpl
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {

  @Autowired
  private AccountRepo accountRepo;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(null));
    if (account.isEnabled()) {
      boolean accountNonExpired = true;
      boolean credentialsNonExpired = true;
      boolean accountNonLocked = true;

      return new org.springframework.security.core.userdetails.User(
        account.getEmail(),
        account.getPassword(),
        true,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        account.getAuthorities());
    } else {
      throw new UsernameNotFoundException(email);
    }
  }

  @Override
  public UserDetails updatePassword(UserDetails user, String newPassword) {
    return null;
  }
}
