package com.fdmgroup.blogplatform.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.blogplatform.data.Tags;
import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.model.Blog;

@Controller
public class HomeController extends BaseController {
	

	@ModelAttribute
	@Override
	public void addCommonAttributes(Model model) {
		super.addCommonAttributes(model);
	}
	
	@GetMapping("/")
	public ModelAndView goToIndex() throws NoLoggedInUserException {
		ModelAndView mav = new ModelAndView("index");
		
		boolean loggedIn = userService.isLoggedIn();
		int blogsToShow = 5;
		
		List<Blog> blogs = null;
		if(loggedIn) {
			blogs = blogService.findRecommendations(userService.getLoggedInUser(), blogsToShow);
		}
		
		if(blogs == null || blogs.isEmpty())
		{
			blogs = blogService.findAll(blogsToShow);
		}
		
		mav.addObject("blogTagMap", blogTagService.createBlogTagNameMap(blogs));
		
		return mav;
	}
	
	@GetMapping("/search")
	public ModelAndView goToSearch() {
		ModelAndView mav = createSearchMAV();
		
		return mav;
	}
	
	@PostMapping("/search")
	public ModelAndView search(@RequestParam String blogName, 
			@RequestParam String blogDescription, 
			@RequestParam(value = "searchTag", required = false) String[] tags) {
		ModelAndView mav = createSearchMAV();
		
		List<String> tagList;
		
		if(tags == null) 
			tagList = new ArrayList<String>();
		else
			tagList = Arrays.asList(tags);
		
		List<Blog> blogs = blogService.findBySearchParams(blogName, blogDescription, tagList);
		
		mav.addObject("blogTagMap", blogTagService.createBlogTagNameMap(blogs));
		mav.addObject("blogName", blogName);
		mav.addObject("blogDescription", blogDescription);
		
		return mav;
	}
	
	private ModelAndView createSearchMAV() {
		ModelAndView mav = new ModelAndView("search");
		List<Blog> blogs;
		try {
			blogs = blogService.findRecommendations(userService.getLoggedInUser(), 10);
		} catch (NoLoggedInUserException e) {
			blogs = blogService.findAll();
		}
		mav.addObject("blogTagMap", blogTagService.createBlogTagNameMap(blogs));
		return mav;
	}
	
	@GetMapping("/faq")
	public String goToFaq() {
		return "faq";
	}
}
