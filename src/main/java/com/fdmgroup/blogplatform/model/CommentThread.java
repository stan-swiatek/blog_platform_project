package com.fdmgroup.blogplatform.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

public class CommentThread implements Serializable {
	private List<CommentThread> children;
	private Comment comment;
	
	public CommentThread() {
		children = new ArrayList<>();
	}
	
	public List<CommentThread> getChildren() {
		return children;
	}
	public void setChildren(List<CommentThread> children) {
		this.children = children;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment replyTo) {
		this.comment = replyTo;
	}
}
