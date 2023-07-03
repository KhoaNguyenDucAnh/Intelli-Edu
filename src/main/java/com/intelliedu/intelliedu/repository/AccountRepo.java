package com.intelliedu.intelliedu.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intelliedu.intelliedu.entity.Account;


public interface AccountRepo extends JpaRepository<Account, Long> {

  Optional<Account> findByUsername(String usesrname);

  Optional<Account> findByEmail(String email);
}
