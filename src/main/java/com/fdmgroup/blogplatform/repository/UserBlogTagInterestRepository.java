package com.fdmgroup.blogplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.model.UserBlogTagInterest;

public interface UserBlogTagInterestRepository extends JpaRepository<UserBlogTagInterest, Integer> {
	List<UserBlogTagInterest> findByUser(User user);
}
