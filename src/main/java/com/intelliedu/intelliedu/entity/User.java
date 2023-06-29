package com.intelliedu.intelliedu.entity;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

  private static final long serialVersionUID = 1L;
  @SuppressWarnings("unused")
  private static final String ROLE_PREFIX = "ROLE_";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Long id;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "roles")
  private List<String> role;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @Column(name = "mindmaps")
  private List<MindMap> mindMap;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @Column(name = "posts")
  private List<Post> post;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @Column(name = "comments")
  private List<Comment> comment;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return false;
  }
}
