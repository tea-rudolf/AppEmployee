package ca.ulaval.glo4003.appemployee.domain;


public interface UserRepository {

	public void add(User user);

	public void remove(User user);

	public User findByUsername(String username);

	public void update(User user);

}