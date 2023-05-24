package com.fdmgroup.blogplatform.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "articles")
public class Article extends Commentable implements Serializable {
	@ManyToOne
	private Blog blog;
	@Column(name = "article_title")
	private String title;
	
	
	@ElementCollection

	private List<String> photos;
	
	public Article() {
		
	}
	
	public Article(Blog blog, String title, String content, DateTime postTime) {
		super(content, postTime);
		this.blog = blog;
		this.title = title;
	}
	
	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		if(this.photos== null) {
			this.photos = new ArrayList<String>();
		}
		this.photos.add(photos);
	}

	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blog == null) ? 0 : blog.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((editTime == null) ? 0 : editTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((postTime == null) ? 0 : postTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Article other = (Article) obj;
		if (blog == null) {
			if (other.blog != null)
				return false;
		} else if (!blog.equals(other.blog))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
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
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Article [id=" + id + ", blog=" + blog + ", title=" + title + ", content=" + content + ", postTime="
				+ postTime + ", editTime=" + editTime + "]";
	}
}
