package com.fdmgroup.blogplatform.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;
import com.fdmgroup.blogplatform.repository.BlogTagRepository;
import com.fdmgroup.blogplatform.service.BlogTagService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BlogTagServiceTests {

	@MockBean
	private BlogTagRepository repo;
	
	@InjectMocks
	private BlogTagService blogTagService;
	
	@Test
	public void testFindByBlog() {
		Blog blog = new Blog();
		List<BlogTag> tags = Arrays.asList(new BlogTag("These"), new BlogTag("are"), new BlogTag("some"), new BlogTag("tags"));

		when(repo.findByBlog(blog)).thenReturn(tags);
		List<BlogTag> results = blogTagService.findByBlog(blog);
		verify(repo, times(1)).findByBlog(blog);
		assertEquals(tags, results);
	}
	
	@Test
	public void testSave() {
		BlogTag tag = new BlogTag("tag");
		blogTagService.save(tag);
		verify(repo, times(1)).save(tag);
	}
	
	@Test
	public void testClearTags() {
		Blog blog = new Blog();
		blogTagService.clearTags(blog);
		verify(repo, times(1)).deleteByBlog(blog);
	}
	
	@Test
	public void testCreateBlogTagNameMap() {
		List<Blog> blogs = new ArrayList<>();
		Blog blog = new Blog();
		blogs.add(blog);
		
		List<BlogTag> bts = new ArrayList<>();
		bts.add(new BlogTag("test"));
		
		when(repo.findByBlog(blog)).thenReturn(bts);
		
		Map<Blog, List<String>> map = blogTagService.createBlogTagNameMap(blogs);
		assertTrue(map.containsKey(blog));
		verify(repo, times(1)).findByBlog(blog);
	}
}
