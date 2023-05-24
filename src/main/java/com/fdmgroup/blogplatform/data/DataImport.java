package com.fdmgroup.blogplatform.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.github.javafaker.Lorem;

import com.fdmgroup.blogplatform.model.Article;
import com.fdmgroup.blogplatform.model.Blog;
import com.fdmgroup.blogplatform.model.BlogTag;
import com.fdmgroup.blogplatform.model.Comment;
import com.fdmgroup.blogplatform.model.Role;
import com.fdmgroup.blogplatform.model.User;
import com.fdmgroup.blogplatform.model.UserBlogTagInterest;
import com.fdmgroup.blogplatform.repository.ArticleRepository;
import com.fdmgroup.blogplatform.repository.BlogRepository;
import com.fdmgroup.blogplatform.repository.BlogTagRepository;
import com.fdmgroup.blogplatform.repository.CommentRepository;
import com.fdmgroup.blogplatform.repository.RoleRepository;
import com.fdmgroup.blogplatform.repository.UserBlogTagInterestRepository;
import com.fdmgroup.blogplatform.repository.UserRepository;

@Component
public class DataImport implements ApplicationRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserBlogTagInterestRepository userBlogTagInterestRepository;

	@Autowired
	private BlogTagRepository blogTagRepository;
	
	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Role roleAdmin = new Role("Admin");
		roleRepository.save(roleAdmin);
		Role roleUser = new Role("User");
		roleRepository.save(roleUser);
		roleAdmin = roleRepository.findByRoleName("Admin").get();
		roleUser = roleRepository.findByRoleName("User").get();		
		
		User user1 = new User(roleUser, "user1", encoder.encode("123"), "Userus", "Oneus", "user@anothersite.com");
		user1.setBio("Slightly less impressive bio than admin's.");
		userRepository.save(user1);
		
		System.out.println("Creating admin");

		User admin = new User(roleAdmin, "admin", encoder.encode("123"), "Ad", "Min", "admin@site.com");
		admin.setBio("This is a super cool bio!");

		userRepository.save(admin);
		
		UserBlogTagInterest interest1 = new UserBlogTagInterest(admin, "Technology");
		interest1.setInterest(0.35d);
		userBlogTagInterestRepository.save(interest1);
		
		UserBlogTagInterest interest2 = new UserBlogTagInterest(admin, "Sports");
		interest2.setInterest(0.65d);
		userBlogTagInterestRepository.save(interest2);
		



		Blog adminBlog = new Blog(admin, "The Master's Blog",
				"This is a blog about the admin's cruel rule over this website.");
//		adminBlog.getTags().add(new BlogTag("Technology"));
//		adminBlog.getTags().add(new BlogTag("Politics"));
//		adminBlog.getTags().add(new BlogTag("Neurobiology"));
//		adminBlog.getTags().add(new BlogTag("Linguistics"));
		blogRepository.save(adminBlog);

		Article adminArticle1 = new Article(adminBlog, "Article 1",
				"This is the text of this article. Truly captivating!", new DateTime(2023, 06, 06, 22, 17));
		articleRepository.save(adminArticle1);

		Article adminArticle2 = new Article(adminBlog, "Article 2", "This is the text of another article. Interesting!",
				new DateTime(2023, 07, 11, 12, 17));
		articleRepository.save(adminArticle2);

		List<String> tags = Tags.getTags();
		
		Random random = new Random();
		
		Faker faker = new Faker();
		Random rnd = new Random();

		for (int i = 2; i <= 150; i++) {
			System.out.println("Creating user" + i);
			String username = "user" + i;
			String password = encoder.encode("123");
			String firstName = faker.name().firstName();
			String lastName = faker.name().lastName();
			String email = "user" + i + "@emailsite.com";
			String bio = faker.lorem().paragraph(rnd.nextInt(5, 15));

			User user = new User(roleUser, username, password, firstName, lastName, email);
			user.setBio(bio);
			userRepository.save(user);

			String blogTitle = faker.book().title();
			Blog blog = new Blog(user, blogTitle, faker.lorem().paragraph(rnd.nextInt(10, 25)));
			blogRepository.save(blog);
			
			int tagCount = random.nextInt(3) + 2;
			List<Integer> alreadyRolled = new ArrayList<>();
			
			for (int j = 0; j < tagCount; j++) {
				int tagIndex = random.nextInt(tags.size());
				if(alreadyRolled.contains(tagIndex)) {
					j--;
					continue;
				}
				alreadyRolled.add(tagIndex);
				
				BlogTag tag = new BlogTag(tags.get(tagIndex));
				tag.setBlog(blog);
				blogTagRepository.save(tag);
			}

			int articleCount = random.nextInt(5) + 5;
			for (int j = 1; j <= articleCount; j++) {
				String articleTitle = faker.book().title();
				
				String articleText = faker.lorem().paragraph(rnd.nextInt(10, 25));
				DateTime articleDate = new DateTime(2023, random.nextInt(12) + 1, random.nextInt(28) + 1,
						random.nextInt(24), random.nextInt(60));
				Article article = new Article(blog, articleTitle, articleText, articleDate);
				articleRepository.save(article);
			}
		}

		Comment adminArticle1Comment1 = new Comment(adminArticle1, admin, "I am commenting on my own article!",
				new DateTime(2023, 06, 06, 23, 2));
		commentRepository.save(adminArticle1Comment1);

		Comment adminArticle1Comment2 = new Comment(adminArticle1, user1, "Ughhhhh.",
				new DateTime(2023, 06, 07, 12, 0));
		commentRepository.save(adminArticle1Comment2);

		Comment adminArticle1Comment3 = new Comment(adminArticle1, admin, "I made yet another comment. :)",
				new DateTime(2023, 06, 07, 15, 55));
		commentRepository.save(adminArticle1Comment3);

		Comment adminArticle2Comment1 = new Comment(adminArticle2, user1, "Deeply disturbing.",
				new DateTime(2023, 06, 06, 23, 2));
		commentRepository.save(adminArticle2Comment1);

		Comment adminComment1Comment1 = new Comment(adminArticle1Comment1, user1, "That is pathetic.",
				new DateTime(2023, 06, 07, 12, 2));
		commentRepository.save(adminComment1Comment1);

		Comment adminComment1Comment2 = new Comment(adminComment1Comment1, admin, "you're banned",
				new DateTime(2023, 06, 07, 12, 3));
		commentRepository.save(adminComment1Comment2);

	}
}
