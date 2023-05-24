package com.fdmgroup.blogplatform.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.blogplatform.exception.RoleNotFoundException;
import com.fdmgroup.blogplatform.model.Role;
import com.fdmgroup.blogplatform.repository.RoleRepository;


@Service
public class RoleService implements IRoleService {
	
	@Autowired
	private RoleRepository repo;

	@Override
	public Role findByRoleName(String roleName) throws RoleNotFoundException {
		// TODO Auto-generated method stub
		return repo.findByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException());
	}
	
	

}
