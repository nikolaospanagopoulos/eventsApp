package com.events.app.services;

import com.events.app.payload.LoginDto;
import com.events.app.payload.RegisterDto;

public interface AuthService {
	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);
}
