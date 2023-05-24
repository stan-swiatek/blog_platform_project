package com.fdmgroup.blogplatform.service;

import java.util.List;
import java.util.Map;

import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;

public interface IBlogTagService {

	List<BlogTag> findByBlog(Blog blog);
	void save(BlogTag tag);
	Map<Blog, List<String>> createBlogTagNameMap(List<Blog> blogs);
	void clearTags(Blog blog);

}
