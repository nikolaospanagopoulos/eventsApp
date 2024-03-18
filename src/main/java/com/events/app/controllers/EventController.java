package com.events.app.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.app.payload.ApiResponse;
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
	public ResponseEntity<ApiResponse> createEvent(@RequestBody EventDto eventDto) {
		ApiResponse apiResponse = new ApiResponse(this.eventService.createEvent(eventDto));
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public List<EventDto> getAllEvents() {
		return eventService.getAllEvents();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getEventById(@PathVariable(name = "id") long id) {

		return new ResponseEntity<>(new ApiResponse(this.eventService.getEventById(id)), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateEvent(@RequestBody EventDto eventDto, @PathVariable(name = "id") long id) {
		ApiResponse apiResponse = new ApiResponse(this.eventService.updateEvent(eventDto, id));
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteEventById(@PathVariable(name = "id") long id) {
		this.eventService.deleteEventById(id);
		return new ResponseEntity<>(new ApiResponse("deleted successfully"), HttpStatus.OK);
	}

}
