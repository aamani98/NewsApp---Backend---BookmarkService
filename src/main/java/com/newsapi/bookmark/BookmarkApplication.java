package com.newsapi.bookmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.newsapi.bookmark.Filter.JWTFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EntityScan
@EnableMongoRepositories
@SpringBootApplication
@EnableSwagger2
public class BookmarkApplication {

	@Bean
	public FilterRegistrationBean<JWTFilter> jwtFilter() {
		FilterRegistrationBean<JWTFilter> filterBean = new FilterRegistrationBean<JWTFilter>();
		filterBean.setFilter(new JWTFilter());
		filterBean.addUrlPatterns("/api/*");
		return filterBean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BookmarkApplication.class, args);
	}

}
