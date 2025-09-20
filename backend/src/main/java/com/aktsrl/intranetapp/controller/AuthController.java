package com.aktsrl.intranetapp.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aktsrl.intranetapp.entity.Utente;
import com.aktsrl.intranetapp.exception.EmailException;
import com.aktsrl.intranetapp.model.dto.LoginDto;
import com.aktsrl.intranetapp.model.dto.UtenteDto;
import com.aktsrl.intranetapp.response.Response;
import com.aktsrl.intranetapp.service.CustomUserDetails;
import com.aktsrl.intranetapp.service.UtenteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
/**
 * Controller REST per la gestione dell'autenticazione e della registrazione utenti.
 * Espone endpoint per:
 * Registrazione di un nuovo utente 
 * Login con gestione della sessione
 * Logout e invalidazione della sessione
 * 
 */
@RestController
@RequestMapping("/api")
public class AuthController {
	
	private final UtenteService utenteService;
    private final AuthenticationManager authenticationManager;
    
    
    public AuthController(UtenteService utenteService, AuthenticationManager authenticationManager) {
		this.utenteService = utenteService;
		this.authenticationManager = authenticationManager;
	}
    
    /**
     * Endpoint per la  registrazione di un nuovo utente
     * @param utenteDto oggetto contenente i dati dell'utente da registrare
     * @return ResponseEntity con: 
     * Messaggio di conferma
     * EmailException email già in uso
     * Exception errore generico
     */
    @PostMapping("/auth/register")
    public ResponseEntity<Response> register(@RequestBody @Valid UtenteDto utenteDto){ 
	try{
		UtenteDto utenteRegistrato = utenteService.registraUtente(utenteDto);
		return ResponseEntity.ok().body(new Response("Utente registrato correttamente", HttpStatus.OK, utenteRegistrato));
	}catch(EmailException  email){ 
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Email già registrata", HttpStatus.CONFLICT, null));
	}catch(Exception errore) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Errore durante la registrazione", HttpStatus.INTERNAL_SERVER_ERROR, null));
	}
	}
    
    /**
     * Endpoint per il login 
     * @param utenteDto oggetto contenente email e password 
     * @return ResponseEntity con: 
     * Messaggio di conferma
     * AuthenticationException credenziali errate
     */
    @PostMapping("/auth/login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
    	try {
			Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
	        );
			SecurityContextHolder.getContext().setAuthentication(authentication);
			HttpSession session = request.getSession(true);
	        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	        Utente utente = userDetails.getUtente();
	        return ResponseEntity.ok().body(new Response("Login effettuato correttamente", HttpStatus.OK, utente));
		}catch(AuthenticationException errore) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("Credenziali errate", HttpStatus.UNAUTHORIZED, null));
		}	
	}
    
    /**
     * Endpoint per effettuare il logout di un utente.
     * Invalida la sessione
     *
     * @param request oggetto  per accedere alla sessione
     * @return ResponseEntity con:  
     * messaggio di conferma del logout
     */
    @PostMapping("/auth/logout")
	public ResponseEntity <Response> logout(HttpServletRequest request){
		request.getSession().invalidate();
		return ResponseEntity.ok().body(new Response("Logout effettuato con successo", HttpStatus.OK, null));
	}

}
