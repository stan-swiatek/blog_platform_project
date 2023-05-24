package com.fdmgroup.blogplatform.service;

import java.util.List;

import com.fdmgroup.blogplatform.exception.BlogNotFoundException;
import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserHasNoBlogException;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.Comment;
import com.fdmgroup.blogplatform.model.User;

public interface IBlogService {
	Blog getParentBlog(Comment comment);
	void createNewBlog(Blog blog);
	List<Blog> findAll();
	Blog findById(int id) throws BlogNotFoundException;
	List<Blog> findRecommendations(User user, int amount);
	List<Blog> findBySearchParams(String name, String description, List<String> tags);
	Blog findByOwner(User owner) throws UserHasNoBlogException;
	void subscribeBlog(int blogId) throws NoLoggedInUserException;
	void unSubscribeBlog(int blogId) throws NoLoggedInUserException ;
	List<Blog> findUpdatedBlogsByUser(User user);
	void blogChanged(Blog blog);
	List<Blog> findAll(int amount);
	void updateUnviewedContent(User user, Blog blog);

}
