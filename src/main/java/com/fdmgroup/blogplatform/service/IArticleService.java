package com.fdmgroup.blogplatform.service;

import java.util.List;

import com.fdmgroup.blogplatform.exception.ArticleNotFoundException;
import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;

public interface IArticleService {
	List<Article> findByBlog(Blog blog);
	Article findById(int id) throws ArticleNotFoundException;
	void createNewArticle(Article article);
}
