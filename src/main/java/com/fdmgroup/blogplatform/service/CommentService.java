package com.fdmgroup.blogplatform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.blogplatform.exception.CommentNotFoundException;
import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.Comment;
import com.fdmgroup.blogplatform.model.CommentThread;
import com.fdmgroup.blogplatform.model.Commentable;
import com.fdmgroup.blogplatform.repository.CommentRepository;

@Service
public class CommentService implements ICommentService {
	
	@Autowired
	private CommentRepository repo;
	
	@Override
	public void save(Comment comment) {
		repo.save(comment);
	}
	
	@Override
	public Comment findById(int id) throws CommentNotFoundException {
		return repo.findById(id).orElseThrow(() -> new CommentNotFoundException());
	}
	
	@Override
	public List<Comment> findByReplyTo(Commentable commentable){
		return repo.findByReplyTo(commentable);	
	}

	@Override
	public CommentThread createCommentThread(Comment start) {
		System.out.println("Creating thread entry for " + start.getContent());
		CommentThread thread = new CommentThread();
		thread.setComment(start);
		List<Comment> replies = findByReplyTo(start);

		replies.sort((c1, c2) -> {
			if(c1.getPostTime().isBefore(c2.getPostTime())) {
				return -1;
			}
			return 1;
		});
		
		for(Comment reply : replies) {
			thread.getChildren().add(createCommentThread(reply));
		}
		
		return thread;
	}
}
