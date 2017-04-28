package services.register.impl;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import entities.User;
import entities.UserRegistration;
import model.RegistrationDto;
import model.UserDto;
import repository.UserRepository;
import services.register.RegisterService;
import services.register.email.ConfirmationEmail;
import services.util.Utils;
import utils.exceptions.UserException;
import utils.UserStateEnum;

@Stateless
@Remote(RegisterService.class)
public class RegisterServiceImpl implements RegisterService {

	@EJB
	UserRepository user;

	@Override
	public UserDto insertNewUser(RegistrationDto registration) throws UserException {

		UserRegistration userRegistration = Utils.getRegistrationFromDto(registration);
		long userId = user.insertNewUser(userRegistration);
		User newUser = user.getUserById(userId);

		return Utils.getUserFromEntity(newUser);
	}

	@Override
	public boolean checkExistingUser(String userName) {

		try {
			return user.checkExistingUser(userName);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	@Override
	public boolean checkExistingEmail(String email) {

		try {
			return user.checkExistingEmail(email);
		} catch (UserException e) {
			return false;
		}
	}

	@Override
	public boolean enableUser(Long userId, String code) throws UserException {

		RegistrationDto registration = getRegistrationById(userId);

		if ((registration.getUser().getId() == userId) && (registration.getValidationCode().equals(code))) {

			changeUserState(registration);
			removRegistration(registration);

			return true;
		}

		return false;

	}

	private void removRegistration(RegistrationDto registration) throws UserException {
		UserRegistration userReg = Utils.getRegistrationFromDto(registration);
		userReg.setId(registration.getId());

		user.deleteRegistration(userReg);
	}

	private void changeUserState(RegistrationDto registration) throws UserException {

		UserDto userDto = registration.getUser();
		userDto.setState(UserStateEnum.ENABLED.getState());
		User userEntity = (Utils.getUserFromDto(userDto));
		user.updateUser(userEntity);
	}

	public RegistrationDto getRegistrationById(long userId) throws UserException {

		RegistrationDto registrationDto = new RegistrationDto();
		UserRegistration registration = user.getRegistrationByUserId(userId);
		registrationDto = Utils.getRegistrationFromEntity(registration);

		return registrationDto;

	}

	@Override
	public void createNewUser(RegistrationDto registration) throws UserException {

		registration.setUser(insertNewUser(registration));
		ConfirmationEmail.sendEmail(registration);

	}

}
