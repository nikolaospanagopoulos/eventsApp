package com.events.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.app.payload.JwtResponseDto;
import com.events.app.payload.LoginDto;
import com.events.app.payload.RegisterDto;
import com.events.app.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<JwtResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
		String token = authService.login(loginDto);
		JwtResponseDto jwtResponseDto = new JwtResponseDto();
		jwtResponseDto.setAccessToken(token);
		Cookie jwtCookie = new Cookie("JWT", token);
		jwtCookie.setHttpOnly(true);
		jwtCookie.setPath("/"); // Or specify the path where the cookie is relevant
		jwtCookie.setMaxAge(7 * 24 * 60 * 60); // Expires in 7 days
		response.addCookie(jwtCookie);
		return ResponseEntity.ok(jwtResponseDto);
	}

	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletResponse response) {
		Cookie jwtCookie = new Cookie("JWT", null);
		jwtCookie.setHttpOnly(true);
		jwtCookie.setPath("/");
		jwtCookie.setMaxAge(0); // Remove the cookie
		response.addCookie(jwtCookie);

		return ResponseEntity.ok("Logged out successfully");
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public AuthService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

}
