package com.fdmgroup.blogplatform.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;
import com.fdmgroup.blogplatform.repository.BlogTagRepository;

@Service
public class BlogTagService implements IBlogTagService {

	@Autowired
	private BlogTagRepository repo;
	
	@Override
	public List<BlogTag> findByBlog(Blog blog) {
		return repo.findByBlog(blog);
	}
	
	@Override
	public void save(BlogTag tag) {
		repo.save(tag);
	}
	
	@Override
	@Transactional //Somehow this is needed because
	//https://stackoverflow.com/questions/32269192/spring-no-entitymanager-with-actual-transaction-available-for-current-thread
	public void clearTags(Blog blog) {
		repo.deleteByBlog(blog);
	}
	
	@Override
	public Map<Blog, List<String>> createBlogTagNameMap(List<Blog> blogs) {
		Map<Blog, List<String>> blogTagMap = new LinkedHashMap<>();
		
		for(Blog blog : blogs) {
			blogTagMap.put(blog, findByBlog(blog).stream().map(tag -> tag.getName()).toList());
		}
		
		return blogTagMap;
	}
	
}
