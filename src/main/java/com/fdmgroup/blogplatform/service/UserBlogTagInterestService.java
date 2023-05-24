package com.fdmgroup.blogplatform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.model.UserBlogTagInterest;
import com.fdmgroup.blogplatform.repository.UserBlogTagInterestRepository;

@Service
public class UserBlogTagInterestService implements IUserBlogTagInterestService {
	@Autowired
	private UserBlogTagInterestRepository repo;
	
	@Override
	public void save(UserBlogTagInterest tag) {
		repo.save(tag);
	}
	
	@Override
	public List<UserBlogTagInterest> findByUser(User user){
		return repo.findByUser(user);
	}
}
