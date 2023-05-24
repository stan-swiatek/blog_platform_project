package com.fdmgroup.blogplatform.controller.test;

import static org.mockito.Mockito.when;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fdmgroup.blogplatform.App;
import com.fdmgroup.blogplatform.data.Tags;
import com.fdmgroup.blogplatform.exception.ArticleNotFoundException;
import com.fdmgroup.blogplatform.exception.BlogNotFoundException;
import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserHasNoBlogException;
import com.fdmgroup.blogplatform.exception.UserIdNotFoundException;
import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.service.IArticleService;
import com.fdmgroup.blogplatform.service.IBlogService;
import com.fdmgroup.blogplatform.service.IBlogTagService;
import com.fdmgroup.blogplatform.service.ICommentService;
import com.fdmgroup.blogplatform.service.IUserService;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = App.class)
public class ControllerTests {

	@Autowired
	private MockMvc mockMvc;
	

	@MockBean
	protected IUserService userService;
	
	@MockBean
	protected IBlogService blogService;
	
	@MockBean
	protected IBlogTagService blogTagService;
	
	@MockBean
	protected IArticleService articleService;
	
	@MockBean
	protected ICommentService commentService;
	

	User loggedIn;
	List<Blog> blogs;
	Blog blog;
	List<Article> articles;
	Article article;
	Map<Blog, List<String>> map;
	
	@BeforeEach
	public void setup() throws NoLoggedInUserException, UserHasNoBlogException, BlogNotFoundException, ArticleNotFoundException, UserIdNotFoundException {
		loggedIn = new User();
		blogs = new ArrayList<>();
		blog = new Blog();
		blogs.add(blog);
		articles = new ArrayList<>();
		article = new Article();
		articles.add(article);
		blog.setOwner(loggedIn);
		map = new LinkedHashMap<>();
		
		when(userService.getLoggedInUser()).thenReturn(loggedIn);
		when(userService.isLoggedIn()).thenReturn(true);
		when(userService.findById(1)).thenReturn(loggedIn);
		
		when(blogService.findByOwner(loggedIn)).thenReturn(blog);
		when(blogService.findById(1)).thenReturn(blog);
		when(blogService.findUpdatedBlogsByUser(loggedIn)).thenReturn(blogs);
		when(blogService.findBySearchParams(any(String.class), any(String.class), any(List.class))).thenReturn(blogs);
		when(blogService.findRecommendations(loggedIn, 1)).thenReturn(blogs);
		when(blogService.findAll(1)).thenReturn(blogs);
		
		when(blogTagService.createBlogTagNameMap(blogs)).thenReturn(map);
		
		when(articleService.findByBlog(blog)).thenReturn(articles);
		when(articleService.findById(1)).thenReturn(article);
		
	}
	
	@Test
	@WithMockUser
	public void testGoToNewPosts() throws Exception {
		
		LinkedHashMap<Blog, List<String>> map = new LinkedHashMap<>();
		
		when(blogService.findUpdatedBlogsByUser(loggedIn)).thenReturn(blogs);
		when(blogTagService.createBlogTagNameMap(blogs)).thenReturn(map);
		
		mockMvc.perform(get("/new_posts"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("blogTagMap", map))
		.andExpect(view().name("recentlyUpdatedBlogs"));
	}	
	
//	@Test
//	@WithMockUser
//	public void testPostReply() throws Exception {
//		
//		
//		
//		
//		mockMvc.perform(post("/comment/{id}", 0).param("id", "0"))
//		.andExpect(status().is3xxRedirection())
//		.andExpect(redirectedUrl("/blogs/0"));
//	}
	
	@Test
	@WithMockUser
	public void testGoToBlogView() throws Exception {
		
		
		mockMvc.perform(get("/new_posts"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("numberOfUpdates", blogs.size()))
		.andExpect(view().name("recentlyUpdatedBlogs"));
	}
	
	@Test
	@WithMockUser
	public void testGoToCreateNewBlog() throws Exception {
		mockMvc.perform(get("/NewBlogPage"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("items", Tags.getTags()))
		.andExpect(view().name("NewBlogPage"));
	}

	@Test
	@WithMockUser
	public void testGoToCreateNewArticle() throws Exception {
		mockMvc.perform(get("/blogs/1/newArticle"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("blogId", 1))
		.andExpect(view().name("newArticle"));
	}
	
	@Test
	@WithMockUser
	public void testSearch() throws Exception {
		
		mockMvc.perform(post("/search")
				.param("blogName", "myBlog")
				.param("blogDescription", "myDesc"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("blogTagMap", map))
		.andExpect(view().name("search"));
	}
	
	@Test
	@WithMockUser
	public void testGoToSearch() throws Exception {
		
		mockMvc.perform(get("/search"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("blogTagMap", map))
		.andExpect(view().name("search"));
	}
	
	@Test
	@WithMockUser
	public void testGoToIndex() throws Exception {
		
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("blogTagMap", map))
		.andExpect(view().name("index"));
	}
	
	@Test
	@WithMockUser
	public void testDisplayUserProfile() throws Exception {
		
		mockMvc.perform(get("/UserProfile/{id}", 1))
		.andExpect(status().isOk())
		.andExpect(model().attribute("user", loggedIn))
		.andExpect(view().name("UserProfile"));
	}
	
	@Test
	@WithMockUser
	public void testEditUserDetails() throws Exception {
		
		mockMvc.perform(get("/editUserDetails"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("user", loggedIn))
		.andExpect(view().name("editUserDetails"));
	}
	
//	@Test
//	@WithMockUser
//	public void testEditUserDetailsPost() throws Exception {
//		
//		mockMvc.perform(post("/editUserDetails"))
//		.andExpect(status().isOk())
//		.andExpect(model().attribute("user", loggedIn))
//		.andExpect(view().name("editUserDetails"));
//	}
	
	
	
	
//	@Test
//	@WithMockUser
//	public void testDisplayCurrentArticle() throws Exception {
//
//		mockMvc.perform(get("/blogs/1/editArticle/1"))
//		.andExpect(status().isOk())
//		.andExpect(view().name("editArticle"));
//	}

	
//	@Test
//	@WithMockUser
//	public void testEditBlogPage() {
//		
//	}
//	
//	@Test
//	@WithMockUser
//	public void testEditBlogPage() {
//		
//	}
}
