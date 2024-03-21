package com.events.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.events.app.payload.ApiResponse;
import com.events.app.payload.TicketDto;
import com.events.app.services.TicketService;
import com.events.app.utilis.ApplicationConstants;

@RestController
@RequestMapping("/api/")
public class TicketController {
	private TicketService ticketService;

	public TicketController(TicketService ticketService) {
		super();
		this.ticketService = ticketService;
	}

	@PostMapping("/events/{eventId}/tickets")
	public ResponseEntity<ApiResponse> createTicket(@PathVariable(value = "eventId") long eventId,
			@RequestBody TicketDto ticketDto) {
		ApiResponse apiResponse = new ApiResponse(this.ticketService.createTicket(eventId, ticketDto));
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@GetMapping("/events/{eventId}/tickets")
	public ResponseEntity<ApiResponse> getAllTicketsForEvent(
			@RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
			@PathVariable(value = "eventId") long eventId) {
		ApiResponse apiResponse = new ApiResponse(
				this.ticketService.getTicketsByEventId(eventId, pageNo, pageSize, sortBy, sortDir));
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
}
