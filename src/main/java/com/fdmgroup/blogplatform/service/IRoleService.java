package com.fdmgroup.blogplatform.service;

import com.fdmgroup.blogplatform.exception.RoleNotFoundException;
import com.fdmgroup.blogplatform.model.Role;

public interface IRoleService {

	Role findByRoleName(String roleName) throws RoleNotFoundException;

}
