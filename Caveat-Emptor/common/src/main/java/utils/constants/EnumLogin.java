package utils.constants;

public enum EnumLogin {

	LOGIN_ERROR("Loggin Error"), 
	INVALID_CREDENTIALS("Invalid credentials"), 
	WELCOME("Welcome");
	
	private final String constant;

	private EnumLogin(final String constant) {
		this.constant = constant;
	}
	public String getConstant() {
		return constant;
	}
	
}
