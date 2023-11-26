package com.intelliedu.intelliedu.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;


public interface AccountRepo extends JpaRepository<Account, UUID> {

  Optional<Account> findByEmail(String email);

	Boolean existsByEmail(String email);
}
