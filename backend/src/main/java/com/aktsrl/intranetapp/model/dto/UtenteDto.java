package com.aktsrl.intranetapp.model.dto;

/**
 * DTO dell'entità Utente.
 * Contiene le informazioni principali dell'utente necessarie
 */
public class UtenteDto {
	
	private Long id;
	private String Nome; 
	private String Cognome; 
	private String Email; 
	private String Ruolo;
	private String Password;
	
	
	public UtenteDto() {
		super();
	}


	public UtenteDto(Long id, String nome, String cognome, String email, String ruolo, String password) {
		super();
		this.id = id;
		Nome = nome;
		Cognome = cognome;
		Email = email;
		Ruolo = ruolo;
		Password = password;
	}


	public Long getId() {
		return id;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getNome() {
		return Nome;
	}
	
	
	public void setNome(String nome) {
		Nome = nome;
	}
	
	
	public String getCognome() {
		return Cognome;
	}
	
	
	public void setCognome(String cognome) {
		Cognome = cognome;
	}
	
	
	public String getEmail() {
		return Email;
	}
	
	
	public void setEmail(String email) {
		Email = email;
	}
	
	
	public String getRuolo() {
		return Ruolo;
	}
	
	
	public void setRuolo(String ruolo) {
		Ruolo = ruolo;
	}
	
	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		Password = password;
	}


	@Override
	public String toString() {
		return "utenteDto [id=" + id + ", Nome=" + Nome + ", Cognome=" + Cognome + ", Email=" + Email + ", Ruolo="
				+ Ruolo + "]";
	}
	
	
	
	
}
