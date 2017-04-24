package com.biddingapp.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.UserDto;
import services.login.LoginService;
import utils.Constants;
import utils.UserException;
import utils.UserStateEnum;

@ManagedBean(name = "userLogin")
@ViewScoped
public class UserLoginBean {

	private String username;
	private String password;

	@EJB
	LoginService userService;

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

		FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.LOGIN_ERROR,
				Constants.INVALID_CREDENTIALS),
				success = new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.WELCOME, username);

		UserDto userDto = new UserDto();
		try {
			userDto = userService.getUser(username);
		} catch (UserException e) {
			FacesContext.getCurrentInstance().addMessage(null, error);
			return null;
		}

		boolean passwordMatch = password.equals(userDto.getPassword());
		boolean usernameMatch = username.equals(userDto.getUsername());
		boolean isEnabled = userDto.getState().equals(UserStateEnum.ENABLED.getState());

		if (!userDto.equals(null) && passwordMatch && usernameMatch && isEnabled) {

			FacesContext.getCurrentInstance().addMessage(null, success);
			return "success.xhtml?faces-redirect=true";

		} else {

			FacesContext.getCurrentInstance().addMessage(null, error);
			return null;
		}
	}

}
