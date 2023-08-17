package com.intelliedu.intelliedu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.EventDto;
import com.intelliedu.intelliedu.service.EventService;

/**
 * EventController
 */
@RestController
@RequestMapping("/api/v1/event")
@ResponseStatus(code = HttpStatus.OK)
public class EventController {

	@Autowired	
	private EventService eventService;

	@GetMapping("")
	public Page<EventDto> findEvent(Pageable pageable, Authentication authentication) {
		return eventService.findEvent(pageable, authentication);
	}

	@PostMapping("")
	public EventDto createEvent(EventDto eventDto, Authentication authentication) {
		return eventService.createEvent(eventDto, authentication);
	}

	@PutMapping("")
	public EventDto updateEvent(EventDto eventDto, Authentication authentication) {
		return eventService.updateEvent(eventDto, authentication);
	}

	@DeleteMapping("/{id}")
	public void deleteEvent(@PathVariable Long id, Authentication authentication) {
		eventService.deleteEvent(id, authentication);
	}
}
