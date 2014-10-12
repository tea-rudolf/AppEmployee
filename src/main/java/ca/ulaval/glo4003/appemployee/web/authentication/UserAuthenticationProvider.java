package ca.ulaval.glo4003.appemployee.web.authentication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;

public class UserAuthenticationProvider implements AuthenticationProvider {

	private UserRepository repository;

	@Inject
	public UserAuthenticationProvider(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();

		try {
			System.out.println("contenu: " + email);
			User user = repository.findByEmail(email);
			return validateUserPassword(user, password);
		} catch (UserNotFoundException e) {
			return null; // Return null means login failed.
		}
	}

	private Authentication validateUserPassword(User user, String password) {
		if (user.isPasswordValid(password)) {
			List<GrantedAuthority> roles = new ArrayList<>();
			roles.add(new SimpleGrantedAuthority(user.getRoleName()));
			return new UsernamePasswordAuthenticationToken(user, password, roles);
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authenticationClass) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationClass));
	}

}
