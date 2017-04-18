package repository;

import model.UserDto;

public interface UserRepository {
	
    public UserDto getUserByName(String userName);
}

