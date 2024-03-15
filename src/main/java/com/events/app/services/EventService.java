package com.events.app.services;

import java.util.List;

import com.events.app.payload.EventDto;

public interface EventService {
	EventDto createEvent(EventDto event);

	List<EventDto> getAllEvents();
}
