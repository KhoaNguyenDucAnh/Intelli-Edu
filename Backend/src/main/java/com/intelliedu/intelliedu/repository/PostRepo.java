package com.intelliedu.intelliedu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Post;

/**
 * PostRepo
 */
public interface PostRepo extends JpaRepository<Post, Long> {

	Optional<Post> findByIdAndAccount(Long id, Account account);	
}
