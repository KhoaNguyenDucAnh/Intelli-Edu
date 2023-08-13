package com.intelliedu.intelliedu.entity;

import java.sql.Timestamp;

import com.github.f4b6a3.uuid.UuidCreator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class ActivationToken {

  @Id
  private String token;

  private Timestamp expireDateTime; 

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Account account;

  public String setToken() {
    return this.token = UuidCreator.getTimeOrderedEpoch().toString();  
  } 
}
