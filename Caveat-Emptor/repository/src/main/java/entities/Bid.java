package entities;

import java.io.Serializable;

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
@NamedQueries({ @NamedQuery(name = "Bid.getAllBids", query = "SELECT b FROM Bid b"),
		 @NamedQuery(name = "Bid.getBidForUser", query = "SELECT b FROM Bid b where b.item.id=:itemId and b.user.id = :userId")})
public class Bid implements Serializable {

	private static final long serialVersionUID = 4139602802551466322L;
	public static final String GET_ALL_BIDS = "Bid.getAllBids";
	public static final String GET_BID_FOR_USER = "Bid.getBidForUser";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "item_id_fk")
	private Item item;
	
	@OneToOne(cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "bidder_id_fk")
	private User user;
	
	@Column
	private Long price;
	
	@Column
	private String valid;

	public Bid() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
}
