package br.com.alura.forum.security.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import br.com.alura.forum.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenManager {

	private String secretKey = "alura.forum.jwt.secret";
	
	@Value("${alura.forum.jwt.expiration}")
	private long expirationInMillis;
	
	public String generateToken(Authentication authentication) {
		
		User user = (User) authentication.getPrincipal();
		Date now = new Date();
		Date expirationTime = new Date(now.getTime() + expirationInMillis);
		
		return Jwts.builder()
			.setIssuer("Alura Forum API")
			.setSubject(Long.toString(user.getId()))
			.setIssuedAt(now)
			.setExpiration(expirationTime)
			.signWith(SignatureAlgorithm.HS256, this.secretKey)
			.compact();
	}
	
	public Long getUserIdFromToken(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(this.secretKey)
				.parseClaimsJws(jwt).getBody();
		return Long.parseLong(claims.getSubject());
	}

	public boolean isValid(String jwt) {
		try {
			Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(jwt);
			return true;
		} catch(JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
