package com.inn.cafe.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	private String username = null;
	
	private Claims claims = null;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().matches("/user/login|/user/signup|/user/forgetpassword")) {
			filterChain.doFilter(request, response);
		}else {
			String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			String jwtToken = null;
			
			if(requestTokenHeader!=null && !requestTokenHeader.isEmpty() && requestTokenHeader.startsWith("Bearer")) {
				jwtToken = requestTokenHeader.split(" ")[1].trim();
				username = jwtUtils.extractUsername(jwtToken);
				claims = jwtUtils.extractAllClaims(jwtToken);
			}
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
				if(jwtUtils.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new 
							UsernamePasswordAuthenticationToken(userDetails, null, 
									userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}
	
	public boolean isAdmin() {
		return "admin".equalsIgnoreCase((String) claims.get("role"));
	}
	
	public boolean isUser() {
		return "user".equalsIgnoreCase((String) claims.get("role"));
	}
	
	public String getCurrentUser() {
		return username;
	}

}
