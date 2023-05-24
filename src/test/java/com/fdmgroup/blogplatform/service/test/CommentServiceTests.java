package com.fdmgroup.blogplatform.service.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fdmgroup.blogplatform.exception.CommentNotFoundException;
import com.fdmgroup.blogplatform.model.Comment;
import com.fdmgroup.blogplatform.model.CommentThread;
import com.fdmgroup.blogplatform.model.Commentable;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.repository.CommentRepository;
import com.fdmgroup.blogplatform.service.CommentService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CommentServiceTests {

	@MockBean
	private CommentRepository repo;
	
	@InjectMocks
	private CommentService service;
	
	@Test
	public void testSave() {
		
	    Comment comment = new Comment();   
	    service.save(comment);
	    verify(repo, times(1)).save(comment);
		
		
	}
	
	@Test
	public void testFindById() throws CommentNotFoundException {
		int id =1;
		Comment comment = new Comment();
		when(repo.findById(id)).thenReturn(Optional.of(comment));
		Comment result = service.findById(id);
		assertEquals(comment.getId(), result.getId());
		verify(repo, times(1)).findById(id);
		
		
	}
	
	

	
	@Test
	public void testFindByReplyTo() {
		 Commentable commentable = mock(Commentable.class);
	    Comment comment1 = new Comment();
	    comment1.setReplyTo(commentable);
	    Comment comment2 = new Comment();
	    comment2.setReplyTo(commentable);
	    repo.save(comment1);
	    repo.save(comment2);
	    
	    List<Comment> savedComments = repo.findAll();
	    
	    List<Comment> comments = service.findByReplyTo(commentable);
	    assertTrue(savedComments.containsAll(comments));
	    verify(repo, times(1)).findByReplyTo(commentable);
	}
	
	@Test
	public void testCreateCommentThread() {
		ArrayList<Comment> replies = new ArrayList<>();
		Comment reply1 = new Comment();
		reply1.setPostTime(DateTime.now());
		Comment reply2 = new Comment();
		reply2.setPostTime(DateTime.now());
		Comment reply3 = new Comment();
		reply3.setPostTime(DateTime.now());
		
		replies.add(reply1);
		replies.add(reply2);
		replies.add(reply3);
		
		Comment start = new Comment();
		
		when(repo.findByReplyTo(start)).thenReturn(replies);
		
		CommentThread result = service.createCommentThread(start);
		
		for(CommentThread c : result.getChildren()) {
			assertTrue(replies.contains(c.getComment()));
		}
		
		assertEquals(start, result.getComment());
	}
}
