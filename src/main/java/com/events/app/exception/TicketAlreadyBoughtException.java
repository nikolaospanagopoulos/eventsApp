package com.events.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TicketAlreadyBoughtException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Long ticketId;

	public TicketAlreadyBoughtException(Long ticketId) {
		super(String.format("Ticket with %s : has been already bought", ticketId));
		this.ticketId = ticketId;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
