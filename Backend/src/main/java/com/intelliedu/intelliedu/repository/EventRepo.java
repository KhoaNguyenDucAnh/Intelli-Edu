package com.intelliedu.intelliedu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelliedu.intelliedu.entity.Event;

/**
 * EventRepo
 */
public interface EventRepo extends JpaRepository<Event, Long> {

	
}
