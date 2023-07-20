package com.security.springsecurity.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config.properties")
public class PropertiesPlaceholderConfiguration {
	
	@Value("${jwt.secret}")
    private String secret;
	@Value("${jwt.token.validity}")
	private String JWT_TOKEN_VALIDITY;

	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getJWT_TOKEN_VALIDITY() {
		return JWT_TOKEN_VALIDITY;
	}
	public void setJWT_TOKEN_VALIDITY(String jWT_TOKEN_VALIDITY) {
		JWT_TOKEN_VALIDITY = jWT_TOKEN_VALIDITY;
	}
	
}
