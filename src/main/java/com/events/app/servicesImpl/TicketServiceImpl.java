package com.events.app.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.events.app.entities.Event;
import com.events.app.entities.Ticket;
import com.events.app.exception.ResourceNotFoundException;
import com.events.app.payload.EventDto;
import com.events.app.payload.EventResponsePaginationObj;
import com.events.app.payload.TicketDto;
import com.events.app.payload.TicketResponsePaginationObj;
import com.events.app.repositories.EventRepository;
import com.events.app.repositories.TicketRepository;
import com.events.app.services.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	TicketRepository ticketRepository;
	EventRepository eventRepository;

	ModelMapper modelMapper;

	public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper,
			EventRepository eventRepository) {
		super();
		this.ticketRepository = ticketRepository;
		this.modelMapper = modelMapper;
		this.eventRepository = eventRepository;
	}

	@Override
	public TicketDto createTicket(long eventId, TicketDto ticketDto) {
		Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
		Event event = getEventById(eventId);
		ticket.setEvent(event);

		Ticket newTicket = ticketRepository.save(ticket);

		return modelMapper.map(newTicket, TicketDto.class);
	}

	public TicketResponsePaginationObj getTicketsByEventId(long eventId, int pageNo, int pageSize, String sortBy,
			String sortDir) {
		// Create the Sort object based on the direction and sortBy parameters
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();

		// Create a Pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		// Use the modified repository method that accepts a Pageable
		Page<Ticket> ticketsPage = ticketRepository.findByEventId(eventId, pageable);

		// Convert the content of the page to your DTOs
		List<TicketDto> content = ticketsPage.getContent().stream().map(t -> modelMapper.map(t, TicketDto.class))
				.collect(Collectors.toList());

		// Create a response object (assuming you have a similar object for pagination
		// response)
		TicketResponsePaginationObj ticketObjRes = new TicketResponsePaginationObj(content, ticketsPage.getNumber(),
				ticketsPage.getSize(), ticketsPage.getTotalElements(), ticketsPage.getTotalPages(),
				ticketsPage.isLast());

		return ticketObjRes;
	}

	private Event getEventById(long eventId) {
		return eventRepository.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event", "id", Long.toString(eventId)));
	}

	private Ticket getTicketByEventId(long eventId, long ticketId) {
		return ticketRepository.findById(ticketId)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket", "id", Long.toString(ticketId)));
	}

	@Override
	public TicketDto getTicketById(long eventId, long ticketId) {
		Event event = getEventById(eventId);
		Ticket ticket = getTicketByEventId(eventId, ticketId);
		if (!ticket.getEvent().getId().equals(event.getId())) {
			throw new ResourceNotFoundException("Ticket", "id", Long.toString(ticketId));
		}
		return modelMapper.map(ticket, TicketDto.class);
	}

	@Override
	public TicketDto updateTicket(long eventId, long ticketId, TicketDto ticketDto) {
		Event event = getEventById(eventId);
		Ticket ticket = getTicketByEventId(eventId, ticketId);

		if (!ticket.getEvent().getId().equals(event.getId())) {
			throw new ResourceNotFoundException("Ticket", "id", Long.toString(ticketId));
		}

		ticket.setCreatedDate(ticketDto.getCreatedDate());
		ticket.setDateOfPurchase(ticketDto.getDateOfPurchase());
		ticket.setDescription(ticketDto.getDescription());
		ticket.setPrice(ticketDto.getPrice());
		ticket.setStatus(ticketDto.getStatus());
		ticket.setTitle(ticketDto.getTitle());

		ticketRepository.save(ticket);

		return modelMapper.map(ticket, TicketDto.class);
	}

	@Override
	public void deleteTicket(long eventId, long ticketId) {
		// TODO Auto-generated method stub
		Event event = getEventById(eventId);
		Ticket ticket = getTicketByEventId(eventId, ticketId);
		if (!ticket.getEvent().getId().equals(event.getId())) {
			throw new ResourceNotFoundException("Ticket", "id", Long.toString(ticketId));
		}
		ticketRepository.delete(ticket);
	}

}
