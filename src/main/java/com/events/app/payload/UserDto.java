package com.events.app.payload;

import com.events.app.payload.dataInterface.DataResponse;

public class UserDto implements DataResponse{
	private String name;
	private String username;
	private String surname;
	private String email;

	public UserDto(String name, String username, String surname, String email) {
		super();
		this.name = name;
		this.username = username;
		this.surname = surname;
		this.email = email;
	}
	public UserDto() {
	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
