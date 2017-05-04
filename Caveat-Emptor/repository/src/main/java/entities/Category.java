package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "Category.findById", query = "SELECT cat FROM Category cat WHERE cat.id  = :id"),
	@NamedQuery(name = "Category.getAllCategories", query = "SELECT cat FROM Category cat"),
	@NamedQuery(name = "Category.getChildren", query = "SELECT cat FROM Category cat where cat.parentId = :id"),
	@NamedQuery(name = "Category.findByName", query = "SELECT cat FROM Category cat where cat.name = :name"),})
public class Category implements Serializable {

	public static final String FIND_CATEGORY_BY_ID = "Category.findById";
	public static final String FIND_CATEGORY_BY_NAME = "Category.findByName";
	public static final String GET_CHILDREN_OF_CATEGORY = "Category.getChildren";
	public static final String GET_ALL_CATEGORIES = "Category.getAllCategories";
		
	private static final long serialVersionUID = 4349755949203026333L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="parent_id")
	private Long parentId;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(length = 45)
	private String name;

	@Column(length = 450)
	private String description;

	public Category() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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

}