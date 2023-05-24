package com.fdmgroup.blogplatform.controllers;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.blogplatform.data.Tags;
import com.fdmgroup.blogplatform.exception.ArticleNotFoundException;
import com.fdmgroup.blogplatform.exception.BlogNotFoundException;
import com.fdmgroup.blogplatform.exception.CommentNotFoundException;
import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserHasNoBlogException;
import com.fdmgroup.blogplatform.exception.UserIdNotFoundException;
import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;
import com.fdmgroup.blogplatform.model.Comment;
import com.fdmgroup.blogplatform.model.CommentThread;
import com.fdmgroup.blogplatform.model.Commentable;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.security.UserPrincipal;
import com.fdmgroup.blogplatform.util.FileUploadUtil;

@Controller
public class BlogController extends BaseController {

	@GetMapping("/new_posts")
	public ModelAndView goToNewPosts() throws NoLoggedInUserException {
		ModelAndView mav = new ModelAndView("recentlyUpdatedBlogs");
		
		List<Blog> blogs = blogService.findUpdatedBlogsByUser(userService.getLoggedInUser());
				
		mav.addObject("blogTagMap", blogTagService.createBlogTagNameMap(blogs));
		
		return mav;
	}
	
	
	@PostMapping("/comment/{id}")
	public String postReply(@PathVariable int id, @RequestParam(name="content") String content, @RequestParam String button)
			throws NoLoggedInUserException, ArticleNotFoundException {
		Commentable parent;
		try {
			parent = commentService.findById(id);
		} catch (CommentNotFoundException e) {
			parent = articleService.findById(id);
		}
		User loggedIn = userService.getLoggedInUser();
		DateTime time = DateTime.now();

		int blogId = -1;
		
		if(button.equals("Reply") || button.equals("Comment")) {
			Comment newComment = new Comment(parent, loggedIn, content, time);
			commentService.save(newComment);
			blogId = blogService.getParentBlog(newComment).getId();
		}
		
		
		Comment comment;
		if(parent instanceof Comment) {
			comment = (Comment) parent;
			
			if(button.equals("Submit")) {
				comment.setContent(content);
				comment.setEditTime(DateTime.now());
				commentService.save(comment);
				blogId = blogService.getParentBlog(comment).getId();
			}
			
			if(button.equals("Delete")) {
				comment.setContent("This comment was deleted.");
				comment.setEditTime(DateTime.now());
				commentService.save(comment);
				blogId = blogService.getParentBlog(comment).getId();
			}
		}
		
		if(blogId == -1) {
			return "redirect:/";
		}
		return "redirect:/blogs/" + blogId;
	}

	@GetMapping("/blogs/{id}")
	public ModelAndView goToBlogView(@PathVariable int id, @ModelAttribute Comment comment)
			throws UserIdNotFoundException, BlogNotFoundException, NoLoggedInUserException {
		ModelAndView mav = createBlogViewModel(id);

		if (userService.isLoggedIn()) {
			User user = userService.getLoggedInUser();
			Blog blog = blogService.findById(id);
			userService.handleBlogVisit(user, blog);
			blogService.updateUnviewedContent(user, blog);
			List<Blog> updatedBlogs = blogService.findUpdatedBlogsByUser(user);
			mav.addObject("numberOfUpdates", updatedBlogs.size());
		}
		
		return mav;
	}
	
	private ModelAndView createBlogViewModel(int id) throws UserIdNotFoundException, BlogNotFoundException, NoLoggedInUserException {
		ModelAndView mav = new ModelAndView("blogView");
		
		User loggedInUser = userService.getLoggedInUser();
		
		Blog blog = blogService.findById(id);
		mav.addObject("blog", blog);
		
		List<Article> articles = articleService.findByBlog(blog);
		
		articles.sort((a1, a2) -> {
			
			if(a1.getPostTime().isBefore(a2.getPostTime())) {
				return 1;
			}
			return -1;
		});
		mav.addObject("articles", articles);
		mav.addObject("ownBlog", userService.isLoggedIn() && blog.getOwner().equals(loggedInUser));
		
		LinkedHashMap<Article, List<CommentThread>> map = new LinkedHashMap<>();
		
		for(Article article : articles) {
			List<CommentThread> commentThreads = new ArrayList<>();
			commentThreads.sort((c1, c2) -> {
				if(c1.getComment().getPostTime().isBefore(c2.getComment().getPostTime())) {
					return 1;
				}
				return -1;
			});
			
			for(Comment comment : commentService.findByReplyTo(article)) {
				commentThreads.add(commentService.createCommentThread(comment));
			}
			
			map.put(article, commentThreads);
		}
		
		mav.addObject("subscribedUser", blog.getSubscribers().containsKey(loggedInUser));
		mav.addObject("article_comment_map", map);
		
		return mav;
	}

	@GetMapping("/NewBlogPage")
	public String goToCreateNewBlog(Model model) {
		Blog blog = (Blog) model.getAttribute("blog");
		if (blog == null) {
			blog = new Blog();
		}

		model.addAttribute("blog", blog);
		model.addAttribute("name", blog.getName());
		model.addAttribute("description", blog.getDescription());
		model.addAttribute("items", Tags.getTags());
		return "NewBlogPage";

	}

	@PostMapping("/NewBlogPage")
	public String createNewBlog(@ModelAttribute Blog blog, ModelMap model, @RequestParam("tagListButton") String item, 
			@RequestParam(value = "blogTag", required = false) String[] tags)

			throws NoLoggedInUserException {

		if(tags == null) 
			tags = new String[0];
		
		blog.getTags().clear();
		for(String tagName : tags) {
			BlogTag tag = new BlogTag(tagName);
			blog.getTags().add(tag);
		}
		
		
		if (item.equals("Submit")) {
			blog.setOwner(userService.getLoggedInUser());
			
			blogService.createNewBlog(blog);

			return "redirect:/";
		} 
		
		model.addAttribute("blog", blog);
		model.addAttribute("items", Tags.getTags());
		return "NewBlogPage";
	}

	@GetMapping("/blogs/{id}/newArticle")
	public String goToCreateNewArticle(ModelMap model, @PathVariable int id) {
		Article article = (Article) model.getAttribute("article");
		if (article == null) {
			article = new Article();

		}

		model.addAttribute("blogId", id);
		model.addAttribute("article", article);
		model.addAttribute("title", article.getTitle());
		model.addAttribute("content", article.getContent());

		return "newArticle";

	}

	@PostMapping("/blogs/{id}/newArticle")
	public String createNewArticle(@ModelAttribute Article article, ModelMap model, @PathVariable int id,
			@RequestParam(value = "image", required = false) MultipartFile[] multipartFiles) throws IOException, BlogNotFoundException {
		
		Blog blog = blogService.findById(id);
		
		article.setBlog(blog);
		article.setPostTime(DateTime.now());
		articleService.createNewArticle(article);

		for (MultipartFile multipartFile : multipartFiles) {

			if (multipartFile != null && !multipartFile.isEmpty()) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				String uploadDir = "./src/main/webapp/img/" + article.getId();
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				article.setPhotos("/img/" + article.getId() + "/" + fileName);

			}
		}

		articleService.createNewArticle(article);
		model.addAttribute("blogId", id);
		model.addAttribute("article", article);
		
		blogService.blogChanged(blog);

		return "redirect:/blogs/" + id;

	}

	@GetMapping("/blogs/{blogId}/editArticle/{articleId}")
	public String displayCurrentArticle(@PathVariable int articleId, @PathVariable int blogId, ModelMap model) throws ArticleNotFoundException {
		Article article = articleService.findById(articleId);
		model.addAttribute("article", article);

		return "editArticle";

	}

	@PostMapping("/blogs/{blogId}/editArticle/{articleId}")
	public String editArticle(@PathVariable int articleId,
			@PathVariable int blogId,
			@ModelAttribute("article") Article updatedArticle, ModelMap model,
			@RequestParam(value = "image", required = false) MultipartFile[] multipartFiles,
			@RequestParam(value = "removePhoto", required = false) String[] removePhotos)
			throws ArticleNotFoundException, IOException {

		Article currentArticle = articleService.findById(articleId);

		currentArticle.setTitle(updatedArticle.getTitle());
		currentArticle.setContent(updatedArticle.getContent());
		currentArticle.setEditTime(DateTime.now());
		
		if (removePhotos != null && removePhotos.length > 0) {
			for (String photoPath : removePhotos) {
				File file = new File("./src/main/webapp" + photoPath);
				if (file.exists()) {
					currentArticle.getPhotos().removeIf(photoURI -> photoPath.equals(photoURI));
					file.delete();
				}
			}
		}
		
		
		for (MultipartFile multipartFile : multipartFiles) {

			if (multipartFile != null && !multipartFile.isEmpty()) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				String uploadDir = "./src/main/webapp/img/" + currentArticle.getId();
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				currentArticle.setPhotos("/img/" + currentArticle.getId() + "/" + fileName);

			}
		}

		model.addAttribute("article", currentArticle);
		articleService.createNewArticle(currentArticle);
		
		return "redirect:/blogs/" + blogId;
	}

	@GetMapping("/editBlogPage/{id}")
	public String editBlogPage(@PathVariable int id, ModelMap model)
			throws UserHasNoBlogException, BlogNotFoundException {

		Blog currentBlog = blogService.findById(id);
		
		currentBlog.setTags(blogTagService.findByBlog(currentBlog));
		
		model.addAttribute("presentTags", blogTagService.findByBlog(currentBlog).stream().map(tag -> tag.getName()).toList());
		model.addAttribute("blog", currentBlog);
		model.addAttribute("items", Tags.getTags());

		return "editBlogPage";

	}

	@PostMapping("/editBlogPage/{id}")
	public String editBlogPage(@ModelAttribute("blog") Blog updatedBlog, @PathVariable int id,
			@RequestParam("tagListButton") String item, ModelMap model, 
			@RequestParam(value = "blogTag", required = false) String[] tags)
			throws UserHasNoBlogException, BlogNotFoundException, NoLoggedInUserException {


		if(tags == null) 
			tags = new String[0];
		
		updatedBlog.getTags().clear();
		blogTagService.clearTags(updatedBlog);
		
		for(String tagName : tags) {
			BlogTag tag = new BlogTag(tagName);
			updatedBlog.getTags().add(tag);
		}
		
		
		if (item.equals("Submit")) {

			updatedBlog.setOwner(userService.getLoggedInUser());
			blogService.createNewBlog(updatedBlog);

			return "redirect:/";
		}

		BlogTag tag = new BlogTag(item);
		
		updatedBlog.getTags().add(tag);
		model.addAttribute("blog", updatedBlog);
		model.addAttribute("items", Tags.getTags());

		return "editBlogPage";
	}

	@PostMapping("/blogs/{blogId}/subscribeBlog")
	public String subscribeToBlog(@ModelAttribute("blog") Blog blog, ModelMap model, @PathVariable int blogId) throws NoLoggedInUserException {
		
			blogService.subscribeBlog(blogId);
			model.addAttribute("blog", blog);

		return "redirect:/blogs/" + blogId;
		
	}
	
	@PostMapping("/blogs/{blogId}/unSubscribeBlog")
	public String unSubscribeToBlog(@ModelAttribute("blog") Blog blog, ModelMap model, @PathVariable int blogId) throws NoLoggedInUserException {
		
			blogService.unSubscribeBlog(blogId);
			model.addAttribute("blog", blog);

		return "redirect:/blogs/" + blogId;
	}
	
}
