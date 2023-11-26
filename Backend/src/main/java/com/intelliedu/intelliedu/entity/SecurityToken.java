package com.intelliedu.intelliedu.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.intelliedu.intelliedu.config.SecurityAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
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
	private UUID id;

  private String token;

  private ZonedDateTime expireDateTime;

	@Enumerated(EnumType.STRING)
	private SecurityAction securityAction;

	@MapsId
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;
}
