package com.events.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PaymentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String problem;

	public PaymentException(String problem) {
		super(String.format("Couldn't handle your payment: '%s'", problem));
		this.problem = problem;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

}
