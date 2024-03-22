package com.events.app.services;

import com.events.app.payload.TicketDto;
import com.events.app.payload.TicketResponsePaginationObj;

public interface TicketService {
	TicketDto createTicket(long eventId, TicketDto ticketDto);

	TicketResponsePaginationObj getTicketsByEventId(long eventId, int pageNo, int pageSize, String sortBy,
			String sortDir);

	TicketDto getTicketById(long eventId, long ticketId);

	TicketDto updateTicket(long eventId, long ticketId, TicketDto ticketDto);

	void deleteTicket(long eventId, long ticketId);
}
