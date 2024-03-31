package com.events.app.servicesImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.events.app.entities.Event;
import com.events.app.exception.ResourceAlreadyExistsException;
import com.events.app.exception.ResourceNotFoundException;
import com.events.app.payload.EventDto;
import com.events.app.payload.EventResponsePaginationObj;
import com.events.app.repositories.EventRepository;
import com.events.app.services.EventService;

@Service
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	private EventDto mapToDto(Event savedEvent) {
		return new EventDto(savedEvent.getId(), savedEvent.getTitle(), savedEvent.getDescription(),
				savedEvent.getEventDate(), savedEvent.getEventTime());
	}

	private Event mapToEventEntity(EventDto eventDto) {
		Event eventEntity = new Event();

		eventEntity.setDescription(eventDto.getDescription());
		eventEntity.setTitle(eventDto.getTitle());
		eventEntity.setEventDate(eventDto.getEventDate());
		eventEntity.setEventTime(eventDto.getEventTime());
		return eventEntity;
	}

	@Override
	public EventDto createEvent(EventDto event) {
		if (eventRepository.existsByTitle(event.getTitle())) {
			throw new ResourceAlreadyExistsException("Event", "title", event.getTitle());
		}
		Event eventEntity = mapToEventEntity(event);
		Event savedEvent = eventRepository.save(eventEntity);
		EventDto eventDto = mapToDto(savedEvent);
		return eventDto;
	}

	@Override
	public EventResponsePaginationObj getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();

		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Event> events = eventRepository.findAll(pageable);

		List<Event> listOfEvents = events.getContent();
		List<EventDto> content = listOfEvents.stream().map(ev -> mapToDto(ev)).collect(Collectors.toList());
		EventResponsePaginationObj eventObjRes = new EventResponsePaginationObj(content, events.getNumber(),
				events.getSize(), events.getTotalElements(), events.getTotalPages(), events.isLast());
		return eventObjRes;
	}

	@Override
	public EventDto getEventById(long id) {
		Event found = eventRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event", "id", Long.toString(id)));
		return mapToDto(found);
	}

	@Override
	public EventDto updateEvent(EventDto eventDto, long id) {
		Event found = eventRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event", "id", Long.toString(id)));
		found.setDescription(eventDto.getDescription());
		found.setEventDate(eventDto.getEventDate());
		found.setEventTime(eventDto.getEventTime());
		found.setTitle(eventDto.getTitle());
		Event updatedEvent = eventRepository.save(found);
		return mapToDto(updatedEvent);
	}

	@Override
	public void deleteEventById(long id) {
		Event found = eventRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event", "id", Long.toString(id)));
		eventRepository.delete(found);
	}

}
