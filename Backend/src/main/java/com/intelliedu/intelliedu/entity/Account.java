package com.intelliedu.intelliedu.entity;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.intelliedu.intelliedu.config.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account implements UserDetails {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String email;

  @Column(unique = true)
  private String username;

  private String password;

  private Boolean isEnabled;

  @Enumerated(EnumType.STRING)
  private Role role;

	@OneToOne(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private ActivationToken activationToken;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Post> post;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Vote> vote;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Event> event;

  @Override
  public List<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
