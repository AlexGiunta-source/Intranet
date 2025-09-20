package com.aktsrl.intranetapp.mapper;

import org.springframework.stereotype.Component;

import com.aktsrl.intranetapp.entity.Utente;
import com.aktsrl.intranetapp.model.dto.UtenteDto;

/**
 * Mapper per convertire tra l'entità Utente  e il DTO UtenteDto e viceversa.
 */
@Component
public class UtenteMapper {

	public UtenteDto toDto(Utente utente) {
		
		UtenteDto dto = new UtenteDto();
		
		dto.setId(utente.getId_utente());
		dto.setNome(utente.getNome());
		dto.setCognome(utente.getCognome());
		dto.setEmail(utente.getEmail());
		dto.setRuolo(utente.getRuolo());
		
		return dto;
		
		
	}
	
	public Utente toEntity(UtenteDto utenteDto) {
		Utente utente = new Utente(); 
		utente.setEmail(utenteDto.getEmail());
		utente.setNome(utenteDto.getNome());
		utente.setCognome(utenteDto.getCognome());
		utente.setPassword(utenteDto.getPassword());
		return utente;
	}
}
