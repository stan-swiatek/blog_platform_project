package com.fdmgroup.blogplatform.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.User;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
	Optional<Blog> findByOwner(User user);
	
	@Query(""
			+ "SELECT b "
			+ "FROM Blog b "
			+ "JOIN b.subscribers s "
			+ "WHERE (KEY(s) = :user) "
			+ "AND (VALUE(s) = TRUE) ")
	List<Blog> findUpdatedBlogsByUser(@Param(value = "user") User user);
	
	
//	SELECT b.*
//	FROM BLOGS b
//	JOIN BLOG_TAGS bt ON bt.blog_id = b.id
//	JOIN USER_INTEREST ui ON ui.name = bt.name
//	WHERE ui.user_id = 1
//	GROUP BY b.id
//	ORDER BY SUM(ui.interest) DESC;
	
	@Query(""
			+ "SELECT b "
			+ "FROM Blog b "
			+ "JOIN BlogTag bt ON bt.blog=b "
			+ "JOIN UserBlogTagInterest ui ON ui.name=bt.name "
			+ "WHERE ui.user = :user "
			+ "GROUP BY b "
			+ "ORDER BY SUM(ui.interest) DESC")
	List<Blog> findByUserInterest(@Param(value = "user") User user, Pageable pageable);
	
	@Query(""
			+ "SELECT b "
			+ "FROM Blog b "
			+ "WHERE (0 < LOCATE(LOWER(:description), LOWER(b.description))) "
			+ "AND (0 < LOCATE(LOWER(:name), LOWER(b.name))) "
			+ "AND (:tagNum = (SELECT COUNT(t.name) "
							+ "FROM BlogTag t "
							+ "WHERE t.blog = b "
							+ "AND t.name IN :tags "
							+ "GROUP BY t.blog) "
				  + "OR :tagNum = 0)")
	List<Blog> findBySearchParams(@Param("name") String name, @Param("description") String description, @Param("tags") List<String> tags, @Param("tagNum") long tagNum);
}
