package com.events.app.services;

import java.util.List;

import com.events.app.payload.EventDto;
import com.events.app.payload.EventResponsePaginationObj;

public interface EventService {
	EventDto createEvent(EventDto event);

	EventResponsePaginationObj getAllEvents(int pageNo, int pageSize, String sortBy, String sortDir);

	EventDto getEventById(long id);

	EventDto updateEvent(EventDto eventDto, long id);

	void deleteEventById(long id);
}
