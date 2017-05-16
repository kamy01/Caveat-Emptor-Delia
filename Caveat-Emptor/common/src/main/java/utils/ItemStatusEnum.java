package utils;

public enum ItemStatusEnum {

	OPEN("open"), CLOSED("closed"), ABANDONED("abandoned"), NOT_YET_OPENED("not yet opened");

	private final String status;

	private ItemStatusEnum(final String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}

	
}
