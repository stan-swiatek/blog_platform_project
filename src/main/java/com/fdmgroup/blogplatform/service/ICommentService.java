package com.fdmgroup.blogplatform.service;

import java.util.List;

import com.fdmgroup.blogplatform.exception.CommentNotFoundException;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.Comment;
import com.fdmgroup.blogplatform.model.CommentThread;
import com.fdmgroup.blogplatform.model.Commentable;

public interface ICommentService {

	CommentThread createCommentThread(Comment start);
	List<Comment> findByReplyTo(Commentable commentable);
	Comment findById(int id) throws CommentNotFoundException;
	void save(Comment comment);
}
