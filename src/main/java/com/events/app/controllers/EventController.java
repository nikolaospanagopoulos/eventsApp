package com.events.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.app.payload.EventDto;
import com.events.app.services.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

	private EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping
	public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
		return new ResponseEntity<>(this.eventService.createEvent(eventDto), HttpStatus.CREATED);
	}
}
