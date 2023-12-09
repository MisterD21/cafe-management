package com.inn.cafe.jwt;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.inn.cafe.entities.User;
import com.inn.cafe.repositories.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	private User userDetails;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		userDetails = userRepository.findByEmailId(username);
		if(!Objects.isNull(userDetails)) {
			return new org.springframework.security.core.userdetails.User(userDetails.getEmail(),
					userDetails.getPassword(), new ArrayList<>());
		}else {
			throw new UsernameNotFoundException("user not found");
		}
	}
	
	public User getUserDetails() {
		return userDetails;
	}
}
