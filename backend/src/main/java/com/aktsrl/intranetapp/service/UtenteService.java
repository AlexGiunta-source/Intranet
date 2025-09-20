package com.aktsrl.intranetapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aktsrl.intranetapp.entity.Utente;
import com.aktsrl.intranetapp.exception.EmailException;
import com.aktsrl.intranetapp.exception.NessunUtenteException;
import com.aktsrl.intranetapp.exception.UtenteNonTrovatoException;
import com.aktsrl.intranetapp.mapper.UtenteMapper;
import com.aktsrl.intranetapp.model.dto.UtenteDto;
import com.aktsrl.intranetapp.repository.UtenteRepository;

/**
 * Servizio per la gestione degli utenti.
 * Contiene la logica di business per registrazione, ricerca, visualizzazione degli utenti. 
 */
@Service
public class UtenteService {

	private final UtenteRepository utenteRepository;
	private final UtenteMapper utenteMapper;
	private final PasswordEncoder passwordEncoder;

	public UtenteService(UtenteRepository utenteRepository, UtenteMapper utenteMapper, PasswordEncoder passwordEncoder) {
		this.utenteRepository = utenteRepository;
		this.utenteMapper = utenteMapper;
		this.passwordEncoder = passwordEncoder;
	}
	  /**
     * Registra un nuovo utente nel sistema.
     * La password viene cifrata e il ruolo impostato a "DIPENDENTE".
     *
     * @param utentedto dati del nuovo utente
     * @return UtenteDto dell'utente registrato
     * @throws EmailException se l'email è già registrata
     */
	@Transactional
	public UtenteDto registraUtente(UtenteDto utentedto) {
		if(utenteRepository.findByEmail(utentedto.getEmail()).isPresent()) {
			throw new EmailException("Email già regitrata");
		}
		Utente utente = utenteMapper.toEntity(utentedto);
		utente.setPassword(passwordEncoder.encode(utentedto.getPassword()));
		utente.setRuolo("DIPENDENTE");
		utenteRepository.save(utente);
		return utenteMapper.toDto(utente);
	}
    /**
     * Restituisce la lista di tutti gli utenti presenti nel sistema.
     * Utilizzato generalmente da HR.
     *
     * @return lista di UtenteDto
     * @throws NessunUtenteException se non ci sono utenti registrati
     */
	@Transactional(readOnly = true)
	public List<UtenteDto> mostraUtenti() {
		List<Utente> listaUtenti = utenteRepository.findAll();
		if(listaUtenti.isEmpty()) {
			throw new NessunUtenteException("Nessun utente presente");
		}
		List <UtenteDto> utentiDto = new ArrayList<>();
		for (Utente utente : listaUtenti) {
			UtenteDto utenteDto = utenteMapper.toDto(utente);
			utentiDto.add(utenteDto);
	}
			return utentiDto;	
	}
    /**
     * Cerca un utente tramite l'email.
     *
     * @param Email  dell'utente da cercare
     * @return UtenteDto dell'utente trovato
     * @throws UtenteNonTrovatoException se non esiste alcun utente con l'email specificata
     */
	@Transactional(readOnly = true)
	public UtenteDto cercaUtentePerEmail(String Email) {
		Utente utente = utenteRepository.findByEmail(Email).orElseThrow(()-> new UtenteNonTrovatoException("Nessuna utente trovato"));
		UtenteDto utenteDto = utenteMapper.toDto(utente);
		return utenteDto;
	}
    /**
     * Cerca un utente tramite il suo ID.
     *
     * @param id  dell'utente da cercare
     * @return UtenteDto dell'utente trovato
     * @throws UtenteNonTrovatoException se non esiste alcun utente con l'ID specificato
     */
	@Transactional
	public UtenteDto cercaUtentePerId(Long id) {
		Utente utente = utenteRepository.findByIdUtente(id).orElseThrow(()-> new UtenteNonTrovatoException("Nessun utente trovato"));
		UtenteDto utenteDto = utenteMapper.toDto(utente);
		return utenteDto;
	}
}
