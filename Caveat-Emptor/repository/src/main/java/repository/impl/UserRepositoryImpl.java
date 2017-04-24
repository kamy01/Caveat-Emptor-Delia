package repository.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;
import entities.UserRegistration;
import repository.UserRepository;
import utils.Constants;
import utils.UserStateEnum;

@Stateless
@Remote(UserRepository.class)
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext(unitName = "myapp")
	EntityManager em;

	public User getUserByName(String username) {

		User user = new User();
		try {

			Query query = em.createNamedQuery(Constants.FIND_USER_BY_USERNAME);

			user = (User) query.setParameter("name", username).getSingleResult();
		} catch (Exception e) {
			System.out.println("Error in UserRepository " + e);
			return user;
		}

		return user;
	}

	@Override
	public boolean checkExistingEmail(String email) {

		Query query = em.createNamedQuery(Constants.FIND_USER_BY_EMAIL);

		if (query.setParameter("name", email).getResultList().size() > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean checkExistingUser(String userName) {

		Query query = em.createNamedQuery(Constants.FIND_USER_BY_USERNAME);

		if (query.setParameter("name", userName).getResultList().size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public long insertNewUser(UserRegistration registration) {

		try {
			em.persist(registration);
			em.flush();
			return registration.getUser().getId();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public void updateUser(User user) {
		
		
		try {
			em.merge(user);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public User getUserById(long id) {

		User user = new User();
		try {

			Query query = em.createNamedQuery(Constants.FIND_USER_BY_ID);

			user = (User) query.setParameter("name", id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return user;
		}

		return user;
	}

	@Override
	public UserRegistration getRegistrationByUserId(long id) {

		UserRegistration registration = new UserRegistration();
		try {

			Query query = em.createNamedQuery(Constants.FIND_REGISTRATION_BY_USERID);

			registration = (UserRegistration) query.setParameter("name", id).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			registration.setId((long) -1);
		}

		return registration;
	}

	@Override
	public void deleteRegistration(UserRegistration registration) {
		try {
			em.remove(em.contains(registration) ? registration : em.merge(registration));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
