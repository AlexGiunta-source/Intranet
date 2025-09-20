package com.aktsrl.intranetapp.exception;

/**
 * Eccezione personalizzata lanciata quando si verifica un errore relativo
 * all'email, ad esempio quando si tenta di registrare un'email già esistente.
 */
public class EmailException extends RuntimeException {

	public EmailException(String message) {
		super(message);
	}

}
