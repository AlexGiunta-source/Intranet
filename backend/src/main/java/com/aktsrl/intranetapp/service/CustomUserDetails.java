package com.aktsrl.intranetapp.service;

import com.aktsrl.intranetapp.entity.Utente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementazione personalizzata di UserDetails per Spring Security.
 */
public class CustomUserDetails implements UserDetails {

    private final Utente utente;

    public CustomUserDetails(Utente utente) {
        this.utente = utente;
    }

    public Utente getUtente() {
        return utente;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + utente.getRuolo()));
    }

    @Override
    public String getPassword() {
        return utente.getPassword();
    }

    @Override
    public String getUsername() {
        return utente.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNome() {
        return utente.getNome();
    }

    public String getCognome() {
        return utente.getCognome();
    }

    public String getRuolo() {
        return utente.getRuolo();
    }
    
    public String getEmail() {
    	return utente.getEmail();
     }
}