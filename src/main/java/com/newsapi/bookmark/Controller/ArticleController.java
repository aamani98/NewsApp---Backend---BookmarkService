package com.newsapi.bookmark.Controller;

import com.newsapi.bookmark.Model.Article;
import com.newsapi.bookmark.Service.UserArticleService;
import com.newsapi.bookmark.exceptions.ArticleAlreadyAddedException;
import com.newsapi.bookmark.exceptions.ArticleNotFoundException;
import com.newsapi.bookmark.exceptions.UserNotFoundException;

import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private UserArticleService userArticleService;
    
//    ************Main End Points*************

    @PostMapping("/bookmarks")
    public ResponseEntity<?> addArticleOfUser(@RequestAttribute("user") Claims email, @RequestBody Article article){
    	boolean added;
    	Map<String,String> response = new HashMap<>();
    	try {
			added = userArticleService.addArticleOfUser(email.getSubject(), article);
		} catch (ArticleAlreadyAddedException e) {
			response.put("message", "Article Already Bookmarked");
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
    	if(added) {
    		response.put("message", "Article Bookmarked");
    		return new ResponseEntity<>(response,HttpStatus.CREATED);
    	}
    	response.put("message", "Cannot Bookmark Article");
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/bookmarks")
    public ResponseEntity<?> getAllArticlesOfUser(@RequestAttribute("user") Claims email){
    	List<Article> allArticles = userArticleService.getAllArticlesOfUser(email.getSubject());
    	if(allArticles.size()==0) {
    		Map<String,String> response = new HashMap<>();
    		response.put("message","No Bookmarked Articles to display");
    		return new ResponseEntity<>(response,HttpStatus.OK);
    	}
    	Map<String,List<Article>> response = new HashMap<>();
    	response.put("bookmark", allArticles);
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @GetMapping("/bookmarks/{articleId}")
    public ResponseEntity<?> getArticleOfUser(@RequestAttribute("user") Claims email,@PathVariable("articleId") String articleId){
    	Article article;
    	try {
    		article = userArticleService.getArticleOfUser(email.getSubject(), articleId);
    	}
    	catch(ArticleNotFoundException e ) {
    		Map<String,String> response = new HashMap<>();
    		response.put("message","Article Not Found");
    		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    	}
    	Map<String,Article> response = new HashMap<>();
    	response.put("bookmark", article);
    	return new ResponseEntity<>(response,HttpStatus.OK);
    }
    
    @DeleteMapping("/bookmarks/{articleId}")
    public ResponseEntity<?> deleteArticleOfUser(@RequestAttribute("user") Claims email,@PathVariable("articleId") String articleId){
    	boolean deleted;
    	Map<String,String> response = new HashMap<>();
		try {
			deleted = userArticleService.deleteArticleOfUser(email.getSubject(), articleId);
		} catch (UserNotFoundException e) {
			response.put("message","User Not Found");
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
    	if(deleted) {
    		response.put("message","Article Deleted");
    		return new ResponseEntity<>(response,HttpStatus.OK);
    	}
    	response.put("message","Cannot Delete");
    	return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @DeleteMapping("/bookmarks")
    public ResponseEntity<?> deleteAllArticlesOfUser(@RequestAttribute("user") Claims email){
    	boolean deleted;
    	Map<String,String> response = new HashMap<>();
		try {
			deleted = userArticleService.deleteAllArticlesOfUser(email.getSubject());
		} catch (UserNotFoundException e) {
			response.put("message","User Not Found");
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
    	if(deleted) {
    		response.put("message","All Articles Deleted");
    		return new ResponseEntity<>(response,HttpStatus.OK);
    	}
    	response.put("message","Cannot Delete");
    	return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
