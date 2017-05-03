package repository.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import entities.User;
import entities.UserRegistration;
import repository.UserRepository;
import utils.exceptions.UserException;

@Stateless
@Remote(UserRepository.class)
public class UserRepositoryImpl implements UserRepository {

	private final static String GET_USER_ERROR = " inside getUser method with parameter = ";
	private final static String CHECK_USER_ERROR = " inside checkUser method with parameter = ";
	private final static String INSERT_USER_ERROR = " inside insertNewUser() with parameter = ";
	private final static String UPDATE_USER_ERROR = " inside updateUser() with parameter = ";
	private final static String GET_REGISTRATION_ERROR = " inside getRegistration method with parameter = ";
	private final static String DELETE_REGISTRATION_ERROR = " inside deleteRegistration method with parameter = ";

	@PersistenceContext(unitName = "myapp")
	EntityManager em;

	public User getUserByName(String username) throws UserException {

		User user = new User();
		try {
			Query query = em.createNamedQuery(User.FIND_USER_BY_USERNAME);
			user = (User) query.setParameter("name", username).getSingleResult();
			return user;

		} catch (IllegalArgumentException e) {
			throw new UserException(e + GET_USER_ERROR + username);
		} catch (Exception e) {
			throw new UserException(e + GET_USER_ERROR + username);
		}
	}

	@Override
	public boolean checkExistingEmail(String email) throws UserException {

		try {
			Query query = em.createNamedQuery(User.FIND_USER_BY_EMAIL);

			if (!query.setParameter("name", email).getResultList().isEmpty()) {
				return true;
			} else {
				return false;
			}
		} catch (IllegalArgumentException e) {
			throw new UserException(e + CHECK_USER_ERROR + email);
		} catch (Exception e) {
			throw new UserException(e + CHECK_USER_ERROR + email);
		}
	}

	@Override
	public boolean checkExistingUser(String userName) throws UserException {

		try {
			Query query = em.createNamedQuery(User.FIND_USER_BY_USERNAME);
			if (!query.setParameter("name", userName).getResultList().isEmpty()) {
				return true;
			} else {
				return false;
			}
		} catch (IllegalArgumentException e) {
			throw new UserException(e + CHECK_USER_ERROR + userName);
		} catch (Exception e) {
			throw new UserException(e + CHECK_USER_ERROR + userName);
		}
	}

	@Override
	public long insertNewUser(UserRegistration registration) throws UserException {

		try {
			em.persist(registration);
			em.flush();
			return registration.getUser().getId();

		} catch (IllegalArgumentException e) {
			throw new UserException(e + INSERT_USER_ERROR + registration.toString());
		} catch (EntityExistsException e) {
			throw new UserException(e + INSERT_USER_ERROR + registration.toString());
		} catch (TransactionRequiredException e) {
			throw new UserException(e + INSERT_USER_ERROR + registration.toString());
		} catch (Exception e) {
			throw new UserException(e + INSERT_USER_ERROR + registration.toString());
		}
	}

	@Override
	public void updateUser(User user) throws UserException {

		try {
			em.merge(user);

		} catch (IllegalArgumentException e) {
			throw new UserException(e + UPDATE_USER_ERROR + user.toString());
		} catch (TransactionRequiredException e) {
			throw new UserException(e + UPDATE_USER_ERROR + user.toString());
		} catch (Exception e) {
			throw new UserException(e + UPDATE_USER_ERROR + user.toString());
		}

	}

	@Override
	public User getUserById(long id) throws UserException {

		User user = new User();

		try {
			Query query = em.createNamedQuery(User.FIND_USER_BY_ID);
			user = (User) query.setParameter("name", id).getSingleResult();
			return user;

		} catch (IllegalArgumentException e) {
			throw new UserException(e + GET_USER_ERROR + id);
		} catch (Exception e) {
			throw new UserException(e + GET_USER_ERROR + id);
		}

	}

	@Override
	public UserRegistration getRegistrationByUserId(long id) throws UserException {

		UserRegistration registration = new UserRegistration();

		try {
			Query query = em.createNamedQuery(UserRegistration.FIND_REGISTRATION_BY_USERID);
			registration = (UserRegistration) query.setParameter("name", id).getSingleResult();
			return registration;

		} catch (IllegalArgumentException e) {
			throw new UserException(e + GET_REGISTRATION_ERROR + id);
		} catch (Exception e) {
			throw new UserException(e + GET_REGISTRATION_ERROR + id);
		}
	}

	@Override
	public void deleteRegistration(UserRegistration registration) throws UserException {

		try {
			em.remove(em.contains(registration) ? registration : em.merge(registration));

		} catch (IllegalArgumentException e) {
			throw new UserException(e + DELETE_REGISTRATION_ERROR + registration.toString());
		} catch (TransactionRequiredException e) {
			throw new UserException(e + DELETE_REGISTRATION_ERROR + registration.toString());
		} catch (Exception e) {
			throw new UserException(e + DELETE_REGISTRATION_ERROR + registration.toString());
		}
	}
}
