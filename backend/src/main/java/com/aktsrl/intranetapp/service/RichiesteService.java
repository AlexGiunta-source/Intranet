package com.aktsrl.intranetapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aktsrl.intranetapp.entity.Richieste;
import com.aktsrl.intranetapp.entity.Utente;
import com.aktsrl.intranetapp.exception.NessunaRichiestaException;
import com.aktsrl.intranetapp.exception.RichiestaNonTrovataException;
import com.aktsrl.intranetapp.exception.UtenteNonTrovatoException;
import com.aktsrl.intranetapp.mapper.RichiesteMapper;
import com.aktsrl.intranetapp.model.dto.DashBoardDto;
import com.aktsrl.intranetapp.model.dto.RichiesteDto;
import com.aktsrl.intranetapp.repository.RichiesteRepository;
import com.aktsrl.intranetapp.repository.UtenteRepository;

/**
 * Service per la gestione delle richieste.
 * Contiene i metodi per creare, aggiornare, eliminare e filtrare richieste.
 */
@Service
public class RichiesteService {

	private final RichiesteRepository richiesteRepository;
	private final RichiesteMapper richiesteMapper;
	private final UtenteRepository  utenteRepository;

	public RichiesteService(RichiesteRepository richiesterepository, RichiesteMapper richiesteMapper,UtenteService utenteservice, UtenteRepository utenteRepository) {
		this.richiesteRepository = richiesterepository;
		this.richiesteMapper = richiesteMapper;
		this.utenteRepository = utenteRepository;

	}
	 /**
     * Restituisce tutte le richieste presenti nel sistema.
     * Se non sono presenti richieste, lancia una eccezione.
     *
     * @return lista di RichiesteDto
     * @throws NessunaRichiestaException se non ci sono richieste
     */
	@Transactional(readOnly = true)
	public List <RichiesteDto> mostraTutteLeRichieste() {
		List<Richieste> richieste = richiesteRepository.findAll();
		if(richieste.isEmpty()) {
			throw new NessunaRichiestaException("Nessuna richiesta presente nel sistema");
		}
		List <RichiesteDto> richiesteDto = new ArrayList<>();
		for (Richieste richiesta : richieste) {
			RichiesteDto listaRichieste = richiesteMapper.toDto(richiesta);
			richiesteDto.add(listaRichieste);
		}
		return richiesteDto;
	}
    /**
     * Cancella una richiesta in base all'id.
     *
     * @param id id della richiesta da cancellare
     * @throws RichiestaNonTrovataException se la richiesta non esiste
     */
	@Transactional
	public void cancellaRichiesta(Long id) {
		Richieste richiestaDaCancellare = richiesteRepository.findById(id).orElseThrow(()-> new RichiestaNonTrovataException("Richiesta non trovata"));
		richiesteRepository.delete(richiestaDaCancellare);
	}
    /**
     * Salva una nuova richiesta associata a un utente tramite email.
     *
     * @param richiestedto DTO della richiesta da salvare
     * @param email  dell'utente che crea la richiesta
     * @return richiesta salvata come DTO
     * @throws UtenteNonTrovatoException se l'utente non esiste
     */
	@Transactional
	public RichiesteDto salvaRichiesta(RichiesteDto richiestedto, String email) {
		Utente convalidaUtente = utenteRepository.findByEmail(email).orElseThrow(()-> new UtenteNonTrovatoException("utente non trovato"));
		Richieste richiestaSalvata = richiesteMapper.toEntity(richiestedto);
		richiestaSalvata.setUtente(convalidaUtente);
		richiesteRepository.save(richiestaSalvata);
		return richiesteMapper.toDto(richiestaSalvata); 
	}
    /**
     * Aggiorna lo stato e le note HR di una richiesta.
     *
     * @param id id della richiesta da aggiornare
     * @param richiestedto DTO con i dati aggiornati
     * @return richiesta aggiornata come DTO
     * @throws RichiestaNonTrovataException se la richiesta non esiste
     */
	@Transactional
	public RichiesteDto aggiornaRichiesta(Long id, RichiesteDto richiestedto) {
		
		Richieste richiestaAggiornata = richiesteRepository.findById(id).orElseThrow(() -> new RichiestaNonTrovataException("Richiesta non trovata"));
		richiestaAggiornata.setStatoRichiesta(richiestedto.getStatoRichiesta());
		richiestaAggiornata.setNoteHr(richiestedto.getNoteHr());
		richiesteRepository.save(richiestaAggiornata);
		return richiesteMapper.toDto(richiestaAggiornata);
	}
    /**
     * Restituisce tutte le richieste di un determinato utente.
     *
     * @param id  dell'utente
     * @return lista di RichiesteDto
     * @throws UtenteNonTrovatoException se l'utente non esiste
     */
	@Transactional
	public List<RichiesteDto> cercaRichiestePerUtente(Long id) {
		Utente convalidaUtente = utenteRepository.findByIdUtente(id).orElseThrow(()-> new UtenteNonTrovatoException("Utente non trovato"));
		List<Richieste> richieste = richiesteRepository.findRichiesteByUtente(convalidaUtente);
		List<RichiesteDto> richiesteDto = new ArrayList<>();
		for (Richieste richiesta : richieste) {
			RichiesteDto listaRichieste = richiesteMapper.toDto(richiesta);
			richiesteDto.add(listaRichieste);
		}
		return richiesteDto;
	}

	@Transactional
    /**
     * Restituisce le richieste filtrate per stato e id utente, ordinate dalla più vecchia.
     *
     * @param statoRichiesta stato della richiesta
     * @param idUtente id dell'utente
     * @return lista di RichiesteDto
     * @throws RichiestaNonTrovataException se non ci sono richieste corrispondenti
     */
	public List<RichiesteDto> cercaRichiestePerStato(String statoRichiesta, Long idUtente) {
		List<Richieste> richieste = richiesteRepository.findByStatoRichiestaAndUtente_IdUtenteOrderByDataRichiestaAsc(statoRichiesta, idUtente);
		if(richieste.isEmpty()) {
			throw new RichiestaNonTrovataException("Richiesta non trovata");
		}
		List <RichiesteDto> richiesteDto = new ArrayList<>();
		for (Richieste richiesta : richieste) {
			RichiesteDto listaRichieste = richiesteMapper.toDto(richiesta);
			richiesteDto.add(listaRichieste);
		}
		return richiesteDto;
	}
    /**
     * Genera una dashboard delle richieste, con conteggi per stato e tipo permesso.
     *
     * @return DashBoardDto con le statistiche delle richieste
     * @throws RichiestaNonTrovataException se non ci sono richieste
     */
	@Transactional
	public DashBoardDto dashBoardRichieste() {
		List<Richieste> richieste = richiesteRepository.findAll();
		if(richieste.isEmpty()) {
			throw new RichiestaNonTrovataException("Richiesta non trovata");
		}
		Integer numeroApprovate = 0, numeroInAttesa = 0, numeroRifiutate = 0, malattia = 0, ferie = 0, permesso = 0 ;
		DashBoardDto dto = new DashBoardDto();
		dto.setNumeroTotaleRichieste(richieste.size());
		if (richieste.size() > 0) {
			for (Richieste r : richieste) {
				if (r.getStatoRichiesta().equalsIgnoreCase("accettata")) {
					numeroApprovate++;
				} else if (r.getStatoRichiesta().equalsIgnoreCase("rifiutata")) {
					numeroRifiutate++;
				}else if(r.getStatoRichiesta().equalsIgnoreCase("in attesa")) {
					numeroInAttesa++;
				}
				if(r.getTipoPermesso().equalsIgnoreCase("malattia")) {
					malattia++;
				}else if(r.getTipoPermesso().equalsIgnoreCase("ferie")) {
					ferie++;
				}else if(r.getTipoPermesso().equalsIgnoreCase("permesso")) {
					permesso++;
				}

			}
			
		}
		dto.setNumeroApprovate(numeroApprovate);
		dto.setNumeroRifiutate(numeroRifiutate);
		dto.setNumeroInAttesa(numeroInAttesa);
		dto.setMalattia(malattia);
		dto.setFerie(ferie);
		dto.setPermesso(permesso);
		return dto;

	}
	
    /**
     * Restituisce le richieste filtrate per stato, data richiesta e id utente.
     *
     * @param statoRichiesta stato della richiesta (opzionale)
     * @param dataRichiesta data della richiesta (opzionale)
     * @param idUtente id dell'utente
     * @return lista di RichiesteDto filtrate
     */
	@Transactional
	public List <RichiesteDto> richiesteFiltrate(String statoRichiesta, LocalDate dataRichiesta, Long idUtente){
		List <RichiesteDto> richiesteDto = new ArrayList<>();
		if (dataRichiesta != null) {
			LocalDateTime dataRichiestaInizio = dataRichiesta.atStartOfDay();
			LocalDateTime dataRichiestaFine = dataRichiesta.atTime(LocalTime.MAX);
			List <Richieste> richieste = richiesteRepository.getRichiesteFiltrate(statoRichiesta, dataRichiestaInizio, dataRichiestaFine, idUtente);
			for (Richieste r : richieste) {
				RichiesteDto risultato = richiesteMapper.toDto(r);
				richiesteDto.add(risultato);
				} 
			
			return richiesteDto;
		}
		List <Richieste> richieste = richiesteRepository.getRichiesteFiltrate(statoRichiesta, null, null, idUtente);
		for (Richieste r : richieste) {
			RichiesteDto risultato = richiesteMapper.toDto(r);
			richiesteDto.add(risultato);
			}
		return richiesteDto;
	}
	
}
