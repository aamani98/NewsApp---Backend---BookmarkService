package com.newsapi.bookmark.Repository;

import com.newsapi.bookmark.Model.Article;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article,String> {

	public Article findByTitle(String title);
	
}
