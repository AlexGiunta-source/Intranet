package com.aktsrl.intranetapp.response;

import java.util.Map;

import org.springframework.http.HttpStatus;

/**
 * Classe di risposta generica.
 * Contiene un messaggio, lo stato HTTP, eventuali dati e errori.
 */
public class Response {
	
	private String message;
	private HttpStatus statusCode;
	private Object data;
	private Map<String, String> errors;
	
	public Response() {
		super();
	}
	
	public Response(String message, HttpStatus statusCode, Object data) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
	}

	public Response(String message, HttpStatus statusCode, Object data, Map<String, String> errors) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.data = data;
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

}



