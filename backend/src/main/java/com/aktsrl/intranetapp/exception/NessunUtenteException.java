package com.aktsrl.intranetapp.exception;
/**
 * Eccezione personalizzata lanciata quando non sono presenti utenti
 * nel sistema o non è possibile recuperare alcun utente.
 */
public class NessunUtenteException extends RuntimeException {

	public NessunUtenteException(String message) {
		super(message);
	}

}
