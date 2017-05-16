package model;

import java.io.Serializable;
import java.util.Date;

public class ItemDto implements Serializable {

	private static final long serialVersionUID = -1361955225797897987L;
	private Long id;
	private UserDto owner;
	private CategoryDto category;
	private String name;
	private String description;
	private Long initialPrice;
	private Date openingDate;
	private Date closingDate;
	private String status;
	private UserDto winner;

	public ItemDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDto getOwner() {
		return owner;
	}

	public void setOwner(UserDto owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(Long initialPrice) {
		this.initialPrice = initialPrice;
	}

	public Date getOpeningDate() {
		return (Date) openingDate.clone();
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getClosingDate() {
		return (Date) closingDate.clone();
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserDto getWinner() {
		return winner;
	}

	public void setWinner(UserDto winner) {
		this.winner = winner;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}
}
