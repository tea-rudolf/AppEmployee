package ca.ulaval.glo4003.appemployee.domain.repository;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;

@Repository
@Singleton
public interface UserRepository {

	void store(User user) throws Exception;

	User findByEmail(String email) throws UserNotFoundException;
}
