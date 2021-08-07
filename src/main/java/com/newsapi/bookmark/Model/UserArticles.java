package com.newsapi.bookmark.Model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class UserArticles {
    private String id;
    private String email;
    @DBRef
    private List<Article> articles;
    
	public UserArticles(String id, String email, List<Article> articles) {
		super();
		this.id = id;
		this.email = email;
		this.articles = articles;
	}

	public UserArticles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	@Override
	public String toString() {
		return "UserArticles [id=" + id + ", email=" + email + ", articles=" + articles + "]";
	}
    
	
    
    
}
