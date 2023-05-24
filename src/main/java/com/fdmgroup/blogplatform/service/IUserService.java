package com.fdmgroup.blogplatform.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.fdmgroup.blogplatform.exception.EmailAlreadyExistsException;
import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserIdNotFoundException;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.User;

public interface IUserService extends UserDetailsService{

	User findById(int id) throws UserIdNotFoundException;
	User findByUsername(String username);
	void saveUser(User user);
	User getLoggedInUser() throws NoLoggedInUserException;
	boolean isLoggedIn();
	boolean usernameExists(String username);
	void handleBlogVisit(User user, Blog blog);
	User findByEmail(String email) throws EmailAlreadyExistsException;
	boolean emailExists(String email);
	

}
