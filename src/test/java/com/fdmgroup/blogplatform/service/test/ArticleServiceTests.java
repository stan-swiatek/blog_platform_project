package com.fdmgroup.blogplatform.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fdmgroup.blogplatform.exception.ArticleNotFoundException;
import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.repository.ArticleRepository;
import com.fdmgroup.blogplatform.service.ArticleService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ArticleServiceTests {

	@MockBean
	private ArticleRepository repo;
	
	@InjectMocks
	private ArticleService articleService;
	
	private ArrayList<Article> articles;
	private Article article;
	private Blog blog;
	
    @BeforeEach
    public void setUp() {
        articles = new ArrayList<>();
        article = new Article();
        blog = new Blog();

        article.setBlog(blog);
        articles.add(article);
    }

    @Test
    public void testFindByBlog() {
        when(repo.findByBlog(blog)).thenReturn(articles);

        List<Article> result = articleService.findByBlog(blog);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(article, result.get(0));
    }

    @Test
    public void testFindByIdSuccess() throws ArticleNotFoundException {
        when(repo.findById(1)).thenReturn(Optional.of(article));

        Article result = articleService.findById(1);

        assertNotNull(result);
        assertEquals(article, result);
    }

    @Test
    public void testFindByIdFail() {
        when(repo.findById(1)).thenReturn(Optional.empty());

        try {
            articleService.findById(1);
        } catch (ArticleNotFoundException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testCreateNewArticle() {
        articleService.createNewArticle(article);

        verify(repo).save(article);
    }
}
