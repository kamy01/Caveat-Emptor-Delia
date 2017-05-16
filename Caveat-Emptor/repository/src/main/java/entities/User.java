package entities;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name = "User.findByUsername", query = "SELECT usr FROM User usr WHERE usr.username  = :name"),
		@NamedQuery(name = "User.findByEmail", query = "SELECT usr FROM User usr WHERE usr.email  = :name"),
		@NamedQuery(name = "User.findById", query = "SELECT usr FROM User usr WHERE usr.id  = :name"), })
public class User implements Serializable {

	private static final long serialVersionUID = 5863801679193281332L;
	public static final String FIND_USER_BY_USERNAME = "User.findByUsername";
	public static final String FIND_USER_BY_EMAIL = "User.findByEmail";
	public static final String FIND_USER_BY_ID = "User.findById";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 45)
	private String username;

	@Column(length = 100)
	private String password;

	@Column(length = 45)
	private String firstName;

	@Column(length = 45)
	private String lastName;

	@Column(length = 100)
	private String email;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "address_id_fk")
	private UserAddress address;

	@Column(length = 10)
	private String state;

	public User() {
		super();
	}

	public UserAddress getAddress() {
		return address;
	}

	public void setAddress(UserAddress address) {
		this.address = address;
	}

	public void setId(Long userId) {
		this.id = userId;
	}

	public Long getId() {
		return id;
	}

	public void setuserId(Long userId) {
		this.id = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emailAdress) {
		this.email = emailAdress;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (id != other.id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", address=" + address + ", state=" + state + "]";
	}

}
