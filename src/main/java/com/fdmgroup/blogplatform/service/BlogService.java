package com.fdmgroup.blogplatform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fdmgroup.blogplatform.exception.BlogNotFoundException;
import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserHasNoBlogException;
import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;
import com.fdmgroup.blogplatform.model.Comment;
import com.fdmgroup.blogplatform.model.Commentable;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.repository.BlogRepository;

@Service
public class BlogService implements IBlogService {
	@Autowired
	private BlogRepository repo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogTagService blogTagService;
	
	@Override
	public Blog findById(int id) throws BlogNotFoundException {
		return repo.findById(id).orElseThrow(() -> new BlogNotFoundException());
	}
		
	@Override
	public void createNewBlog(Blog blog) {
		repo.save(blog);
		
		for(BlogTag tag : blog.getTags()) {
			tag.setBlog(blog);
			blogTagService.save(tag);
		}
	}

	@Override
	public void updateUnviewedContent(User user, Blog blog) {
		if(blog.getSubscribers().containsKey(user)) {
			blog.getSubscribers().put(user, false);
			repo.save(blog);
		}
	}
	
	@Override
	public Blog getParentBlog(Comment comment) {
		Commentable parent = comment.getReplyTo();
		
		while(!(parent instanceof Article)) {
			parent = ((Comment) parent).getReplyTo();
		}
		
		return ((Article) parent).getBlog();
	}
	
	@Override
	public List<Blog> findAll(){
		return repo.findAll();
	}
	
	@Override
	public List<Blog> findRecommendations(User user, int amount){
		Pageable pageable = PageRequest.of(0, amount);
		List<Blog> blogs = repo.findByUserInterest(user, pageable);
		if(blogs.isEmpty()) {
			blogs = repo.findAll(pageable).getContent();
		}
		return blogs;
	}
	
	@Override 
	public List<Blog> findAll(int amount){
		Pageable pageable = PageRequest.of(0, amount);
		return repo.findAll(pageable).getContent();
	}

	@Override
	public Blog findByOwner(User owner) throws UserHasNoBlogException {
		// TODO Auto-generated method stub
		return repo.findByOwner(owner).orElseThrow(() -> new UserHasNoBlogException(owner));
	}

	@Override

	public void subscribeBlog(int blogId) throws NoLoggedInUserException {
		Blog blog = repo.findById(blogId).orElseThrow(() -> new EntityNotFoundException("Blog not found"));
		User subscriber = userService.getLoggedInUser();
		blog.getSubscribers().put(subscriber, false);
		repo.save(blog);
			
			
		}
	
	@Override
	public void unSubscribeBlog(int blogId) throws NoLoggedInUserException {
		Blog blog = repo.findById(blogId).orElseThrow(() -> new EntityNotFoundException("Blog not found"));
		User subscriber = userService.getLoggedInUser();
		if(blog.getSubscribers().containsKey(subscriber)) {
			blog.getSubscribers().remove(subscriber);
			repo.save(blog);
		}		
	}
		
	
	@Override
	public List<Blog> findBySearchParams(String name, String description, List<String> tags) {
		// TODO Auto-generated method stub
		return repo.findBySearchParams(name, description, tags, tags.size());
	}

	@Override
	public List<Blog> findUpdatedBlogsByUser(User user){
		return repo.findUpdatedBlogsByUser(user);
	}

	@Override
	public void blogChanged(Blog blog) {
		for(Map.Entry<User, Boolean> entry : blog.getSubscribers().entrySet()) {
			entry.setValue(true);
		}
		
		repo.save(blog);
	}
}
