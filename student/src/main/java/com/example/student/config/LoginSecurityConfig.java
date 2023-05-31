package com.example.student.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class LoginSecurityConfig {

	@Bean
	protected SecurityFilterChain configure(final HttpSecurity http) throws Exception {

		try {
			http
					.csrf().disable()
					.formLogin((form) -> form
							.loginPage("/")
							.permitAll())
					.authorizeHttpRequests()
					.requestMatchers("/login/**").permitAll()
					.requestMatchers("/logout/**").permitAll()
					.requestMatchers("/api/mail/send").permitAll()
					.requestMatchers("/register/**").permitAll()
					.requestMatchers("/dashboard/**").permitAll()
					.requestMatchers("/users/**").permitAll()
					.requestMatchers("/users/admins").permitAll()
					.requestMatchers("/delete-student/**").permitAll()
					.requestMatchers("/delete-admin/**").permitAll()
					.requestMatchers("/edit-student/**").permitAll()
					.requestMatchers("/edit-admin/**").permitAll()
					.requestMatchers("/add**").permitAll()
					.requestMatchers("/categories/**").permitAll()
					.requestMatchers("/delete-category/**").permitAll()
					.requestMatchers("/edit-category/**").permitAll()
					.requestMatchers("/all-skills/**").permitAll()
					.requestMatchers("/edit-skill/**").permitAll()
					.requestMatchers("/delete-skill/**").permitAll()
					.requestMatchers("/reset-password/**").permitAll()
					.requestMatchers("/confirm-reset/**").permitAll()
					.requestMatchers("/invalid-link/**").permitAll()
					.requestMatchers("/my-skills/**").permitAll()
					.requestMatchers("/delete-evaluation/**").permitAll()
					.requestMatchers("/reevaluate/**").permitAll()
					.requestMatchers("/favicon.ico").permitAll()
					.requestMatchers("/static/**").permitAll()
					.and()
					.logout()
					.invalidateHttpSession(true)
					.logoutSuccessUrl("/login").permitAll();

		} catch (Exception e) {
			log.error("Security configuration failed", e);
		}
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.invalidSessionUrl("/invalidSession.html")
				.sessionFixation().migrateSession();

		return http.build();
	}

	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}

}
