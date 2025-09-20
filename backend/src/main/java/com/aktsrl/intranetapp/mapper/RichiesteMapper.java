package com.aktsrl.intranetapp.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.aktsrl.intranetapp.entity.Richieste;
import com.aktsrl.intranetapp.model.dto.RichiesteDto;
/**
 * Mapper per convertire tra l'entità Richieste  e il DTO RichiesteDto e viceversa.
 */
@Component 
public class RichiesteMapper {

	public  RichiesteDto toDto(Richieste richieste) {
		RichiesteDto dto = new RichiesteDto();
		dto.setEmailUtente(richieste.getUtente().getEmail());
		dto.setIdUtente(richieste.getUtente().getId_utente());
		dto.setTipoPermesso(richieste.getTipoPermesso());
		dto.setDataRichiesta(richieste.getDataRichiesta());
		dto.setDataInizio(richieste.getDataInizio());
		dto.setDataFine(richieste.getDataFine());
		dto.setMotivazione(richieste.getMotivazione());
		dto.setStatoRichiesta(richieste.getStatoRichiesta());
		dto.setIdRichiesta(richieste.getIdRichiesta());
		dto.setNoteHr(richieste.getNoteHr());
		return dto;
		
		
	}
	
	public Richieste toEntity(RichiesteDto richiesteDto) {
		Richieste richiesta = new Richieste();
		richiesta.setTipoPermesso(richiesteDto.getTipoPermesso());
		richiesta.setDataInizio(richiesteDto.getDataInizio());
		richiesta.setDataFine(richiesteDto.getDataFine());
		richiesta.setMotivazione(richiesteDto.getMotivazione());
		richiesta.setDataRichiesta(LocalDateTime.now());
		richiesta.setStatoRichiesta("in attesa");
		return richiesta;
	}
}
