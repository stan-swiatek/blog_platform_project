package com.fdmgroup.blogplatform.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.blogplatform.exception.NoLoggedInUserException;
import com.fdmgroup.blogplatform.exception.UserIdNotFoundException;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.util.FileUploadUtil;



@Controller
public class UserManagementController extends BaseController{
	
	
	@GetMapping("/UserProfile/{id}")
	public ModelAndView displayUserProfile(ModelMap model, @PathVariable int id) throws UserIdNotFoundException {
		ModelAndView modelAndView = createUserViewModel(id, "UserProfile");

		return modelAndView;
		
	}
	
	
	@GetMapping("/editUserDetails")
	public ModelAndView editUserDetails(ModelMap model) throws NoLoggedInUserException {
		ModelAndView modelAndView = new ModelAndView("editUserDetails");
		
		modelAndView.addObject("user", userService.getLoggedInUser());
		
		return modelAndView;
	}
	
	private ModelAndView createUserViewModel(int id, String view) throws UserIdNotFoundException {
		ModelAndView mav = new ModelAndView(view);
		User user = userService.findById(id);
		mav.addObject("user", user);
		return mav;
	}
	
	@PostMapping("/editUserDetails")
	public String editUserDetails(@ModelAttribute("user") User updatedUser,
			@RequestParam(value = "image", required = false) MultipartFile[] multipartFiles,
			@RequestParam(value = "removePhoto", required = false) String[] removePhotos,
			ModelMap model) throws NoLoggedInUserException, IOException {
		
		
		User loggedUser = userService.getLoggedInUser();

		loggedUser.setFirstName(updatedUser.getFirstName());
		loggedUser.setSurName(updatedUser.getSurName());
		loggedUser.setBio(updatedUser.getBio());
		loggedUser.setEmail(updatedUser.getEmail());
		
		
		if (removePhotos != null && removePhotos.length > 0) {
			for (String photoPath : removePhotos) {
				File file = new File("./src/main/webapp" + photoPath);
				if (file.exists()) {
					loggedUser.getProfilePicture().removeIf(photoURI -> photoPath.equals(photoURI));
					file.delete();
					
				}
			}
		}
		

		
		for (MultipartFile multipartFile : multipartFiles) {


			if (multipartFile != null && !multipartFile.isEmpty()) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				String uploadDir = "./src/main/webapp/img/" + loggedUser.getId();
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				loggedUser.setProfilePicture("/img/" + loggedUser.getId() + "/" + fileName);

		}
		}
		
		model.addAttribute("user", loggedUser);
//		model.addAttribute("firstName", loggedUser.getFirstName());
//		model.addAttribute("surName", loggedUser.getSurName());
//		model.addAttribute("bio", loggedUser.getBio());
		
		userService.saveUser(loggedUser);
	
		return "editUserDetails";
		
	}

}
