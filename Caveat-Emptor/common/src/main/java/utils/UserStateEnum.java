package utils;

public enum UserStateEnum {

	PENDING("pending"), ENABLED("enabled"), DISABLED("disabled");

	private final String state;

	private UserStateEnum(final String state) {
		this.state = state;
	}
	public String getState() {
		return state;
	}

}
