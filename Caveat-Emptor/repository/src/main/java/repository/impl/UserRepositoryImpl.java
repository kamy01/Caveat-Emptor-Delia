package repository.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.User;
import model.UserDto;
import repository.UserRepository;

@Stateless
@Remote(UserRepository.class)
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext(unitName = "myapp")
	EntityManager em;

	public UserDto getUserByName(String username) {
		
		UserDto u = new UserDto();
		
		try {
			User user = (User) em.createNamedQuery("User.findByUsername").setParameter("name", username).getSingleResult();
	
			u = getUserFromEntity(user);
		} 
		catch(Exception e) {
			System.out.println("Error in UserRepository " + e);
		}
		
		return u;
	}

	public UserDto getUserFromEntity(User entity) {
		return new UserDto(entity.getUserId(), entity.getUsername(), entity.getPassword(), entity.getFirstName(),
				entity.getLastName(), entity.getEmailAdress());
	}

}
