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
import services.util.EntityDtoMapper;
import utils.exceptions.UserException;
import utils.UserStateEnum;

@Stateless
@Remote(RegisterService.class)
public class RegisterServiceImpl implements RegisterService {

	@EJB
	UserRepository repository;

	@Override
	public UserDto insertNewUser(RegistrationDto registration) throws UserException {

		UserRegistration userRegistration = EntityDtoMapper.getRegistrationFromDto(registration);
		long userId = repository.insertNewUser(userRegistration);
		User newUser = repository.getUserById(userId);

		return EntityDtoMapper.getUserFromEntity(newUser);
	}

	@Override
	public boolean checkExistingUser(String userName) {

		try {
			return repository.checkExistingUser(userName);
		} catch (UserException e) {
			return false;
		}
	}

	@Override
	public boolean checkExistingEmail(String email) {

		try {
			return repository.checkExistingEmail(email);
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
		UserRegistration userReg = EntityDtoMapper.getRegistrationFromDto(registration);
		userReg.setId(registration.getId());

		repository.deleteRegistration(userReg);
	}

	private void changeUserState(RegistrationDto registration) throws UserException {

		UserDto userDto = registration.getUser();
		userDto.setState(UserStateEnum.ENABLED.getState());
		User userEntity = (EntityDtoMapper.getUserFromDto(userDto));
		repository.updateUser(userEntity);
	}

	public RegistrationDto getRegistrationById(long userId) throws UserException {

		RegistrationDto registrationDto = new RegistrationDto();
		UserRegistration registration = repository.getRegistrationByUserId(userId);
		registrationDto = EntityDtoMapper.getRegistrationFromEntity(registration);

		return registrationDto;

	}

	@Override
	public void createNewUser(RegistrationDto registration) throws UserException {

		registration.setUser(insertNewUser(registration));
		ConfirmationEmail.sendEmail(registration);

	}

}
