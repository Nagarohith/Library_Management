package net.cr.beans;

import java.util.List;
/**
 * 			BooksBean.java : This bean class contains the List collection to handle multiple row values or multiple column values.
 * @author nagr0616
 *
 */
public class BooksBean {
	private List<String> titles;
	private List<BookBean> books;
	private List<BookBean> userBooks;
	
	public List<String> getTitles() {
		return titles;
	}
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	public List<BookBean> getBooks() {
		return books;
	}
	public void setBooks(List<BookBean> books) {
		this.books = books;
	}
	public List<BookBean> getUserBooks() {
		return userBooks;
	}
	public void setUserBooks(List<BookBean> userBooks) {
		this.userBooks = userBooks;
	}
	
}
