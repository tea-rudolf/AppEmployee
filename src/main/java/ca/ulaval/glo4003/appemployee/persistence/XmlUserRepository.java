package ca.ulaval.glo4003.appemployee.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.appemployee.domain.user.User;
import ca.ulaval.glo4003.appemployee.domain.user.UserAlreadyExistsException;
import ca.ulaval.glo4003.appemployee.domain.user.UserNotFoundException;
import ca.ulaval.glo4003.appemployee.domain.user.UserRepository;

@Repository
@Singleton
public class XmlUserRepository implements UserRepository {

	XmlRepositoryMarshaller xmlRepositoryMarshaller = XmlRepositoryMarshaller.getInstance();
	private List<User> users;

	public XmlUserRepository() {
		users = new ArrayList<User>();
		unmarshall();

		add(new User("test@test.com", "1234"));
	}

	@Override
	public void add(User user) {
		User userFound = findUser(user.getEmail());

		if (userFound != null) {
			throw new UserAlreadyExistsException("User " + user.getEmail() + " already exists.");
		}
		users.add(user);
		marshall();
	}

	@Override
	public boolean validateCredentials(String email, String password) {
		User user = findUser(email);

		if (user == null) {
			return false;
		}
		return user.validatePassword(password);
	}

	@Override
	public User findByEmail(String email) {
		User user = findUser(email);

		if (user != null) {
			return user;
		}
		throw new UserNotFoundException("User " + email + " not found.");
	}

	@Override
	public void update(User user) {
		User userFound = findUser(user.getEmail());

		if (user != null) {
			int index = users.indexOf(userFound);
			users.set(index, user);
		}

		marshall();
	}

	private void marshall() {
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		xmlRootNode.setUsers(users);
		xmlRepositoryMarshaller.Marshall();
	}

	private void unmarshall() {
		xmlRepositoryMarshaller.Unmarshall();
		XmlRootNode xmlRootNode = xmlRepositoryMarshaller.getRootNode();
		users = xmlRootNode.getUsers();
	}

	private User findUser(String email) {
		unmarshall();
		User userFound = null;

		for (User user : users) {
			if (user.getEmail().equals(email)) {
				userFound = user;
			}
		}
		return userFound;
	}

}
