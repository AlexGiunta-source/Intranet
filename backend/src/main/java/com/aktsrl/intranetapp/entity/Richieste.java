package com.aktsrl.intranetapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity che rappresenta una richiesta effettuata da un utente.
 * 
 * Contiene informazioni sul tipo di permesso richiesto, date,
 * motivazioni, stato della richiesta e note dell'HR.
 * La relazione ManyToOne con {@link Utente} indica l'utente
 * a cui appartiene la richiesta.
 */
@Entity
@Table(name ="Richieste")

public class Richieste {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRichiesta;
	
	
	
	@Column(name = "tipo_permesso", length = 100)
	private String tipoPermesso;
	
	@Column(name = "data_richiesta", length = 100)
	private LocalDateTime dataRichiesta;
	
	@Column(name = "data_inizio", length = 100)
	private LocalDateTime dataInizio;
	
	@Column(name = "data_fine", length = 100)
	private LocalDateTime dataFine;
	
	@Column(name = "motivazione", length = 100)
	private String motivazione;
	
	@Column(name = "stato_richiesta", length = 100)
	private String statoRichiesta;
	
	@Column(name = "note_hr", length = 255)
	private String noteHr;
	
	

	public Richieste() {
		
	}

	public Richieste(String noteHr, Long idRichiesta, String tipoPermesso, LocalDateTime dataRichiesta,
			LocalDateTime dataInizio, LocalDateTime dataFine, String motivazione, String statoRichiesta) {
		super();
		this.idRichiesta = idRichiesta;
		this.tipoPermesso = tipoPermesso;
		this.dataRichiesta = dataRichiesta;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.motivazione = motivazione;
		this.statoRichiesta = statoRichiesta;
		this.noteHr = noteHr;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_utente")
	private Utente utente;
	
	public Long getIdRichiesta() {
		return idRichiesta;
	}
	
	public void setIdRichiesta(Long idRichiesta) {
		this.idRichiesta = idRichiesta;
	}
	
	public Utente getUtente() {
		return utente;
	}
	
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	public String getTipoPermesso() {
		return tipoPermesso;
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
	
	public String getNoteHr() {
		return noteHr;
	}

	public void setNoteHr(String noteHr) {
		this.noteHr = noteHr;
	}

	@Override
	public String toString() {
		return "Richieste [idRichiesta=" + idRichiesta + ", tipoPermesso=" + tipoPermesso
				+ ", dataRichiesta=" + dataRichiesta + ", dataInizio=" + dataInizio + ", dataFine=" + dataFine
				+ ", motivazione=" + motivazione + ", statoRichiesta=" + statoRichiesta + "]";
	}
	
	
	
	
	
}
