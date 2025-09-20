package com.aktsrl.intranetapp.model.dto;

/**
 * DTO utilizzato per il login degli utenti.
 * Contiene le credenziali necessarie per effetttuare l'accesso di  un utente.
 */
public class LoginDto {
	private String email;
	private String password;


	public LoginDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	
	}
	
	public LoginDto() {
		super();

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
