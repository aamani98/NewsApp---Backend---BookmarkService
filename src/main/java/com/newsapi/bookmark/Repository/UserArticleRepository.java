package com.newsapi.bookmark.Repository;

import com.newsapi.bookmark.Model.UserArticles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserArticleRepository extends MongoRepository<UserArticles,String> {
	
	public UserArticles findByEmail(String email);
}
