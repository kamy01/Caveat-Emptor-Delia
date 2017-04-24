package com.biddingapp.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.UserDto;
import services.login.LoginService;
import utils.Constants;
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

		FacesMessage message = null;

		UserDto userDto = userService.getUser(username);
		
		boolean passwordMatch = password.equals(userDto.getPassword());
		boolean usernameMatch = username.equals(userDto.getUsername());
		boolean isEnabled = userDto.getState().equals(UserStateEnum.ENABLED.getState());
		

		if (!userDto.equals(null) && passwordMatch && usernameMatch && isEnabled) {

			message = new FacesMessage(FacesMessage.SEVERITY_INFO, Constants.WELCOME, username);
			FacesContext.getCurrentInstance().addMessage(null, message);
			
			return "success.xhtml?faces-redirect=true";

		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, Constants.LOGIN_ERROR, Constants.INVALID_CREDENTIALS);

			FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}
	}

}
