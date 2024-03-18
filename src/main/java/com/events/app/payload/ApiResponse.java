package com.events.app.payload;

import com.events.app.payload.dataInterface.DataResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ApiResponse {
	private Boolean success;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private DataResponse data;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

	public ApiResponse(String message, DataResponse data) {
		super();
		this.success = true;
		this.message = message;
		this.data = data;
	}

	public ApiResponse(String message) {
		super();
		this.success = true;
		this.message = message;

	}

	public ApiResponse(DataResponse data) {
		super();
		this.success = true;
		this.data = data;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public DataResponse getData() {
		return data;
	}

	public void setData(DataResponse data) {
		this.data = data;
	}

}
