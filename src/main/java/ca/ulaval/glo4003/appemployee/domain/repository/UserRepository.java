package ca.ulaval.glo4003.appemployee.domain.repository;

import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.user.User;

@Repository
@Singleton
public interface UserRepository {

	void store(User user) throws Exception;

	User findByEmail(String email);

	List<User> findByEmails(List<String> emails);

	Collection<User> findAll();
}
