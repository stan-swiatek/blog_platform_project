package com.fdmgroup.blogplatform.service.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fdmgroup.blogplatform.exception.BlogNotFoundException;
import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserHasNoBlogException;
import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;
import com.fdmgroup.blogplatform.model.Comment;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.repository.BlogRepository;
import com.fdmgroup.blogplatform.service.BlogService;
import com.fdmgroup.blogplatform.service.BlogTagService;
import com.fdmgroup.blogplatform.service.UserService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BlogServiceTests {

	@MockBean
	private BlogRepository repo;
	
	@MockBean
	private BlogTagService blogTagService;
	
	@MockBean
	private UserService userService;
	
	@InjectMocks
	private BlogService blogService;
	
	Blog blog;
	
	@BeforeEach
	public void setup() {
		blog = new Blog();
		blog.setTags(Arrays.asList(new BlogTag("Tag1"), new BlogTag("Tag2"), new BlogTag("Tag3")));
	}
	
	@Test
	public void testCreateNewBlog() {
		blogService.createNewBlog(blog);
		
		verify(repo, times(1)).save(blog);
		verify(blogTagService, times(blog.getTags().size())).save(any(BlogTag.class));
	}
	
	@Test
	public void testGetParentBlog() {
		Comment start = new Comment();
		Comment middle = new Comment();
		Article article = new Article();
		start.setReplyTo(middle);
		middle.setReplyTo(article);
		article.setBlog(blog);
		
		assertEquals(blog, blogService.getParentBlog(start));
	}
	
	@Test
	public void testFindAll() {
		List<Blog> expected = Arrays.asList(blog);
		when(repo.findAll()).thenReturn(expected);
		List<Blog> results = blogService.findAll();
		verify(repo, times(1)).findAll();
		assertEquals(expected, results);
	}
	
	@Test
	public void testfindRecommendations() {
		List<Blog> expected = Arrays.asList(blog);
		when(repo.findByUserInterest(any(User.class), any(PageRequest.class))).thenReturn(expected);
		List<Blog> results = blogService.findRecommendations(new User(), 1);
		assertEquals(expected, results);
		verify(repo, times(1)).findByUserInterest(any(User.class), any(PageRequest.class));
	}
	
	@Test
	public void testFindByOwner() throws UserHasNoBlogException {
		User user = new User();
		when(repo.findByOwner(user)).thenReturn(Optional.of(blog));
		Blog result = blogService.findByOwner(user);
		verify(repo, times(1)).findByOwner(user);
		assertEquals(blog, result);
	}
	
	@Test
	public void testfindBySearchParams() {
		List<Blog> expected = Arrays.asList(blog);
		String name = "name", description = "desc";
		List<String> tags = Arrays.asList("These", "are", "some", "tags");
		
		when(repo.findBySearchParams(name, description, tags, tags.size())).thenReturn(expected);
		List<Blog> results = blogService.findBySearchParams(name, description, tags);
		assertEquals(expected, results);
		verify(repo, times(1)).findBySearchParams(name, description, tags, tags.size());
	}
	
	
	@Test
	public void testSubscribeBlog() throws NoLoggedInUserException {
		Blog blog = new Blog();
		User user = new User();
		int blogId = 1;
		when(repo.findById(blogId)).thenReturn(Optional.of(blog));
		when(userService.getLoggedInUser()).thenReturn((user));
		blogService.subscribeBlog(blogId);
		assertTrue(blog.getSubscribers().containsKey(user));
		
	}
	
	@Test
	public void testUnsubscribeBlog() throws NoLoggedInUserException {
		Blog blog = new Blog();
		User user = new User();
		int blogId = 1;
		when(repo.findById(blogId)).thenReturn(Optional.of(blog));
		when(userService.getLoggedInUser()).thenReturn((user));
		blogService.unSubscribeBlog(blogId);
		assertTrue(!blog.getSubscribers().containsKey(user));
	}
	
	@Test
	public void testUpdateUnviewedContent() {
		User user = new User();
		blog.getSubscribers().put(user, true);
		
		blogService.updateUnviewedContent(user, blog);
		
		verify(repo, times(1)).save(blog);
		assertEquals(false, blog.getSubscribers().get(user));
	}
	
	@Test
	public void testFindUpdatedBlogsByUser() {
		User user = new User();
		List<Blog> blogs = Arrays.asList(blog);
		
		when(repo.findUpdatedBlogsByUser(user)).thenReturn(blogs);
		
		List<Blog> results = blogService.findUpdatedBlogsByUser(user);
		
		assertEquals(blogs, results);
		verify(repo, times(1)).findUpdatedBlogsByUser(user);
	}
	
	@Test
	public void testBlogChanged() {
		int entries = 10;
		blog.setSubscribers(new HashMap<>());
		for(int i = 0; i < entries; i++) {
			blog.getSubscribers().put(new User(), false);
		}
		
		blogService.blogChanged(blog);
		
		verify(repo, times(1)).save(blog);
		for(Map.Entry<User, Boolean> entry : blog.getSubscribers().entrySet()) {
			assertEquals(true, entry.getValue());
		}
	
	}
	
	@Test
	public void testFindById() throws BlogNotFoundException {
		when(repo.findById(0)).thenReturn(Optional.of(blog));
		
		Blog result = blogService.findById(0);
		
		assertEquals(blog, result);
		verify(repo, times(1)).findById(0);
	}
	
}
