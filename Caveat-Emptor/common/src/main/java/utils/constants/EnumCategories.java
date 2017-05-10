package utils.constants;

public enum EnumCategories {

	CATEGORY_ADDED("Category added "), 
	CATEGRY_ERROR_TITLE("Category Error"), 
	ERROR_CATEGORY_INSERT("Category has not been added, please check the info!"),
	ERROR_CATEGORY_REMOVE("Category has not been removed, please check the info!");
	
	
	private final String constant;

	private EnumCategories(final String constant) {
		this.constant = constant;
	}
	public String getConstant() {
		return constant;
	}
	
}
