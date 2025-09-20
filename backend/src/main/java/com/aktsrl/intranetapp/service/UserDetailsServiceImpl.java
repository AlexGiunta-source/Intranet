package com.aktsrl.intranetapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aktsrl.intranetapp.entity.Utente;
import com.aktsrl.intranetapp.repository.UtenteRepository;

/**
 * Implementazione del servizio UserDetailsService di Spring Security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
     private final UtenteRepository utenteRepository;
	
	 public UserDetailsServiceImpl(UtenteRepository utenteRepository) {
		super();
		this.utenteRepository = utenteRepository;
	}

	@Override
	public  UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Utente utente = utenteRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException ("Utente non trovato"));
		
		return new CustomUserDetails(utente);
	}

}
