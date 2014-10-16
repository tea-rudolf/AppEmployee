package ca.ulaval.glo4003.appemployee.domain.user;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

@Repository
@Singleton
public interface UserRepository {

	void store(User user) throws Exception;

	User findByEmail(String email) throws UserNotFoundException;
}
