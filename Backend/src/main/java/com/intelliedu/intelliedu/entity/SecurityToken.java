package com.intelliedu.intelliedu.entity;

import java.time.ZonedDateTime;

import com.intelliedu.intelliedu.config.SecurityAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ActivationToken
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SecurityToken {

  @Id
	@GeneratedValue
	private Long id;

  private String token;

  private ZonedDateTime expireDateTime;

	@Enumerated(EnumType.STRING)
	private SecurityAction securityAction;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;
}
