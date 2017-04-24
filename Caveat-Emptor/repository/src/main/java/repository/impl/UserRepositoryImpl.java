package repository.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;
import entities.UserRegistration;
import repository.UserRepository;
import utils.Constants;
import utils.UserException;

@Stateless
@Remote(UserRepository.class)
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext(unitName = "myapp")
	EntityManager em;

	public User getUserByName(String username) throws UserException {

		User user = new User();
		try {

			Query query = em.createNamedQuery(Constants.FIND_USER_BY_USERNAME);

			user = (User) query.setParameter("name", username).getSingleResult();
		} catch (Exception e) {
			throw new UserException("Error in UserRepository " + e);
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
	public long insertNewUser(UserRegistration registration) throws UserException {

		try {
			em.persist(registration);
			em.flush();
			return registration.getUser().getId();
		} catch (Exception e) {
			throw new UserException("Error while trying to insert new user." + e);
		}
	}

	@Override
	public void updateUser(User user) throws UserException {

		try {
			em.merge(user);

		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Error while trying to update a user." + e);
		}

	}

	@Override
	public User getUserById(long id) throws UserException {

		User user = new User();
		try {

			Query query = em.createNamedQuery(Constants.FIND_USER_BY_ID);

			user = (User) query.setParameter("name", id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Error while trying to find a user by an id " + e);
		}

		return user;
	}

	@Override
	public UserRegistration getRegistrationByUserId(long id) throws UserException {

		UserRegistration registration = new UserRegistration();
		try {

			Query query = em.createNamedQuery(Constants.FIND_REGISTRATION_BY_USERID);

			registration = (UserRegistration) query.setParameter("name", id).getSingleResult();

		} catch (Exception e) {
			throw new UserException("Error while trying to find a registration by an user id: " + e);
		}

		return registration;
	}

	@Override
	public void deleteRegistration(UserRegistration registration) throws UserException {
		try {
			em.remove(em.contains(registration) ? registration : em.merge(registration));
		} catch (Exception e) {
			throw new UserException("Error while trying to delete a registration: " + e);
		}
	}

}
