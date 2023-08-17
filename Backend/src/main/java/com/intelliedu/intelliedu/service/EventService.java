package com.intelliedu.intelliedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.intelliedu.intelliedu.dto.EventDto;
import com.intelliedu.intelliedu.entity.Account;
import com.intelliedu.intelliedu.entity.Event;
import com.intelliedu.intelliedu.mapper.EventMapper;
import com.intelliedu.intelliedu.repository.EventRepo;
import com.intelliedu.intelliedu.security.service.AuthService;

/**
 * EventService
 */
@Service
public class EventService {

	@Autowired
	private EventRepo eventRepo;

	@Autowired
	private EventMapper eventMapper;

	@Autowired
	private AuthService authService;

	public Page<EventDto> findEvent(Pageable pageable, Authentication authentication) {
		return eventMapper.toEventDto(eventRepo.findByAccount(pageable, authService.getAccount(authentication)));
	}

	public EventDto createEvent(EventDto eventDto, Authentication authentication) {
		Account account = authService.getAccount(authentication);

		Event event = eventMapper.toEvent(eventDto);

		event.setAccount(account);
		
		account.getEvent().add(event);

		return eventMapper.toEventDto(eventRepo.save(event));
	}

	public EventDto updateEvent(EventDto eventDto, Authentication authentication) {
    Account account = authService.getAccount(authentication);

		// Check existance
    Event event = eventRepo
			.findByIdAndAccount(eventDto.getId(), account)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		event.setName(eventDto.getName());
		event.setDeadline(eventDto.getDeadline());
		event.setUrgent(eventDto.getUrgent());
		event.setImportant(eventDto.getImportant());
		event.setEventType(eventDto.getEventType());
		event.setDescription(eventDto.getDescription());

    return eventMapper.toEventDto(eventRepo.save(event));
  }

	public void deleteEvent(Long id, Authentication authentication) {
		eventRepo.deleteByIdAndAccount(id, authService.getAccount(authentication));
	}
}
