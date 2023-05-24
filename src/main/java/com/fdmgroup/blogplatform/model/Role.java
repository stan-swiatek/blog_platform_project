package com.fdmgroup.blogplatform.model;

import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;



@Entity
public class Role implements GrantedAuthority{
	@Id
	@GeneratedValue
	private Integer roleId;
	
    private String roleName;

    public Role() {}

    public Role(String roleName) {
    	super();
		this.roleName = roleName;
	}

	public String getAuthority() {
		// TODO Auto-generated method stub
		return "ROLE_" + roleName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}