package ca.ulaval.glo4003.appemployee.web.controllers.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ca.ulaval.glo4003.appemployee.domain.UserRepository;

public class UserSecurityService implements UserDetailsService {

	UserRepository userRepository;

	public UserSecurityService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		return new UserSecurityDetails(userRepository.findByEmail(username));
	}
}
