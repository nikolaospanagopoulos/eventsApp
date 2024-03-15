package com.events.app.services;

import com.events.app.payload.EventDto;

public interface EventService {
	EventDto createEvent(EventDto event);
}
