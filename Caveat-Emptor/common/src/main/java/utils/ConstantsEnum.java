package utils;

public enum ConstantsEnum {

	LOGIN_ERROR("Loggin Error"), 
	INVALID_CREDENTIALS("Invalid credentials"), 
	WELCOME("Welcome"),
	REGISTRATION_ERROR_TITLE("Registration Error"), 
	EMAIL_ALREADY_USED("This email address is already used!"), 
	REGISTRATION_ERROR("The register is incorrect! Please check your info!"), 
	CORRECT_REGISTER("Registered correctly. Please wait for the confirmation email."),
	USERNAME_ALREADY_USED("This username is already taken!");
	
	private final String constant;

	private ConstantsEnum(final String constant) {
		this.constant = constant;
	}
	public String getConstant() {
		return constant;
	}

	
}
