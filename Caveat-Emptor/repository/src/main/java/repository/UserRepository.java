package repository;

import entities.User;
import entities.UserRegistration;

public interface UserRepository {
	
    public User getUserByName(String userName);
    
    public long insertNewUser(UserRegistration registration);
    
    public boolean checkExistingUser(String userName);
    public boolean checkExistingEmail(String email);
    

	public void updateUser(User user);
	
	public void deleteRegistration(UserRegistration registration);

	public User getUserById(long id);

	public UserRegistration getRegistrationByUserId(long id);
}

