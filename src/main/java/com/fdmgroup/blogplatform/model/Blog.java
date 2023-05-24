package com.fdmgroup.blogplatform.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "blogs")
public class Blog implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne
	private User owner;
	@Lob
	@Column(unique = true)
	private String name;
	@Lob
	private String description;
//	@ElementCollection
//	@CollectionTable(name = "blog_tags", joinColumns = @JoinColumn(name = "blog_id"))
//	@Column(name = "tag")
	@Transient
	private List<BlogTag> tags;
	@ElementCollection
	@CollectionTable(name = "blog_subscriptions", joinColumns = @JoinColumn(name = "blog_id"))
	@Column(name = "unviewed_content")
	private Map<User, Boolean> subscribers;
	
	public Blog() {
		if(tags == null) {
			tags = new ArrayList<>();
		}
		if(subscribers == null) {
			subscribers = new HashMap<>();
		}
	}
	
	public Blog(User owner, String name, String description) {
		super();
		this.owner = owner;
		this.name = name;
		this.description = description;
		tags = new ArrayList<>();
	}

	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
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
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Map<User, Boolean> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Map<User, Boolean> subscribers) {
		this.subscribers = subscribers;
	}

	
	public List<BlogTag> getTags() {
		return tags;
	}
	
	public void setTags(List<BlogTag> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((subscribers == null) ? 0 : subscribers.hashCode());
//		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
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
		Blog other = (Blog) obj;
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
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (subscribers == null) {
			if (other.subscribers != null)
				return false;
		} else if (!subscribers.equals(other.subscribers))
			return false;
//		if (tags == null) {
//			if (other.tags != null)
//				return false;
//		} else if (!tags.equals(other.tags))
//			return false;
		return true;
	}


}
