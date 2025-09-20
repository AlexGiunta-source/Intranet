package com.aktsrl.intranetapp.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aktsrl.intranetapp.entity.Richieste;
import com.aktsrl.intranetapp.entity.Utente;



public interface RichiesteRepository extends JpaRepository<Richieste,Long > {
List <Richieste> findRichiesteByUtente(Utente utente);

List <Richieste> findByStatoRichiestaAndUtente_IdUtenteOrderByDataRichiestaAsc(String statoRichiesta, Long idUtente);
List <Richieste> findByDataRichiesta(LocalDateTime dataRichiesta);
@Query(value = "SELECT * FROM richieste "
		+ "WHERE (:statoRichiesta is null or stato_richiesta = :statoRichiesta) "
		+ "and (:dataRichiestaInizio is null or data_richiesta between :dataRichiestaInizio and :dataRichiestaFine) "
		+ "and (:idUtente is null or id_utente = :idUtente)" ,nativeQuery=true)
List <Richieste> getRichiesteFiltrate(@Param("statoRichiesta")String statoRichiesta ,@Param("dataRichiestaInizio")LocalDateTime dataRichiestaInizio,@Param("dataRichiestaFine")LocalDateTime dataRichiestaFine, @Param("idUtente")Long idUtente);


}
