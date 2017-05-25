package entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({ @NamedQuery(name = "Item.findByName", query = "SELECT it FROM Item it WHERE it.name  = :name"),
		@NamedQuery(name = "Item.findByCategoryId", query = "SELECT it FROM Item it, Category c WHERE c.id  = :id"),
		@NamedQuery(name = "Item.getItemsForUser", query = "SELECT it from Item it where it.owner.id  = :id"),
		@NamedQuery(name = "Item.findById", query = "SELECT it FROM User it WHERE it.id  = :id"), 
		@NamedQuery(name = "Item.getAllItems", query = "SELECT it FROM Item it"),
		@NamedQuery(name = "Item.getItemsForCategories", query = "SELECT it FROM Item it where it.category.id in (:idList)")})
public class Item implements Serializable {

	private static final long serialVersionUID = -8470034525611044083L;
	public static final String FIND_ITEM_BY_NAME = "Item.findByName";
	public static final String FIND_ITEM_BY_CATEGORY_ID = "Item.findByCategoryId";
	public static final String FIND_ITEM_BY_CATEGORY_NAME = "Item.findByCategoryName";
	public static final String GET_ITEMS_FOR_USER = "Item.getItemsForUser";
	public static final String GET_ALL_ITEMS = "Item.getAllItems";
	public static final String GET_ITEMS_FOR_CATEGORIES = "Item.getItemsForCategories";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "owner_id_fk")
	private User owner;

	@OneToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "category_id_fk")
	private Category category;

	@Column(length = 45)
	private String name;

	@Column(length = 100)
	private String description;

	@Column(name = "initial_price")
	private Long initialPrice;

	@Column(name = "opening_date")
	private Timestamp openingDate;

	@Column(name = "closing_date")
	private Timestamp closingDate;

	@Column(length = 10)
	private String status;

	@OneToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "winner_id_fk")
	private User winner;

	public Item() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public Timestamp getOpeningDate() {
		return (Timestamp) openingDate.clone();
	}

	public void setOpeningDate(Timestamp openingDate) {
		this.openingDate = openingDate;
	}

	public Timestamp getClosingDate() {
		return (Timestamp) closingDate.clone();
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

	public User getWinner() {
		return winner;
	}

	public void setWinner(User winner) {
		this.winner = winner;
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", owner=" + owner + ", category=" + category + ", name=" + name + ", description="
				+ description + ", initialPrice=" + initialPrice + ", openingDate=" + openingDate + ", closingDate="
				+ closingDate + ", status=" + status + ", winner=" + winner.toString() + "]";
	}
}
