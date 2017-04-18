package entities;

import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name="User.findByUsername",
    			query="SELECT u FROM User u WHERE u.username  = :name"),
}) 
public class User {
	
	public User() {
		super();
	}

	public User(String username, String password, String firstName,
			String lastName, String emailAdress) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAdress = emailAdress;
	}

	@Id
	@Column(name="user_id")
	@GeneratedValue
	private long userId;
	
	@Column(length = 45)
	private String username;
	
	@Column(length = 100)
	private String password;
	
	@Column(length = 45)
	private String firstName;
	
	@Column(length = 45)
	private String lastName;
	
	@Column(length = 100) 
	private String emailAdress;

	public long getuserId() {
		return userId;
	}

	public void setuserId(long userId) {
		this.userId = userId;
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

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}
}
