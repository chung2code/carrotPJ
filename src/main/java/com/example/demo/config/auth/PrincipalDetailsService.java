package com.example.demo.config.auth;

import com.example.demo.domain.user.dto.UserDto;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user =  userRepository.findById(username);

		if(user.isEmpty())
			return null;

		UserDto dto = new UserDto();
		dto.setId(user.get().getId());
		dto.setPassword(user.get().getPassword());
		dto.setRole(user.get().getRole());

		PrincipalDetails principalDetails = new PrincipalDetails();
		principalDetails.setUser(dto);

		return principalDetails;

	}


}
