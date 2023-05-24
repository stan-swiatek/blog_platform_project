package com.fdmgroup.blogplatform.service;

import java.util.List;

import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.model.UserBlogTagInterest;

public interface IUserBlogTagInterestService {
	void save(UserBlogTagInterest tag);
	List<UserBlogTagInterest> findByUser(User user);
}
