package com.fdmgroup.blogplatform.security;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	
	@Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}

	@Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/h2/**"/*not suitable for production level app*/, "/search", "/faq", "WEB-INF/jsps/**", "/", "/**/*.png", "/register").permitAll()
				.antMatchers("/admin/**").hasRole("Admin")
				.anyRequest().authenticated()
				.and()
			.formLogin().loginPage("/login").permitAll()  // Spring won't inject it's own login page
				.defaultSuccessUrl("/", false)
				.failureUrl("/login")
				.and()
			.logout()
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.and()
			.csrf()
				.disable()	// So we don't have to add csrf to every form
			.httpBasic()
				.and()
			.headers().frameOptions().disable();	// Allows H2 to render correctly
	}
	
}
