package com.fdmgroup.blogplatform.service.test;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fdmgroup.blogplatform.model.UserBlogTagInterest;
import com.fdmgroup.blogplatform.repository.UserBlogTagInterestRepository;
import com.fdmgroup.blogplatform.service.UserBlogTagInterestService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserBlogTagInterestServiceTests {
	
	@InjectMocks
	private UserBlogTagInterestService service;
	
	@MockBean
	private UserBlogTagInterestRepository repo;
	
	
	
	@Test 
	public void testSave(){
		UserBlogTagInterest tag = new UserBlogTagInterest();
		service.save(tag);
		verify(repo, times(1)).save(tag);
		
	}
	
	

}
