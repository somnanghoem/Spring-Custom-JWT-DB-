package com.security.springsecurity.config.secuirty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.springsecurity.config.exception.CustomAccessDeniedHandler;
import com.security.springsecurity.config.exception.JwtAuthenticationEntryPoint;
import com.security.springsecurity.config.filter.JwtTokenFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; 
	
	@Autowired
	public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
		auth.userDetailsService( userDetailsService ).passwordEncoder( passwordEncoder() );
	}
	
	@Bean
	SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
		
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/v1/get-token/**").permitAll().anyRequest().authenticated()
			.and().exceptionHandling()
			.authenticationEntryPoint( jwtAuthenticationEntryPoint )
			//.accessDeniedHandler( accessDeniedHandlerCustom() )
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore( jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public CustomAccessDeniedHandler accessDeniedHandlerCustom() {
		return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
