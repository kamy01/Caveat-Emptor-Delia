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
import javax.persistence.Table;

@Entity
@Table(name="user_registration")
@NamedQueries({
    @NamedQuery(name="UserRegistration.findRegistrationByUserId",
    			query="SELECT reg FROM UserRegistration reg where reg.user.id = :name"),
}) 
public class UserRegistration implements Serializable {

	private static final long serialVersionUID = 6264641848581551347L;
	public static final String FIND_REGISTRATION_BY_USERID = "UserRegistration.findRegistrationByUserId";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	@JoinColumn(name="user_id_fk")
	private User user;

	@Column(length = 100, name="expiration_date") 
	private Timestamp expirationDate;
	
	@Column(length = 100, name="validation_code") 
	private String validationCode;
	
	public UserRegistration() {
		super();
	}
	
	public String getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long registrationId) {
		this.id = registrationId;
	}

	public Timestamp getExpirationDate() {
		return (Timestamp) expirationDate.clone();
	}

	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}

}
