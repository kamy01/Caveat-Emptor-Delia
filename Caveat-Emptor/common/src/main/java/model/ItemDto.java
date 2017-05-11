package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ItemDto implements Serializable {

	private static final long serialVersionUID = 5830382014860046401L;

	private long id;
	private UserDto owner;
	private CategoryDto category;
	private String name;
	private String description;
	private long initialPrice;
	private Timestamp openingDate;
	private Timestamp closingDate;
	private String status;
	private long winner;

	public ItemDto() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(long initialPrice) {
		this.initialPrice = initialPrice;
	}

	public Timestamp getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Timestamp openingDate) {
		this.openingDate = openingDate;
	}

	public Timestamp getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Timestamp closingDate) {
		this.closingDate = closingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getWinner() {
		return winner;
	}

	public void setWinner(long winner) {
		this.winner = winner;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}
}
