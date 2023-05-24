package com.fdmgroup.blogplatform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "commentable")
public abstract class Commentable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Integer id;
	@Lob
	@Column(name = "content")
	protected String content;
	@Lob
	@Column(name = "post_time")
	protected DateTime postTime;
	@Lob
	@Column(name = "last_edit_time")
	protected DateTime editTime;
	
	public Commentable() {}
	
	public Commentable(String content, DateTime postTime) {
		super();
		this.content = content;
		this.postTime = postTime;
	}

	public Integer getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DateTime getPostTime() {
		return postTime;
	}
	
	public String getReadablePostTime() {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		return postTime.toString(dtf);
	}
	
	public String getReadableEditTime() {
		if(editTime == null) return "";
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		return "edited: " + editTime.toString(dtf);
	}

	public void setPostTime(DateTime postTime) {
		this.postTime = postTime;
	}

	public DateTime getEditTime() {
		return editTime;
	}

	public void setEditTime(DateTime editTime) {
		this.editTime = editTime;
	}
}
