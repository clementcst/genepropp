package com.acfjj.app.utils;

public class Response {
	private Object value;
	private String message;
	private Boolean success;
	
	public Response() {
		
	}
	
	public Response(Object value) {
		super();
		this.value = value;
		this.message = null;
		this.success = true;
	}
	
	public Response(String message, Boolean success) {
		super();
		this.value = null;
		this.message = message;
		this.success = success;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
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
}
