package com.events.app.services;

import java.util.List;

import com.events.app.payload.TicketDto;
import com.events.app.payload.TicketResponsePaginationObj;

public interface TicketService {
	TicketDto createTicket(long eventId, TicketDto ticketDto);

	TicketResponsePaginationObj getTicketsByEventId(long eventId, int pageNo, int pageSize, String sortBy,
			String sortDir);
}
