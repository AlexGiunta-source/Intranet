package com.aktsrl.intranetapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aktsrl.intranetapp.exception.NessunaRichiestaException;
import com.aktsrl.intranetapp.exception.RichiestaNonTrovataException;
import com.aktsrl.intranetapp.exception.UtenteNonTrovatoException;
import com.aktsrl.intranetapp.model.dto.DashBoardDto;
import com.aktsrl.intranetapp.model.dto.FiltriDto;
import com.aktsrl.intranetapp.model.dto.RichiesteDto;
import com.aktsrl.intranetapp.response.Response;
import com.aktsrl.intranetapp.service.RichiesteService;

import jakarta.validation.Valid;

/**
 * Controller REST che gestisce le richieste degli utenti.
 * Contiene endpoint per salvare, aggiornare, eliminare e cercare richieste,
 * con autorizzazioni differenziate in base al ruolo dell'utente.
 */
@RestController
@RequestMapping("/api")
public class RichiesteController {

	private RichiesteService richiesteService;

	public RichiesteController(RichiesteService richiesteService) {
		this.richiesteService = richiesteService;

	}
    /**
     * Restituisce tutte le richieste presenti nel sistema.
     * Accessibile solo agli utenti con ruolo HR.
     *
     * @return lista delle richieste o errore se non trovate
     */
	@PreAuthorize("hasRole('HR')")
	@GetMapping("/mostraRichieste")

	public ResponseEntity<Response> mostraLeRichieste() {
		try {
			List <RichiesteDto> listaRichieste = richiesteService.mostraTutteLeRichieste();
			return ResponseEntity.ok().body(new Response("operazione effettuata con successo", HttpStatus.OK, listaRichieste));
		} catch (NessunaRichiestaException nessunaRichiesta) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(nessunaRichiesta.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
	}
	
    /**
     * Salva una nuova richiesta per l'utente autenticato.
     * Accessibile solo agli utenti con ruolo DIPENDENTE.
     *
     * @param richiesteDto dati della richiesta
     * @param userDetails dettagli dell'utente autenticato
     * @return richiesta salvata o errore
     */
	@PreAuthorize("hasRole('DIPENDENTE')")
	@PostMapping("/salvaRichiesta")
	public ResponseEntity <Response> salvaRichiesta(@RequestBody @Valid RichiesteDto richiesteDto, @AuthenticationPrincipal UserDetails userDetails) { 
		try {
			RichiesteDto richiestaSalvata = richiesteService.salvaRichiesta(richiesteDto, userDetails.getUsername());
			return ResponseEntity.ok().body(new Response("operazione effettuata con successo", HttpStatus.OK, richiestaSalvata));
		} catch (UtenteNonTrovatoException utenteNonTrovato) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response( utenteNonTrovato.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
	}
    /**
     * Elimina una richiesta tramite il suo id.
     * Accessibile solo agli utenti con ruolo DIPENDENTE.
     *
     * @param id identificativo della richiesta
     * @return messaggio di conferma o errore
     */
	@PreAuthorize("hasRole('DIPENDENTE')")
	@DeleteMapping("/eliminaRichiesta/{id}")
	public ResponseEntity<Response> eliminaRichiestaId(@PathVariable Long id) {
		try {
			richiesteService.cancellaRichiesta(id);
			return ResponseEntity.ok().body(new Response("Richiesta cancellata con successo", HttpStatus.OK, null));
		} catch (RichiestaNonTrovataException richiestaNonTrovata) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(richiestaNonTrovata.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
	}
    /**
     * Aggiorna una richiesta esistente tramite id.
     * Accessibile solo agli utenti con ruolo HR.
     *
     * @param id identificativo della richiesta
     * @param richiesteDto dati aggiornati della richiesta
     * @return richiesta aggiornata o errore
     */
	@PreAuthorize("hasRole('HR')")
	@PutMapping("/aggiornaRichiesta/{id}")
	public ResponseEntity<Response> aggiornaRichiestaId(@PathVariable Long id, @RequestBody  RichiesteDto richiesteDto) {
		try {
			RichiesteDto aggiornaRichiesta = richiesteService.aggiornaRichiesta(id , richiesteDto);
			return ResponseEntity.ok().body(new Response("operazione effettuata con successo", HttpStatus.OK, aggiornaRichiesta));
		} catch (RichiestaNonTrovataException richiestaNonTrovata) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(richiestaNonTrovata.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
	}
    /**
     * Restituisce tutte le richieste relative a un utente specifico.
     * Accessibile solo agli utenti con ruolo DIPENDENTE.
     *
     * @param id identificativo dell'utente
     * @return lista delle richieste o errore
     */
	@PreAuthorize("hasRole('DIPENDENTE')")
	@GetMapping("/cercaRichiestePerUtente/{id}")
	public ResponseEntity<Response> cercaRichiestePerUtente(@PathVariable Long id) {
		try {
			List <RichiesteDto> listaRichieste = richiesteService.cercaRichiestePerUtente(id);
			return ResponseEntity.ok().body(new Response("operazione effettuata con successo", HttpStatus.OK, listaRichieste));
		} catch (UtenteNonTrovatoException utenteNonTrovato) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(utenteNonTrovato.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
	}
    /**
     * Restituisce le richieste filtrate in base allo stato e all'id dell'utente.
     * Accessibile solo agli utenti con ruolo DIPENDENTE.
     *
     * @param statoRichiesta stato della richiesta
     * @param idUtente identificativo dell'utente
     * @return lista delle richieste o errore
     */
	@PreAuthorize("hasRole('DIPENDENTE')")
	@GetMapping("/cerca/stato/{statoRichiesta}/{idUtente}")
	public ResponseEntity<Response> cercaRichiestaPerStato(@PathVariable String statoRichiesta, @PathVariable Long idUtente) {
		
		try {
			List <RichiesteDto> listaRichiesteStato = richiesteService.cercaRichiestePerStato(statoRichiesta ,idUtente);
			return ResponseEntity.ok().body(new Response("operazione effettuata con successo", HttpStatus.OK, listaRichiesteStato));
		} catch (RichiestaNonTrovataException  richiestaNonTrovata) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(richiestaNonTrovata.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
	}
    /**
     * Restituisce una dashboard riepilogativa delle richieste per HR.
     *
     * @return dashboard con statistiche delle richieste o errore
     */
	@PreAuthorize("hasRole('HR')")
	@GetMapping("/dashBoardHr")
	public ResponseEntity<Response> dashBoardHr() {
		try {
			DashBoardDto dashBoardDto = richiesteService.dashBoardRichieste();
			return ResponseEntity.ok().body(new Response("operazione effettuata con successo", HttpStatus.OK, dashBoardDto));
		} catch (RichiestaNonTrovataException richiestaNonTrovata) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(richiestaNonTrovata.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
	}
    /**
     * Restituisce le richieste filtrate in base a parametri come stato, data e id utente.
     * Accessibile solo agli utenti con ruolo HR.
     *
     * @param filtri oggetto con i parametri di filtro
     * @return lista delle richieste filtrate o errore
     */
	@PreAuthorize("hasRole('HR')")
	@PostMapping("/richiestefiltrate")
	public ResponseEntity<Response> richiesteFiltrate(@RequestBody @Valid FiltriDto filtri) {
		try {
			List <RichiesteDto> richiesteFiltrate = richiesteService.richiesteFiltrate(filtri.getStatoRichiesta(), filtri.getDataRichiesta(), filtri.getIdUtente());
			return ResponseEntity.ok().body(new Response("operazione effettuata con successo", HttpStatus.OK, richiesteFiltrate));
		} catch (RuntimeException erroreGenerico) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
		
	}
    /**
     * Restituisce le richieste di un utente con ruolo DIPENDENTE filtrate per stato e id.
     *
     * @param statoRichiesta stato della richiesta
     * @param idUtente identificativo dell'utente
     * @return lista delle richieste o errore
     */
	@PreAuthorize("hasRole('DIPENDENTE')")
	@GetMapping("/cercaRichiestePerStatoEId/{statoRichiesta}/{idUtente}")
	public ResponseEntity <Response> cercaRichiestePerStatoEId( @PathVariable String statoRichiesta, @PathVariable Long  idUtente){
		try {
			List <RichiesteDto> richiesteFiltrate = richiesteService.cercaRichiestePerStato(statoRichiesta,idUtente );
			return ResponseEntity.ok().body(new Response("operazione effettuata con successo", HttpStatus.OK, richiesteFiltrate));
		}catch(RichiestaNonTrovataException richiestaNonTrovata) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(richiestaNonTrovata.getMessage(), HttpStatus.NOT_FOUND, null));
		}catch(RuntimeException erroreGenerico) {
			return ResponseEntity.badRequest().body(new Response("operazione fallita", HttpStatus.BAD_REQUEST, null));
		}
	}
	

}