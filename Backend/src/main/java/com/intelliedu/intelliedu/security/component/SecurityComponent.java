package com.intelliedu.intelliedu.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.repository.AccountRepo;

/**
 * Security
 */
@Component
public class SecurityComponent {

  @Autowired
  private AccountRepo accountRepo;

  @Bean
  //@Profile({"dev", "test", "prod"})
  UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepo
          .findByEmail(email)
          .orElseThrow(() -> new UsernameNotFoundException(null));
        
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
    }; 
  }

  //@Bean
  //@Profile("test")
  UserDetailsService inMemoryUserDetailsManager() {
    return new InMemoryUserDetailsManager(
      User
        .withUsername("admin")
        .password("admin")
        .passwordEncoder(passwordEncoder()::encode)
        .build(),
      User
        .withUsername("nimda")
        .password("nimda")
        .passwordEncoder(passwordEncoder()::encode)
        .build()
    );
  }

  @Lazy
  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }
}
