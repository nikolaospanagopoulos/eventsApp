package com.events.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.events.app.payload.ApiResponse;
import com.events.app.payload.EventDto;

import com.events.app.services.EventService;
import com.events.app.utilis.ApplicationConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventController {

	private EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ApiResponse> createEvent(@Valid @RequestBody EventDto eventDto) {
		ApiResponse apiResponse = new ApiResponse(this.eventService.createEvent(eventDto));
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<ApiResponse> getAllEvents(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

		ApiResponse apiResponse = new ApiResponse(eventService.getAllEvents(pageNo, pageSize, sortBy, sortDir));
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getEventById(@PathVariable(name = "id") long id) {

		return new ResponseEntity<>(new ApiResponse(this.eventService.getEventById(id)), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateEvent(@Valid @RequestBody EventDto eventDto,
			@PathVariable(name = "id") long id) {
		ApiResponse apiResponse = new ApiResponse(this.eventService.updateEvent(eventDto, id));
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteEventById(@PathVariable(name = "id") long id) {
		this.eventService.deleteEventById(id);
		return new ResponseEntity<>(new ApiResponse("deleted successfully"), HttpStatus.OK);
	}

}
