package com.biddingapp.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import services.register.RegisterService;
import utils.exceptions.*;

@ManagedBean(name = "activationBean")
@RequestScoped
public class ConfirmAccountBean {

	private String scope;
	@ManagedProperty(value = "#{param.userId}")
	private String userId;
	@ManagedProperty(value = "#{param.code}")
	private String code;
	boolean enabled;

	@EJB
	RegisterService user;

	@PostConstruct
	public void init() {

		try {
			if (user.enableUser(Long.parseLong(userId), code)) {
				enabled = true;
			}
		} catch (NumberFormatException e) {
			System.err.println(e + " in ConfirmAccountBean init() method for userId=" + userId);
		} catch (UserException e) {
			System.err.println(e + " in ConfirmAccountBean init() method");
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
