package com.events.app.payload;

public class ErrorResponseDto {
	private boolean success;
	private String message;
	private String errorCode; // Optional, depending on your application's needs

	public ErrorResponseDto(String message, String errorCode) {
		this.success = false;
		this.message = message;
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
