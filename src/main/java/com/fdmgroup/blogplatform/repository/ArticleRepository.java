package com.fdmgroup.blogplatform.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	ArrayList<Article> findByBlog(Blog blog);
}
