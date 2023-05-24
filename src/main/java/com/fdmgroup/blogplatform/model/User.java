package com.fdmgroup.blogplatform.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "uzerz")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String username;
	@Column(name="email", unique = true)
	private String email;
	@Column(name="firstname")
	private String firstName;
	@Column(name="surname")
	private String surName;
	@ElementCollection
	private List<String> profilePicture;
	
	private String password;
	@Lob
	private String bio;
	
//	@ElementCollection
//	@CollectionTable(name = "interest_tags", joinColumns = @JoinColumn(name = "user_id"))
//	@Column(name = "tag")
//	private Map<String, Double> tags;
//	@ManyToOne
	private Role role;

	public User() {}
	
	public User(Role role, String username, String password, String firstName, String surName, String email) {
		super();
		this.role = role;
		this.username = username;
		this.firstName = firstName;
		this.surName = surName;
		this.password = password;
		this.email = email;
//		this.tags = new HashMap<>();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public Integer getId() {
		return id;
	}

//	public Map<String, Double> getTags() {
//		return tags;
//	}

	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public List<String> getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		if(this.profilePicture== null) {
			this.profilePicture = new ArrayList<String>();
		}
		this.profilePicture.add(profilePicture);
		
	}


}
