package com.aktsrl.intranetapp.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
/**
 * DTO  per la gestione delle richieste di permesso.
 * Contiene tutte le informazioni necessarie per rappresentare una richiesta
 */
public class RichiesteDto {

	private Long idUtente;
	@NotEmpty(message="tipo permesso non presente")
	private String tipoPermesso;
	@Past(message="formato data non valido")
	private LocalDateTime dataRichiesta;
	@Future(message="formato data non valido")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime dataInizio;
	@Future(message="formato data non valido")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime dataFine;
	@NotEmpty(message="inserisci una motivazione")
	private String motivazione; 
	private String statoRichiesta;
	private Long idRichiesta;
	private String emailUtente;
	private String noteHr;
	
	
	
	public RichiesteDto() {
		super();
	}


	public RichiesteDto(Long idUtente, String tipoPermesso, LocalDateTime dataInizio,
			LocalDateTime dataFine, String motivazione) {
		super();
		this.idUtente = idUtente;
		this.tipoPermesso = tipoPermesso;
		this.dataRichiesta = LocalDateTime.now();
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.motivazione = motivazione;
		this.statoRichiesta = "in attesa";
	} 
	
	
	


public String getTipoPermesso() {
	return tipoPermesso;
}
public Long getIdUtente() {
	return idUtente;
}


public void setIdUtente(Long idUtente) {
	this.idUtente = idUtente;
}


public void setTipoPermesso(String tipoPermesso) {
	this.tipoPermesso = tipoPermesso;
}


public LocalDateTime getDataRichiesta() {
	return dataRichiesta;
}


public void setDataRichiesta(LocalDateTime dataRichiesta) {
	this.dataRichiesta = dataRichiesta;
}


public LocalDateTime getDataInizio() {
	return dataInizio;
}


public void setDataInizio(LocalDateTime dataInizio) {
	this.dataInizio = dataInizio;
}


public LocalDateTime getDataFine() {
	return dataFine;
}


public void setDataFine(LocalDateTime dataFine) {
	this.dataFine = dataFine;
}


public String getMotivazione() {
	return motivazione;
}


public void setMotivazione(String motivazione) {
	this.motivazione = motivazione;
}


public String getStatoRichiesta() {
	return statoRichiesta;
}


public void setStatoRichiesta(String statoRichiesta) {
	this.statoRichiesta = statoRichiesta;
}


public Long getIdRichiesta() {
	return idRichiesta;
}


public void setIdRichiesta(Long idRichiesta) {
	this.idRichiesta = idRichiesta;
}


public String getEmailUtente() {
	return emailUtente;
}


public void setEmailUtente(String emailUtente) {
	this.emailUtente = emailUtente;
}


public String getNoteHr() {
	return noteHr;
}


public void setNoteHr(String noteHr) {
	this.noteHr = noteHr;
}


@Override
public String toString() {
	return "RichiesteDto [tipoPermesso=" + tipoPermesso + ", dataRichiesta=" + dataRichiesta + ", dataInizio="
			+ dataInizio + ", dataFine=" + dataFine + ", motivazione=" + motivazione + ", statoRichiesta="
			+ statoRichiesta + "]";
}

}

