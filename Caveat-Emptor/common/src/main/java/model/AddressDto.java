package model;

import java.io.Serializable;

public class AddressDto implements Serializable {

	private static final long serialVersionUID = 6288728117050804860L;
	private Long id;
	private String streetName;
	private String zipcode;
	private String city;
	
	public AddressDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long addressId) {
		this.id = addressId;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
