package com.biddingapp.beans;

import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.AddressDto;
import model.RegistrationDto;
import model.UserDto;
import services.register.RegisterService;
import utils.ConstantsEnum;
import utils.exceptions.*;
import utils.UserStateEnum;

@ManagedBean(name = "userRegister")
@ViewScoped
public class UserRegistrationBean {

	private UserDto user;
	private AddressDto address;
	private RegistrationDto registration;
	private boolean usernameValid;
	private boolean emailValid;
	
	
	@EJB
	RegisterService registerService;

	@PostConstruct
	public void init() {

		user = new UserDto();
		address = new AddressDto();
		registration = new RegistrationDto();
		usernameValid = true;
		emailValid = true;
	}

	public String register() {

		FacesMessage message = null;

		user.setState(UserStateEnum.PENDING.getState());
		user.setAddress(address);

		registration.setUser(user);
		registration.setExpirationDate(new Date());
		registration.setValidationCode(UUID.randomUUID().toString());

		if (usernameValid && emailValid) {

			try {
				registerService.createNewUser(registration);
			} catch (UserException e) {
				e.printStackTrace();
			}

			message = new FacesMessage(FacesMessage.SEVERITY_INFO, ConstantsEnum.CORRECT_REGISTER.getConstant(), user.getUsername());

			FacesContext.getCurrentInstance().addMessage(null, message);
			return "success.xhtml?faces-redirect=true";

		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, ConstantsEnum.REGISTRATION_ERROR_TITLE.getConstant(),
					ConstantsEnum.REGISTRATION_ERROR.getConstant());

			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}

	public void isEmailRegistered() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (registerService.checkExistingEmail(user.getEmail())) {

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, ConstantsEnum.REGISTRATION_ERROR_TITLE.getConstant(),
					ConstantsEnum.EMAIL_ALREADY_USED.getConstant()));

			emailValid = false;
		} else {
			emailValid = true;
		}
	}

	public void isUsernameRegistered() {

		FacesContext context = FacesContext.getCurrentInstance();
		if (registerService.checkExistingUser(user.getUsername())) {

			context.addMessage(null,

					new FacesMessage(FacesMessage.SEVERITY_WARN, ConstantsEnum.REGISTRATION_ERROR_TITLE.getConstant(),
							ConstantsEnum.USERNAME_ALREADY_USED.getConstant()));
			usernameValid = false;

		} else {
			usernameValid = true;
		}
	}

	public boolean isUsernameValid() {
		return usernameValid;
	}

	public void setUsernameValid(boolean usernameValid) {
		this.usernameValid = usernameValid;
	}

	public boolean isEmailValid() {
		return emailValid;
	}

	public void setEmailValid(boolean emailValid) {
		this.emailValid = emailValid;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public RegistrationDto getRegistration() {
		return registration;
	}

	public void setRegistration(RegistrationDto registration) {
		this.registration = registration;
	}
}
