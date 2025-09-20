package com.aktsrl.intranetapp.exception;
/**
 * Eccezione personalizzata lanciata quando un utente specifico
 * non viene trovato nel sistema.
 */
public class UtenteNonTrovatoException extends RuntimeException {

	public UtenteNonTrovatoException(String message) {
		super(message);
	}

}
