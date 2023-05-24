package com.fdmgroup.blogplatform.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.blogplatform.model.Comment;
import com.fdmgroup.blogplatform.model.Commentable;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	ArrayList<Comment> findByReplyTo(Commentable article);
}
