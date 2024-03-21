package com.events.app.payload;

import java.time.LocalDate;

import com.events.app.payload.dataInterface.DataResponse;

public class TicketDto implements DataResponse {
	private Long id;
	private String title;
	private String description;
	private double price;
	private String status;
	private LocalDate dateOfPurchase;
	private LocalDate createdDate;

	public TicketDto(Long id, String title, String description, double price, String status, LocalDate dateOfPurchase,
			LocalDate createdDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.status = status;
		this.dateOfPurchase = dateOfPurchase;
		this.createdDate = createdDate;
	}

	public TicketDto() {
		super();
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(LocalDate dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

}
