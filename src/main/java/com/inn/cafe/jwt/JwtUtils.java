package com.inn.cafe.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtils {

	private String secret = "ljhka879akhiusy6d8768s7d8dyfiusdhggfsdidfs786fsdffsd";
	
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	@SuppressWarnings("deprecation")
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(String username, String role) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", role);
		return createToken(claims, username);
	}

	@SuppressWarnings("deprecation")
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
}
