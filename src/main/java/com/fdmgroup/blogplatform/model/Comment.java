package com.fdmgroup.blogplatform.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "comments")
public class Comment extends Commentable implements Serializable {
	@ManyToOne
	private Commentable replyTo;
	@ManyToOne
	private User creator;
	
	public Comment() {}
	
	public Comment(Commentable replyTo, User creator, String content, DateTime postTime) {
		super(content, postTime);
		this.replyTo = replyTo;
		this.creator = creator;
	}

	public Commentable getReplyTo() {
		return replyTo;
	}
	
	public void setReplyTo(Commentable replyTo) {
		this.replyTo = replyTo;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((postTime == null) ? 0 : postTime.hashCode());
		result = prime * result + ((replyTo == null) ? 0 : replyTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (editTime == null) {
			if (other.editTime != null)
				return false;
		} else if (!editTime.equals(other.editTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (postTime == null) {
			if (other.postTime != null)
				return false;
		} else if (!postTime.equals(other.postTime))
			return false;
		if (replyTo == null) {
			if (other.replyTo != null)
				return false;
		} else if (!replyTo.equals(other.replyTo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", replyTo=" + replyTo + ", creator=" + creator + ", content=" + content
				+ ", postTime=" + postTime + ", editTime=" + editTime + "]";
	}
}
