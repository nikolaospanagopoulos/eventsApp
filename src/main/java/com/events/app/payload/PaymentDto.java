package com.events.app.payload;

public class PaymentDto {
	private String name;
	private String surname;
	private String username;
	private String email;

	public PaymentDto(String name, String surname, String username, String email) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
