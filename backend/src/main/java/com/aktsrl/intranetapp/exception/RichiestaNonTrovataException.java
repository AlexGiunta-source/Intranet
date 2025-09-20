package com.aktsrl.intranetapp.exception;
/**
 * Eccezione personalizzata lanciata quando una richiesta specifica
 * non viene trovata nel sistema.
 */
public class RichiestaNonTrovataException extends RuntimeException  {

	public RichiestaNonTrovataException(String message) {
		super(message);
	}

	
}
