package com.events.app.payload;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDto {
	private Long id;
	private String title;
	private String description;
	private LocalDate eventDate;
	private LocalTime eventTime;

	public EventDto(Long id, String title, String description, LocalDate eventDate, LocalTime eventTime) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
	}

	public LocalTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(LocalTime eventTime) {
		this.eventTime = eventTime;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public EventDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
