package com.events.app.utilis;

import java.time.Instant;

public class TicketUtil {
	public static String generateUniqueTicketId(Long eventId, Long userId) {
		return "TICKET-" + eventId + "-" + userId + "-" + Instant.now().toEpochMilli();
	}
}
