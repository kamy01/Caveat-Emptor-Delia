package model;

import java.util.Date;

public class RegistrationDto {

	private Long id;
	private UserDto user;
	private Date expirationDate;
	private String validationCode;


	public RegistrationDto() {
		super();
	}
	
	public String getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Date getExpirationDate() {
		return (Date) expirationDate.clone();
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
