package com.biddingapp.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.UserDto;
import services.login.LoginService;
import utils.UserStateEnum;
import utils.constants.EnumLogin;
import utils.exceptions.UserException;

@ManagedBean(name = "userLogin")
@SessionScoped
public class UserLoginBean {

	private String username;
	private String password;
	
	private UserDto user;

	@EJB
	LoginService userService;
	
	@PostConstruct
	public void init() {
		user = new UserDto();
	}

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

	public String login() {

		FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_WARN, EnumLogin.LOGIN_ERROR.getConstant(),
				EnumLogin.INVALID_CREDENTIALS.getConstant()),
				success = new FacesMessage(FacesMessage.SEVERITY_INFO, EnumLogin.WELCOME.getConstant(), username);


		try {
			user = userService.getUser(username);
		} catch (UserException e) {
			FacesContext.getCurrentInstance().addMessage(null, error);
			return null;
		}

		boolean passwordMatch = password.equals(user.getPassword());
		boolean usernameMatch = username.equals(user.getUsername());
		boolean isEnabled = user.getState().equals(UserStateEnum.ENABLED.getState());

		if (!user.equals(null) && passwordMatch && usernameMatch && isEnabled) {

			FacesContext.getCurrentInstance().addMessage(null, success);
			return "caveatEmptor.xhtml?faces-redirect=true";

		} else {

			FacesContext.getCurrentInstance().addMessage(null, error);
			return null;
		}
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
