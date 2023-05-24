package com.fdmgroup.blogplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;

public interface BlogTagRepository extends JpaRepository<BlogTag, Integer> {

	List<BlogTag> findByBlog(Blog blog);
	
	void deleteByBlog(Blog blog);
}
