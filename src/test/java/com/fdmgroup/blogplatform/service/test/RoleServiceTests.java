package com.fdmgroup.blogplatform.service.test;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fdmgroup.blogplatform.exception.RoleNotFoundException;
import com.fdmgroup.blogplatform.model.Role;
import com.fdmgroup.blogplatform.repository.RoleRepository;
import com.fdmgroup.blogplatform.service.RoleService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class RoleServiceTests {
	
	@InjectMocks
	RoleService roleService;
	
	@MockBean
	RoleRepository repo;
	
	
	
	@Test
	public void testFindByRoleName() throws RoleNotFoundException {
		Role role = new Role();
		role.setRoleName("Janitor");
		when(repo.findByRoleName("Janitor")).thenReturn(Optional.of(role));
		Role result = roleService.findByRoleName("Janitor");
		assertEquals(role.getRoleName(), result.getRoleName());
		verify(repo, times(1)).findByRoleName("Janitor");
	}
	

}
