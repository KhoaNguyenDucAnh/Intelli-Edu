package com.intelliedu.intelliedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Post;

/**
 * PostRepo
 */
public interface PostRepo extends JpaRepository<Post, String> {

}
