package ca.ulaval.glo4003.appemployee.domain.user;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface UserRepository {

	void add(User user);

	boolean validateCredentials(String email, String password);

	User findByEmail(String email) throws UserNotFoundException;

	void update(User user);

}
