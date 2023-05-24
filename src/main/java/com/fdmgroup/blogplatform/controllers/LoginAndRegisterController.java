package com.fdmgroup.blogplatform.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.blogplatform.exception.RoleNotFoundException;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.service.IRoleService;
import com.fdmgroup.blogplatform.service.IUserService;





@Controller
public class LoginAndRegisterController extends BaseController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private PasswordEncoder encoder;
	

	
	@Autowired
	private IRoleService roleService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/login")
		public String loginFail(@ModelAttribute("user")User user, ModelMap model, 
				String password) {
		if(!userService.usernameExists(user.getUsername())) {
			model.addAttribute("message", "There is no User with this username");
			
		} else if(!encoder.matches(password, user.getPassword()))  {
			model.addAttribute("message", "Password does not match");
			
		}
		

		return "login";
			
		}
	
	
	@PostMapping("/register")
	public String registerSubmit(@ModelAttribute("user")User user, ModelMap model, @RequestParam(name="confirmPassword") String confirmPassword) throws RoleNotFoundException {
		if (userService.usernameExists(user.getUsername())) {
			model.addAttribute("message", "This username already exists.");
			return "register";
		}
		
		if (userService.emailExists(user.getEmail())) {
			model.addAttribute("message", "This email is already in use.");
			return "register";
		}
		
		if (!user.getPassword().equals(confirmPassword)) {
			model.addAttribute("message", "Passwords do not match.");
			return "register";
		}
		
		user.setRole(roleService.findByRoleName("User"));
		user.setPassword(encoder.encode(user.getPassword()));
		userService.saveUser(user);




		
		return "redirect:/login";

	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ModelAndView handleUsernameNotFoundException(UsernameNotFoundException ex) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("notFound");
		mav.addObject("type", "user");
		mav.addObject("message", ex.getMessage());
		return mav;
	}
}
