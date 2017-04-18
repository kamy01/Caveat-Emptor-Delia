package com.biddingapp.beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.UserDto;
import services.UserService;

@ManagedBean(name = "userLogin")
@ViewScoped
public class UserLoginBean {

	private String username;
	private String password;

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

	public String login() {

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
