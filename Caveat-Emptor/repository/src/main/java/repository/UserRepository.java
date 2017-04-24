package repository;

import entities.User;
import entities.UserRegistration;
import utils.UserException;

public interface UserRepository {

	public User getUserByName(String userName) throws UserException;

	public long insertNewUser(UserRegistration registration) throws UserException;

	public boolean checkExistingUser(String userName);

	public boolean checkExistingEmail(String email);

	public void updateUser(User user) throws UserException;

	public void deleteRegistration(UserRegistration registration) throws UserException;

	public User getUserById(long id) throws UserException;

	public UserRegistration getRegistrationByUserId(long id) throws UserException;
}
