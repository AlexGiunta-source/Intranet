package com.aktsrl.intranetapp.model.dto;

/**
 * DTO che rappresenta i dati di sintesi
 * delle richieste per la dashboard HR.
 * Contiene il numero totale di richieste e la suddivisione per stato
 * e tipo di permesso.
 */
public class DashBoardDto {
	
	private Integer numeroTotaleRichieste;
	private Integer numeroApprovate;
	private Integer numeroInAttesa;
	private Integer numeroRifiutate; 
	private Integer malattia; 
	private Integer ferie; 
	private Integer permesso;
	
	public DashBoardDto() {
		super();
		
	}

	public DashBoardDto(Integer numeroTotaleRichieste, Integer numeroApprovate, Integer numeroInAttesa,
			Integer numeroRifiutate, Integer malattia, Integer ferie, Integer permesso) {
		super();
		this.numeroTotaleRichieste = numeroTotaleRichieste;
		this.numeroApprovate = numeroApprovate;
		this.numeroInAttesa = numeroInAttesa;
		this.numeroRifiutate = numeroRifiutate;
		this.malattia = malattia;
		this.ferie = ferie;
		this.permesso = permesso;
	}

	public Integer getNumeroTotaleRichieste() {
		return numeroTotaleRichieste;
	}

	public void setNumeroTotaleRichieste(Integer numeroTotaleRichieste) {
		this.numeroTotaleRichieste = numeroTotaleRichieste;
	}

	public Integer getNumeroApprovate() {
		return numeroApprovate;
	}

	public void setNumeroApprovate(Integer numeroApprovate) {
		this.numeroApprovate = numeroApprovate;
	}

	public Integer getNumeroInAttesa() {
		return numeroInAttesa;
	}

	public void setNumeroInAttesa(Integer numeroInAttesa) {
		this.numeroInAttesa = numeroInAttesa;
	}

	public Integer getNumeroRifiutate() {
		return numeroRifiutate;
	}

	public void setNumeroRifiutate(Integer numeroRifiutate) {
		this.numeroRifiutate = numeroRifiutate;
	}

	public Integer getMalattia() {
		return malattia;
	}

	public void setMalattia(Integer malattia) {
		this.malattia = malattia;
	}

	public Integer getFerie() {
		return ferie;
	}

	public void setFerie(Integer ferie) {
		this.ferie = ferie;
	}

	public Integer getPermesso() {
		return permesso;
	}

	public void setPermesso(Integer permesso) {
		this.permesso = permesso;
	} 
	
}
