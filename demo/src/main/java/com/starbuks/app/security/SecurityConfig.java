package com.starbuks.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Autowired
	private CustomLoginSuccessHandler customLoginSuccessHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				/*
				 * .authorizeHttpRequests(auth -> auth .requestMatchers("/login", "/registro",
				 * "/css/**", "/js/**", "/images/**").permitAll()
				 * .requestMatchers("/cliente/inicio", "/cliente/productos").permitAll()
				 * .requestMatchers("/carrito/pagar").authenticated() // 🔐 Aquí se exige login
				 * .requestMatchers("/cliente/miscompras").authenticated() // Solo usuarios
				 * logueados .requestMatchers("/admin/**").hasRole("ADMIN")
				 * .anyRequest().authenticated() )
				 */
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/login", "/registro", "/css/**", "/js/**", "/images/**")
								.permitAll().requestMatchers("/cliente/inicio", "/cliente/productos").permitAll()

								// Rutas solo para usuarios autenticados
								.requestMatchers("/carrito/pagar").authenticated()
								.requestMatchers("/cliente/miscompras").authenticated()

								// Rutas exclusivas para ADMIN
								.requestMatchers("/dashboard/**", "/productos/**", "/ventas/**", "/usuarios/**",
										"/reportes/**", "/admin/**")
								.hasRole("ADMIN").anyRequest().authenticated())

				.formLogin(form -> form.loginPage("/login")
		                .successHandler(customLoginSuccessHandler)
						.permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true).deleteCookies("JSESSIONID").clearAuthentication(true).permitAll())
				.exceptionHandling(exception -> exception.accessDeniedPage("/403")).headers(headers -> headers
		                .frameOptions(frame -> frame.sameOrigin())//para que permita visualizar los reportes con el iframe
			            );
		;
		

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

}
