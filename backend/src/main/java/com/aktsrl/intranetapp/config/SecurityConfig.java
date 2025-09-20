package com.aktsrl.intranetapp.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.aktsrl.intranetapp.service.UserDetailsServiceImpl;

/**
 * Configurazione di sicurezza per l'applicazione Spring Security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}
	
	@Bean
	 PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}
	
	  @Bean
	     AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	        return authConfig.getAuthenticationManager();
	    }
	  

	  
	    @Bean
	    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration config = new CorsConfiguration();

	        
	        config.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));

	        
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

	        
	        config.setAllowedHeaders(List.of("Content-Type", "Authorization", "X-Requested-With", "Accept"));


	        config.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);
	        return source;
	    }
	  
	  
	  @Bean
	  // TODO sistemare requestmatchers 
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
	    	http
	        .csrf(csrf -> csrf.disable())  
	        .cors(withDefaults())
	        .authorizeHttpRequests(auth -> auth
	        				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	        				.requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
	        				.requestMatchers("/api/salvaRichiesta", "/api/eliminaRichiesta/**", "/api/cercaRichiestePerUtente/**","/api/cerca/stato/*/*").hasRole("DIPENDENTE")
	        				.requestMatchers("/api/mostraListaPrenotazioniAdmin", "/api/eliminaPrenotazioneAdmin/**").hasRole("HR")
	                        .anyRequest().authenticated()
	        )
	        .formLogin().disable() 
	        .logout(logout -> logout
	        		.logoutUrl("/logout")
	        		.logoutSuccessUrl("/login")
	        		.invalidateHttpSession(true)
	        		.deleteCookies("JSESSIONID")
	        		.permitAll()
	        		)
	        .sessionManagement(session -> session 
	        		.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	        		)
	        .userDetailsService(userDetailsServiceImpl); 
	        

	return http.build();
	}
}
