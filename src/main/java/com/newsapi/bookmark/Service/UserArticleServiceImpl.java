package com.newsapi.bookmark.Service;

import com.newsapi.bookmark.Model.Article;
import com.newsapi.bookmark.Model.UserArticles;
import com.newsapi.bookmark.Repository.ArticleRepository;
import com.newsapi.bookmark.Repository.UserArticleRepository;
import com.newsapi.bookmark.exceptions.ArticleAlreadyAddedException;
import com.newsapi.bookmark.exceptions.ArticleNotFoundException;
import com.newsapi.bookmark.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserArticleServiceImpl implements UserArticleService {
    @Autowired
    private UserArticleRepository userArticleRepository;
    @Autowired
    private ArticleRepository articleRepository;

//    Add Article
    public Article addArticle(Article article){
    	Example<Article> newArticle = Example.of(article);
    	Optional<?> foundArticle = articleRepository.findOne(newArticle);
    	if(foundArticle.isEmpty()) {
    		return articleRepository.save(article);
    	}
    	return (Article) foundArticle.get();
    }
    
//    Add UserArticle
//    Not used
    public UserArticles addUserArticle(UserArticles userArticle) {
    	List<Article> listOfArtticles = userArticle.getArticles().stream().map(article-> addArticle(article)).collect(Collectors.toList());
    	userArticle.setArticles(listOfArtticles);
    	return userArticleRepository.save(userArticle);
    }
//    Add User to User Articles
    public UserArticles addUserToUserArticles(String email) {
    	UserArticles user = new UserArticles();
    	user.setEmail(email);
    	return userArticleRepository.save(user);
    }
    
//    Get All Articles Of User
    public List<Article> getAllArticlesOfUser(String email){
    	UserArticles userArticle = userArticleRepository.findByEmail(email);
    	if(userArticle==null) {
    		userArticle = addUserToUserArticles(email);
    	}
    	return userArticle.getArticles();
    }
    
//    Get Particular Article Of User
    public Article getArticleOfUser(String email, String articleId) throws ArticleNotFoundException {
    	List<Article> allArticlesOfUser = getAllArticlesOfUser(email);
    	List<Article> foundArticle = allArticlesOfUser.stream().filter(article -> article.getId().equals(articleId)).collect(Collectors.toList());
    	if(foundArticle.size()<1) {
    		throw new ArticleNotFoundException("Article Not Found");
    	}
    	return foundArticle.get(0);
    }
    
//    Add Article Of User
    
    public boolean addArticleOfUser(String email,Article article) throws ArticleAlreadyAddedException {
    	UserArticles userArticles =  userArticleRepository.findByEmail(email);
//    	If no user exists ---->new user
    	if(userArticles==null) {
    		UserArticles newUserArticles = new UserArticles();
    		List<Article> articlesOfNewUser = new ArrayList<>();
    		articlesOfNewUser.add(addArticle(article));
    		newUserArticles.setEmail(email);
    		newUserArticles.setArticles(articlesOfNewUser);
    		userArticleRepository.save(newUserArticles);
    		return true;
    	}
//    	User Exists but checking if article is already bookmarked
    	List<Article> articlesList = userArticles.getArticles();
    	Article articleinDb = addArticle(article);
    	if(articlesList == null) {
    		List<Article> newArticleList = new ArrayList<>();
    		newArticleList.add(articleinDb);
    		userArticles.setArticles(newArticleList);
    		userArticleRepository.save(userArticles);
    	}
    	if(articlesList!=null) {
//    		Check if article is already bookmarked
    		for(Article a:articlesList) {
        		if(a.getId().equals(articleinDb.getId())) {
        			throw new ArticleAlreadyAddedException("Article is bookmarked already");
        		}
        	}
//        	User Exists but article not bookmarked
        	articlesList.add(articleinDb);
        	userArticles.setArticles(articlesList);
        	userArticleRepository.save(userArticles);
    	}

    	return true;
    }
    
//    Delete Article Of User
    public boolean deleteArticleOfUser(String email, String articleId) throws UserNotFoundException {
    	UserArticles userArticles =  userArticleRepository.findByEmail(email);
    	if(userArticles==null) {
    		throw new UserNotFoundException("User Not Found");
    	}
    	List<Article> articlesList = userArticles.getArticles();
    	articlesList = articlesList.stream().filter(article -> !article.getId().equals(articleId)).collect(Collectors.toList());
    	userArticles.setArticles(articlesList);
    	userArticleRepository.save(userArticles);
    	return true;
    }
//    Delete All Articles Of User
    public boolean deleteAllArticlesOfUser(String email) throws UserNotFoundException {
    	UserArticles userArticles =  userArticleRepository.findByEmail(email);
    	if(userArticles==null) {
    		throw new UserNotFoundException("User Not Found");
    	}
    	List<Article> articlesList = new ArrayList<>();
    	userArticles.setArticles(articlesList);
    	userArticleRepository.save(userArticles);
    	return true;    	
    }
}
