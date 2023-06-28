package com.intelliedu.intelliedu.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.intelliedu.intelliedu.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
