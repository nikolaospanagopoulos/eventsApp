package com.events.app.payload;

public class PaymentDto {
	private String name;
	private String surname;
	private String username;

	public PaymentDto(String name, String surname, String username) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
	}

	public PaymentDto() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
