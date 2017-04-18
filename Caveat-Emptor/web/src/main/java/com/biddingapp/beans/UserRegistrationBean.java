package com.biddingapp.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.UserDto;
import services.UserService;

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

	@EJB
	UserService usrService;

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

	public UserService getUsrService() {
		return usrService;
	}

	public void setUsrService(UserService usrService) {
		this.usrService = usrService;
	}
	
	public String register() {

		FacesMessage message = null;

		UserDto usrDto = usrService.getUser(username);

		if (password.equals(usrDto.getPassword()) && username.equals(usrDto.getUsername())) {
			
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
			 return "success.xhtml?faces-redirect=true";
			
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");

			FacesContext.getCurrentInstance().addMessage(null, message);
			 return null;
		}
	}
}

