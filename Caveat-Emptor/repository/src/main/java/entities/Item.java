package entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "Item.findByName", query = "SELECT it FROM Item it WHERE it.name  = :name"),
		@NamedQuery(name = "Item.findByCategoryId", query = "SELECT it FROM Item it WHERE it.categoryId  = :id"),
		@NamedQuery(name = "Item.findByUserId", query = "SELECT it FROM Item it WHERE it.userId  = :id"),
		@NamedQuery(name = "Item.findById", query = "SELECT it FROM User it WHERE it.id  = :id"), 
		@NamedQuery(name = "Item.getAllItems", query = "SELECT it FROM Item it"),})
public class Item implements Serializable {

	public static final String FIND_ITEM_BY_NAME = "Item.findByName";
	public static final String FIND_ITEM_BY_CATEGORY_ID = "Item.findByCategoryId";
	public static final String FIND_ITEM_BY_CATEGORY_NAME = "Item.findByCategoryName";
	public static final String FIND_ITEM_BY_USER_ID = "Item.findByUserId";
	public static final String GET_ALL_ITEMS = "Item.getAllItems";

	private static final long serialVersionUID = 9047647663339787977L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 45, name = "user_id")
	private long userId;

	@Column(length = 45, name = "category_id")
	private long categoryId;

	@Column(length = 45)
	private String name;

	@Column(length = 100)
	private String description;

	@Column(name = "initial_price")
	private long initialPrice;

	@Column(name = "opening_date")
	private Timestamp openingDate;

	@Column(name = "closing_date")
	private Timestamp closingDate;

	@Column(length = 10)
	private String status;

	@Column(length = 45, name = "winner_id")
	private long winner;

	public Item() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
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
}
