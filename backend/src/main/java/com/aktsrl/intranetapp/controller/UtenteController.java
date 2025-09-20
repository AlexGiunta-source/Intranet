 package com.aktsrl.intranetapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aktsrl.intranetapp.exception.NessunUtenteException;
import com.aktsrl.intranetapp.exception.UtenteNonTrovatoException;
import com.aktsrl.intranetapp.model.dto.UtenteDto;
import com.aktsrl.intranetapp.response.Response;
import com.aktsrl.intranetapp.service.UtenteService;

/**
 * Controller che espone gli endpoint REST per la gestione degli utenti.
 * Alcuni metodi sono protetti e accessibili soltanto a utenti con ruolo HR
 */
@RestController 
@RequestMapping("/api")
public class UtenteController {

	private final  UtenteService utenteService;
	
	public  UtenteController(UtenteService utenteService) {
		this.utenteService = utenteService;
	}
    /**
     * Restituisce la lista completa di tutti gli utenti registrati.
     * Accessibile solo agli utenti con ruolo HR.
     *
     * @return ResponseEntity contenente la lista degli utenti in caso di successo, in caso di errore: 
     * NessunUtenteException se non ci sono utenti presenti,
     * RuntimeException in caso di errore generico.
     */
	@PreAuthorize("hasRole('HR')")
	@GetMapping("/mostraUtenti")
	public ResponseEntity<Response> mostraUtenti(){
		try {
			List <UtenteDto> listaUtenti = utenteService.mostraUtenti();
			return ResponseEntity.ok().body(new Response ("operazione effettuata con successo",HttpStatus.OK, listaUtenti));
		}catch(NessunUtenteException nessunUtente) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(nessunUtente.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico ) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
	}
    /**
     * Cerca un utente tramite il suo indirizzo email.
     * Accessibile solo agli utenti con ruolo HR.
     *
     * @param email indirizzo email dell'utente da ricercare
     * @return ResponseEntity contenente l'utente trovato in caso di successo,
     * UtenteNonTrovatoException se l'utente non esiste,
     * RuntimeException in caso di errore generico.
     */
	@PreAuthorize("hasRole('HR')")
	@GetMapping("/utente/{email}")
	public ResponseEntity <Response> cercaUtentePerEmail(@PathVariable String  email) {
		try {
			 UtenteDto utente = utenteService.cercaUtentePerEmail(email);
			return ResponseEntity.ok().body(new Response ("operazione effettuata con successo", HttpStatus.OK, utente));
		}catch(UtenteNonTrovatoException utenteNonTrovato) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(utenteNonTrovato.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico ) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
		
	}

    /**
     * Cerca un utente nel sistema tramite il suo ID.
     * Accessibile solo agli utenti con ruolo HR.
     *
     * @param id identificativo univoco dell'utente
     * @return ResponseEntity contenente l'utente trovato in caso di successo,
     * UtenteNonTrovatoException se l'utente non esiste,
     * RuntimeException in caso di errore generico.
     */
	@PreAuthorize("hasRole('HR')")
	@GetMapping("/cercaUtenteId/{id}")
	public ResponseEntity<Response> cercaUtenteId(@PathVariable Long id ){
		try {
			UtenteDto utenteDto =utenteService.cercaUtentePerId(id);	
			return ResponseEntity.ok().body(new Response("operazione effettuata con successo", HttpStatus.OK, utenteDto));
		}
		catch(UtenteNonTrovatoException utenteNonTrovato ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(utenteNonTrovato.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico ) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
		
	}
	
}
