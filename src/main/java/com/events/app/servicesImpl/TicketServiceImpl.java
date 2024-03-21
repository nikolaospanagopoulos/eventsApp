package com.events.app.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

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
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event", "id", Long.toString(eventId)));
		ticket.setEvent(event);

		Ticket newTicket = ticketRepository.save(ticket);

		return modelMapper.map(newTicket, TicketDto.class);
	}

	@Override

	/*
	 * 
	 * @Override public EventResponsePaginationObj getAllEvents(int pageNo, int
	 * pageSize, String sortBy, String sortDir) {
	 * 
	 * Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ?
	 * Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
	 * 
	 * Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
	 * System.out.println(pageable); Page<Event> events =
	 * eventRepository.findAll(pageable);
	 * 
	 * List<Event> listOfEvents = events.getContent(); List<EventDto> content =
	 * listOfEvents.stream().map(ev -> mapToDto(ev)).collect(Collectors.toList());
	 * EventResponsePaginationObj eventObjRes = new
	 * EventResponsePaginationObj(content, events.getNumber(), events.getSize(),
	 * events.getTotalElements(), events.getTotalPages(), events.isLast()); return
	 * eventObjRes; }
	 * 
	 * 
	 * 
	 */

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

}
