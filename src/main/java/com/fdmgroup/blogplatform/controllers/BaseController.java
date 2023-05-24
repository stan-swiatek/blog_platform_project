package com.fdmgroup.blogplatform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.blogplatform.data.Tags;
import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserHasNoBlogException;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.service.IArticleService;
import com.fdmgroup.blogplatform.service.IBlogService;
import com.fdmgroup.blogplatform.service.IBlogTagService;
import com.fdmgroup.blogplatform.service.ICommentService;
import com.fdmgroup.blogplatform.service.IUserService;

@Controller
public class BaseController {
	@Autowired
	protected IUserService userService;
	
	@Autowired
	protected IBlogService blogService;
	
	@Autowired
	protected IBlogTagService blogTagService;
	
	@Autowired
	protected IArticleService articleService;
	
	@Autowired
	protected ICommentService commentService;
	
	@ModelAttribute
	public void addCommonAttributes(Model model) {
		model.addAttribute("loggedIn", userService.isLoggedIn());
		try {
			User loggedInUser = userService.getLoggedInUser();
			model.addAttribute("loggedInUser", userService.getLoggedInUser());
			Integer ownBlogId = blogService.findByOwner(loggedInUser).getId();
			model.addAttribute("ownBlogId", ownBlogId);
			model.addAttribute("hasBlog", true);
			List<Blog> updatedBlogs = blogService.findUpdatedBlogsByUser(loggedInUser);
			model.addAttribute("numberOfUpdates", updatedBlogs.size());
		} catch (NoLoggedInUserException e) 
		{} catch (UserHasNoBlogException e) {
			model.addAttribute("hasBlog", false);
		}
		List<String> tags = Tags.getTags();
		model.addAttribute("tags", tags);
		
	}
	
	@ExceptionHandler(NoLoggedInUserException.class)
	public ModelAndView handleNoUserLoggedInException(NoLoggedInUserException e) {
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("errorMessage", "No user is logged in.");
		return mav;
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ModelAndView handlePlaceNotFoundException(Exception ex) {
		ModelAndView mav = new ModelAndView("error");
		return mav;
	}
	
	@ExceptionHandler(value = NoLoggedInUserException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ModelAndView handlePlaceNotFoundException(NoLoggedInUserException ex) {
		ModelAndView mav = new ModelAndView("redirect:/login");
		return mav;
	}
}
