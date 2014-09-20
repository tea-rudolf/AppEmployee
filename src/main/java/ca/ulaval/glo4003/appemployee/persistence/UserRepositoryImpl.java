package ca.ulaval.glo4003.appemployee.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.User;
import ca.ulaval.glo4003.appemployee.domain.UserNotFound;
import ca.ulaval.glo4003.appemployee.domain.UserRepository;

@Repository
@Singleton
public class UserRepositoryImpl implements UserRepository {

	private Map<String, User> credentials = new HashMap<String, User>();

	public UserRepositoryImpl() {
		credentials.put("GracyMb", new User("GracyMb", "toto1", "GracyMb@gmail.com"));
		credentials.put("TeaRud", new User("GracyMb", "toto2", "TeaRud@gmail.com"));
	}

	@Override
	public void add(User user) {
		credentials.put(user.getUsername(), user);
	}

	@Override
	public void remove(User user) {
		credentials.remove(user.getUsername());
	}

	@Override
	public User findByUsername(String username) throws UserNotFound {
		if (!credentials.containsKey(username)) {
			throw new UserNotFound("User does not exist in repository");
		}

		return credentials.get(username);
	}

	@Override
	public void update(User user) {
		credentials.put(user.getUsername(), user);
	}

}
