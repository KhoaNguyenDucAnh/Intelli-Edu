package com.intelliedu.intelliedu.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intelliedu.intelliedu.dto.EventDto;
import com.intelliedu.intelliedu.service.EventService;

import jakarta.validation.Valid;

/**
 * EventController
 */
@RestController
@RequestMapping("/api/v1/event")
public class EventController {

	@Autowired	
	private EventService eventService;

	@GetMapping("")
	public Map<String, List<Object>> findEvent(Authentication authentication) {
		return eventService.findEvent(authentication);
	}

  @GetMapping("/notification/{accountId}")
  public List<Map<String, String>> findEvent(@PathVariable UUID accountId) {
    return eventService.findEvent(accountId);
  }

	@PostMapping("")
	public EventDto createEvent(@RequestBody @Valid EventDto eventDto, Authentication authentication) {
		return eventService.createEvent(eventDto, authentication);
	}

	@PutMapping("/{id}")
	public EventDto updateEvent(@PathVariable UUID id, @RequestBody @Valid EventDto eventDto, Authentication authentication) {
		return eventService.updateEvent(id, eventDto, authentication);
	}

	@DeleteMapping("/{id}")
	public void deleteEvent(@PathVariable UUID id, Authentication authentication) {
		eventService.deleteEvent(id, authentication);
	}

  @PostMapping("/share/{id}")
  public String shareEvent(@PathVariable UUID id, Authentication authentication) {
		return eventService.shareEvent(id, authentication);
	}
}
