package com.biddingapp.beans;

import java.util.Date;
import java.util.UUID;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.AddressDto;
import model.RegistrationDto;
import model.UserDto;
import services.register.RegisterService;
import services.register.email.ConfirmationEmail;
import utils.Constants;
import utils.UserException;
import utils.UserStateEnum;

@ManagedBean(name = "userRegister")
@ViewScoped
public class UserRegistrationBean {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String city;
	private String street;
	private String zipcode;
	private boolean usernameValid = true;
	private boolean emailValid = true;

	@EJB
	RegisterService registerService;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String register() {

		FacesMessage message = null;

		RegistrationDto registration = new RegistrationDto();
		UserDto user = new UserDto();
		AddressDto address = new AddressDto();

		address.setCity(city);
		address.setStreetName(street);
		address.setZipcode(zipcode);

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
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

			message = new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.CORRECT_REGISTER, username);

			FacesContext.getCurrentInstance().addMessage(null, message);
			return "success.xhtml?faces-redirect=true";

		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.REGISTRATION_ERROR_TITLE,
					Constants.REGISTRATION_ERROR);

			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}

	public void isEmailRegistered() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (registerService.checkExistingEmail(email)) {

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.REGISTRATION_ERROR_TITLE,
					Constants.EMAIL_ALREADY_USED));

			emailValid = false;
		} else {
			emailValid = true;
		}
	}

	public void isUsernameRegistered() {

		FacesContext context = FacesContext.getCurrentInstance();
		if (registerService.checkExistingUser(username)) {

			context.addMessage(null,

					new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.REGISTRATION_ERROR_TITLE,
							Constants.USERNAME_ALREADY_USED));
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
}
