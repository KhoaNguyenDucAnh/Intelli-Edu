package com.intelliedu.intelliedu.controller;

import java.util.List;
import java.util.Map;

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

	@PostMapping("")
	public EventDto createEvent(@RequestBody EventDto eventDto, Authentication authentication) {
		return eventService.createEvent(eventDto, authentication);
	}

	@PutMapping("/{id}")
	public EventDto updateEvent(@PathVariable String id, @RequestBody EventDto eventDto, Authentication authentication) {
		return eventService.updateEvent(id, eventDto, authentication);
	}

	@DeleteMapping("/{id}")
	public void deleteEvent(@PathVariable String id, Authentication authentication) {
		eventService.deleteEvent(id, authentication);
	}

  @PostMapping("/share/{id}")
  public void shareEvent(@PathVariable String id, Authentication authentication) {
		eventService.shareEvent(id, authentication);
	}

  @PostMapping("/add/{id}")
  public EventDto addSharedEvent(@PathVariable String id, Authentication authentication) {
		return eventService.addSharedEvent(id, authentication);
	}
}
