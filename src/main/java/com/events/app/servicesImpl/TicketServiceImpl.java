package com.events.app.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.events.app.entities.Event;
import com.events.app.entities.Ticket;
import com.events.app.entities.User;
import com.events.app.exception.ResourceNotFoundException;
import com.events.app.exception.TicketAlreadyBoughtException;
import com.events.app.exception.UserNotFoundException;
import com.events.app.payload.EventDto;
import com.events.app.payload.EventResponsePaginationObj;
import com.events.app.payload.PaymentDto;
import com.events.app.payload.TicketDto;
import com.events.app.payload.TicketResponsePaginationObj;
import com.events.app.payload.UserDto;
import com.events.app.repositories.EventRepository;
import com.events.app.repositories.TicketRepository;
import com.events.app.repositories.UserRepository;
import com.events.app.services.EmailService;
import com.events.app.services.TicketService;
import com.events.app.utilis.QRcodeUtil;
import com.events.app.utilis.TicketUtil;

@Service
public class TicketServiceImpl implements TicketService {
	TicketRepository ticketRepository;
	EventRepository eventRepository;
	UserRepository userRepository;
	EmailService emailService;

	ModelMapper modelMapper;

	public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper,
			EventRepository eventRepository, UserRepository userRepository, EmailService emailService) {
		super();
		this.ticketRepository = ticketRepository;
		this.modelMapper = modelMapper;
		this.eventRepository = eventRepository;
		this.userRepository = userRepository;
		this.emailService = emailService;
	}

	private void processTicketPurchase(String userEmail, String ticketId) {
		// Generate QR code for the ticket
		byte[] qrCode;
		try {
			qrCode = QRcodeUtil.generateQRCode(ticketId, 300, 300);
			emailService.sendTicketEmail(userEmail, qrCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("something went wrong");

		}

		// Send QR code via email

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

	@Override
	public TicketDto buyTicket(long eventId, long ticketId, PaymentDto paymentDto) {
		Event event = getEventById(eventId);
		Ticket ticket = getTicketByEventId(eventId, ticketId);
		if (!ticket.getEvent().getId().equals(event.getId())) {
			throw new ResourceNotFoundException("Ticket", "id", Long.toString(ticketId));
		}
		if (ticket.getTicketUniqueIdentifier() != null) {
			throw new TicketAlreadyBoughtException(ticketId);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedUsername = authentication.getName();
		
		User currentUser = userRepository.findByUsername(authenticatedUsername)
				.orElseThrow(() -> new UserNotFoundException("username", authenticatedUsername));

		ticket.setUser(currentUser);

		String uniqueIdentifier = TicketUtil.generateUniqueTicketId(eventId, currentUser.getId());

		ticket.setTicketUniqueIdentifier(uniqueIdentifier);
		processTicketPurchase(paymentDto.getEmail(), Long.toString(ticketId));
		ticketRepository.save(ticket);

		

		TicketDto boughtTicket = modelMapper.map(ticket, TicketDto.class);

		// TODO: check currentuser is same as user that wants to buy

		boughtTicket.setBoughtBy(modelMapper.map(currentUser, UserDto.class));

		return boughtTicket;

	}

}
