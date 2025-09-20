package com.aktsrl.intranetapp.model.dto;

import java.time.LocalDate;

/**
 * DTO utilizzato per filtrare le richieste
 */
public class FiltriDto {

	private Long idUtente; 
	private LocalDate dataRichiesta; 
	private String statoRichiesta;
	
	
	public FiltriDto(Long idUtente, LocalDate dataRichiesta, String statoRichiesta) {
		super();
		this.idUtente = idUtente;
		this.dataRichiesta = dataRichiesta;
		this.statoRichiesta = statoRichiesta;
	}


	public FiltriDto() {
		super();
		
	}


	public Long getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}


	public LocalDate getDataRichiesta() {
		return dataRichiesta;
	}


	public void setDataRichiesta(LocalDate dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}


	public String getStatoRichiesta() {
		return statoRichiesta;
	}


	public void setStatoRichiesta(String statoRichiesta) {
		this.statoRichiesta = statoRichiesta;
	} 
	
	
}
