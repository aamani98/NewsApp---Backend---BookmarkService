package com.newsapi.bookmark.Service;

import java.util.List;


import com.newsapi.bookmark.Model.Article;
import com.newsapi.bookmark.Model.UserArticles;
import com.newsapi.bookmark.exceptions.ArticleAlreadyAddedException;
import com.newsapi.bookmark.exceptions.ArticleNotFoundException;
import com.newsapi.bookmark.exceptions.UserNotFoundException;


public interface UserArticleService {
	
	public Article addArticle(Article article);
	
	public UserArticles addUserToUserArticles(String email);
	
	public List<Article> getAllArticlesOfUser(String email);
	
	public Article getArticleOfUser(String email, String articleId) throws ArticleNotFoundException;
	
	public boolean addArticleOfUser(String email,Article article) throws ArticleAlreadyAddedException;
	
	public boolean deleteArticleOfUser(String email, String articleId) throws UserNotFoundException;
	
	public boolean deleteAllArticlesOfUser(String email) throws UserNotFoundException;
}
