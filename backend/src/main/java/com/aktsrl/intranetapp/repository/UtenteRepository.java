package com.aktsrl.intranetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aktsrl.intranetapp.entity.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Integer>{

	Optional <Utente> findByEmail(String email);
	Optional <Utente> findByIdUtente(Long id);
}
