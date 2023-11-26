package com.intelliedu.intelliedu.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true)
  private String email;

  private String username;

  private String password;

  @Builder.Default
  private boolean isEnabled = false;

  @Enumerated(EnumType.STRING)
  private Role role;

	//private Integer point;

  @Builder.Default
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<File> file = new ArrayList<>();

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<Vote> vote;

	//@OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	//private List<Schedule> schedule;

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
