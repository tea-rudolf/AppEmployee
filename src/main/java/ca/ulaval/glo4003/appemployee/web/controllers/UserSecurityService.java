package ca.ulaval.glo4003.appemployee.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ca.ulaval.glo4003.appemployee.domain.UserRepository;

public class UserSecurityService  implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;
	
	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		return new UserAuthenticationDetails(userRepo.findByUsername(username));
	}
	
}
