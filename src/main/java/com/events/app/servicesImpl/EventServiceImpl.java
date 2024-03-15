package com.events.app.servicesImpl;

import org.springframework.stereotype.Service;

import com.events.app.entities.Event;
import com.events.app.payload.EventDto;
import com.events.app.repositories.EventRepository;
import com.events.app.services.EventService;

@Service
public class EventServiceImpl implements EventService {

	private EventRepository eventRepository;

	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public EventDto createEvent(EventDto event) {
		Event eventEntity = new Event();

		eventEntity.setDescription(event.getDescription());
		eventEntity.setTitle(event.getTitle());
		Event savedEvent = eventRepository.save(eventEntity);

		return new EventDto(savedEvent.getId(), savedEvent.getTitle(), savedEvent.getDescription());
	}

}
