package ca.ulaval.glo4003.appemployee.domain.user;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface UserRepository {

	public void add(User user);

	public boolean validateCredentials(String email, String password);

	public User findByEmail(String email) throws UserNotFoundException;

	public void update(User user);

}