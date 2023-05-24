package com.fdmgroup.blogplatform.service.test;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserIdNotFoundException;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.model.UserBlogTagInterest;
import com.fdmgroup.blogplatform.repository.UserRepository;
import com.fdmgroup.blogplatform.security.UserPrincipal;
import com.fdmgroup.blogplatform.service.BlogTagService;
import com.fdmgroup.blogplatform.service.IBlogTagService;
import com.fdmgroup.blogplatform.service.IUserBlogTagInterestService;
import com.fdmgroup.blogplatform.service.UserBlogTagInterestService;
import com.fdmgroup.blogplatform.service.UserService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTests {
	
	@InjectMocks
	private UserService userService;
	
	@MockBean
    private UserRepository repo;
	
	@MockBean
    private IUserBlogTagInterestService btiService;
	
	@MockBean
	private IBlogTagService btService;
	
	
	User user;
	
	@BeforeEach
	public void setup() {
		user = new User();
	}
	

	@Test
	public void testLoadUserByUsernameSuccess() {
		User user = new User();
		user.setUsername("Marian");
		when(repo.findByUsername("Marian")).thenReturn(Optional.of(user));
		UserDetails result = userService.loadUserByUsername("Marian");
		assertEquals(user.getUsername(), result.getUsername());
		verify(repo, times(1)).findByUsername("Marian");
	
	}
	
	
	@Test
	public void testFindByUserName() {
		User user = new User();
		user.setUsername("Marian");
		when(repo.findByUsername("Marian")).thenReturn(Optional.of(user));
		User result = userService.findByUsername("Marian");
		assertEquals(user.getUsername(), result.getUsername());
		verify(repo, times(1)).findByUsername("Marian");
		
	}
	

	
	
	@Test
	public void testFindById() throws UserIdNotFoundException {
		int id =1;
		User user = new User();
		when(repo.findById(id)).thenReturn(Optional.of(user));
		User result = userService.findById(id);
		assertEquals(user.getId(), result.getId());
		verify(repo, times(1)).findById(id);
		
	}
	
	
	@Test
	public void testGetLoggedInUser() throws NoLoggedInUserException {
		User user = new User();
		user.setUsername("Marian");
		when(repo.findByUsername("Marian")).thenReturn(Optional.of(user));
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn("Marian");
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		User result = userService.getLoggedInUser();
		assertEquals(user.getUsername(), result.getUsername());
		verify(repo, times(1)).findByUsername("Marian");
		
	}

	
	@Test
	public void testIsLoggedIn() throws NoLoggedInUserException {
		User user = new User();
		user.setUsername("Marian");
		when(repo.findByUsername("Marian")).thenReturn(Optional.of(user));
		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn("Marian");
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		boolean result = userService.isLoggedIn();
		assertTrue(result);
		verify(repo, times(1)).findByUsername("Marian");
	}
	
	
	
	@Test
	public void testUserNameExist() {
		User user = new User();
		user.setUsername("Marian");
		when(repo.findByUsername("Marian")).thenReturn(Optional.of(user));
		boolean result = userService.usernameExists("Marian");
		assertTrue(result);
		verify(repo, times(1)).findByUsername("Marian");
	}
	
	
	@Test
	public void testSaverUser() {
		User user = new User();
		userService.saveUser(user);
		verify(repo, times(1)).save(user);
	}
	
	
	@Test
	public void testEmailExist() {
		User user = new User();
		user.setEmail("abc@abc.com");
		when(repo.findByEmail("abc@abc.com")).thenReturn(Optional.of(user));
		boolean result = userService.emailExists("abc@abc.com");
		assertTrue(result);
		verify(repo, times(1)).findByEmail("abc@abc.com");
	}
	
	@Test
	public void testHandleBlogVist() {
		Blog blog = new Blog();
		List<BlogTag> expectedBTs = new ArrayList<>();
		List<UserBlogTagInterest> expectedUBTIs = new ArrayList<>();
		
		for(int i = 0; i < 4; i++) {
			BlogTag bt = new BlogTag(""+i);
			expectedBTs.add(bt);
			
			if(i%2==0) {
				UserBlogTagInterest ubti = new UserBlogTagInterest();
				ubti.setInterest(0.4d);
				ubti.setName(""+i);
				expectedUBTIs.add(ubti);
			}
		}
		
		UserBlogTagInterest ubti = new UserBlogTagInterest();
		ubti.setInterest(0.2d);
		ubti.setName("sada");
		expectedUBTIs.add(ubti);
		
		when(btiService.findByUser(user)).thenReturn(expectedUBTIs);
		when(btService.findByBlog(blog)).thenReturn(expectedBTs);
		
		
		userService.handleBlogVisit(user, blog);
		
		verify(repo, times(1)).save(user);
		verify(btiService, atLeast(5)).save(any(UserBlogTagInterest.class));
		
		assertTrue(expectedUBTIs.get(0).getInterest() > 0.4d);
		assertTrue(expectedUBTIs.get(1).getInterest() > 0.4d);
		assertTrue(expectedUBTIs.get(2).getInterest() < 0.2d);
	}
}
