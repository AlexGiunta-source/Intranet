package com.aktsrl.intranetapp.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 * DTO  utilizzato per la registrazione di un nuovo utente.
 * Contiene i dati obbligatori per creare un nuovo utente nel sistema.
 */
public class NuovoUtenteDto { 

	 @NotEmpty(message="nome non valido")
	private String nome; 
    @NotEmpty(message="cognome non valido")
	private String cognome; 
	@Email(message="email non valida")
	private String email; 
	@NotEmpty(message="password non valida")
	@Size(message="formato password non valido" , min=1 , max= 10)
	private String password;
	
	public NuovoUtenteDto(String nome, String cognome, String email, String password) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
	}

	public NuovoUtenteDto() {
		super();
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
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
