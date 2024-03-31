package com.events.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.events.app.payload.PaymentDto;
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

	@PutMapping("/events/{eventId}/tickets/{ticketId}/buy")
	public ResponseEntity<ApiResponse> buyTicket(@RequestBody PaymentDto paymentDto,
			@PathVariable(value = "eventId") long eventId, @PathVariable(value = "ticketId") long ticketId) {
		ApiResponse apiResponse = new ApiResponse(this.ticketService.buyTicket(eventId, ticketId, paymentDto));
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
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

	@GetMapping("/events/{eventId}/tickets/{ticketId}")
	public ResponseEntity<ApiResponse> getTicketForEventById(@PathVariable(value = "eventId") long eventId,
			@PathVariable(value = "ticketId") long ticketId) {
		ApiResponse apiResponse = new ApiResponse(this.ticketService.getTicketById(eventId, ticketId));
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@PutMapping("/events/{eventId}/tickets/{ticketId}")
	public ResponseEntity<ApiResponse> updateTicket(@PathVariable(value = "eventId") long eventId,
			@PathVariable(value = "ticketId") long ticketId, @RequestBody TicketDto ticketDto) {
		ApiResponse apiResponse = new ApiResponse(this.ticketService.updateTicket(eventId, ticketId, ticketDto));
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/events/{eventId}/tickets/{ticketId}")
	public ResponseEntity<ApiResponse> deleteTicket(@PathVariable(value = "eventId") long eventId,
			@PathVariable(value = "ticketId") long ticketId) {
		ticketService.deleteTicket(eventId, ticketId);
		return new ResponseEntity<>(new ApiResponse("deleted successfully"), HttpStatus.OK);
	}
}
