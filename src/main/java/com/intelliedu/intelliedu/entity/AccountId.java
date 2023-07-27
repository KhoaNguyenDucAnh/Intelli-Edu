package com.intelliedu.intelliedu.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * AccountId
 */
@Data
public class AccountId implements Serializable {

  private Long id;

  private String email;
}
