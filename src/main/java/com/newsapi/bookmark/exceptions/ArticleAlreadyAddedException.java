package com.newsapi.bookmark.exceptions;

public class ArticleAlreadyAddedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ArticleAlreadyAddedException(String message) {
		super(message);
	}
}
