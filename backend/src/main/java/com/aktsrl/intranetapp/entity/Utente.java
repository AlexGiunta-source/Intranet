package com.aktsrl.intranetapp.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity che rappresenta un utente del sistema.
 * 
 * Contiene informazioni personali, credenziali e ruolo dell'utente.
 * Ha una relazione OneToMany con {@link Richieste}, indicando
 * le richieste associate all'utente.
 */
@Entity
@Table(name = "Utente")

public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUtente;
	
	@Column(name = "Nome", length = 100)
	private String nome;
	
	@Column(name = "Cognome", length = 100)
	private String cognome;
	
	@Column(name = "Email", length = 100)
	private String email;
	
	@Column(name = "password" ,length = 100)
	private String password;
	
	@Column(name = "Ruolo", length = 100)
	private String ruolo;
	
	public Utente() {
		
	}
	
	public Utente(Long idUtente, String nome, String cognome, String email, String pdw, String ruolo) {
		super();
		this.idUtente = idUtente;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = pdw;
		this.ruolo = ruolo;
	}

	@OneToMany(mappedBy = "utente", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set <Richieste> richieste = new HashSet<>();




	public Long getId_utente() {
		return idUtente;
	}
	
	public void setId_utente(Long id_utente) {
		this.idUtente = id_utente;
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
	
	public void setPassword(String pdw) {
		this.password = pdw;
	}
	
	public String getRuolo() {
		return ruolo;
	}
	
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	@Override
	public String toString() {
		return "Utente [idUtente=" + idUtente + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email
				+ ", pdw=" + password + ", ruolo=" + ruolo + "]";
	}
	
}
