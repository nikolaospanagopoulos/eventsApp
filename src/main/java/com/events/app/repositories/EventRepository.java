package com.events.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.events.app.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	
}
