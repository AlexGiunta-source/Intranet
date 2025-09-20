package com.aktsrl.intranetapp.exception;

/**
 * Eccezione personalizzata lanciata quando non sono presenti richieste
 * nel sistema o non è possibile recuperare alcuna richiesta.
 */
public class NessunaRichiestaException extends RuntimeException  {

	public NessunaRichiestaException(String message) {
		super(message);
		
	}

	
}
