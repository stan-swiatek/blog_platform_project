package com.fdmgroup.blogplatform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.blogplatform.exception.ArticleNotFoundException;
import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.repository.ArticleRepository;

@Service
public class ArticleService implements IArticleService {

	@Autowired
	private ArticleRepository repo;
	
	@Override
	public List<Article> findByBlog(Blog blog){
		return repo.findByBlog(blog);
	}

	@Override
	public Article findById(int id) throws ArticleNotFoundException {
		// TODO Auto-generated method stub
		return repo.findById(id).orElseThrow(() -> new ArticleNotFoundException());
	}
	
	@Override
	public void createNewArticle(Article article) {
		repo.save(article);
	}
}
