package com.fdmgroup.blogplatform.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdmgroup.blogplatform.exception.EmailAlreadyExistsException;
import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserIdNotFoundException;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.model.UserBlogTagInterest;
import com.fdmgroup.blogplatform.repository.UserRepository;
import com.fdmgroup.blogplatform.security.UserPrincipal;



@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private IUserBlogTagInterestService userBlogTagInterestService;
	
	@Autowired
	private IBlogTagService blogTagService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		return new UserPrincipal(user);
	}
	
	@Override
	public User findById(int id) throws UserIdNotFoundException {
		return repo.findById(id).orElseThrow(() -> new UserIdNotFoundException(id));
	}
	
	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		return repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	}
	
	@Override
	public User findByEmail(String email) throws EmailAlreadyExistsException {
		return repo.findByEmail(email).orElseThrow(() -> new EmailAlreadyExistsException());
	}
	
	@Override
	public boolean usernameExists(String username) {
		try {
			findByUsername(username);
		} catch (UsernameNotFoundException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean emailExists(String email) {
		try {
			findByEmail(email);
		} catch (EmailAlreadyExistsException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public void saveUser(User user) {
		repo.save(user);
	}

	@Override
	public User getLoggedInUser() throws NoLoggedInUserException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			return findByUsername(auth.getName());
		} catch(UsernameNotFoundException e) {
			throw new NoLoggedInUserException();
		}
	}
	
	@Override
	public boolean isLoggedIn() {
		try {
			getLoggedInUser();
		} catch(NoLoggedInUserException e) {
			return false;
		}
		return true;
	}
	
	@Override
	public void handleBlogVisit(User user, Blog blog) {
		increaseInterestInTags(user, blog);
		normalizeTagInterests(user);
		saveUser(user);
	}
	
	private void increaseInterestInTags(User user, Blog blog) {
		Double interestIncrease = 0.1d;
		
		List<UserBlogTagInterest> interests = userBlogTagInterestService.findByUser(user);
		List<BlogTag> tags = blogTagService.findByBlog(blog);
		
		for(BlogTag tag : tags) {
			UserBlogTagInterest currentInterest = null;
			for(UserBlogTagInterest interest : interests) {
				if(interest.getName().equals(tag.getName())) {
					currentInterest = interest;
					break;
				}
			}
			
			if(currentInterest == null) {
				currentInterest = new UserBlogTagInterest(user, tag.getName());
			}
			
			
			Double newVal = currentInterest.getInterest() + interestIncrease;
			currentInterest.setInterest(newVal);
			
			userBlogTagInterestService.save(currentInterest);
		}
	}
	
	private void normalizeTagInterests(User user) {
		List<UserBlogTagInterest> interests = userBlogTagInterestService.findByUser(user);
		Double sum = interests.stream().mapToDouble(interest -> interest.getInterest()).sum();
		
		if(sum <= 0) return;

		for(UserBlogTagInterest interest : interests) {
			interest.setInterest(interest.getInterest() / sum);
			
			userBlogTagInterestService.save(interest);
		}
	}


}
