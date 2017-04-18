package net.cr.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.cr.beans.BookBean;
import net.cr.dao.BookDAO;
import net.cr.dao.BooksDAO;

public class TestBookDAO {
	@Test
	public void testAddBook(){
		BookBean book = new BookBean();
		BookDAO bookDao = new BookDAO();
		BooksDAO booksDao = new BooksDAO();
		book.setBookId("B012324");
		book.setIsbn("9865743215689");
		book.setTitle("Programming in C");
		book.setAuthors("Balaguru Swamy");
		book.setPublisher("Subhas");
		book.setEdition("2010");
		bookDao.insertBookDetails(book);

		List<BookBean> list = new ArrayList<BookBean>();
		list = booksDao.getAllBooks(book.getBookId());
		
		assertEquals(book.getBookId(),list.get(0).getBookId());
	}
	@Test
	public void testDeleteBook(){
		BookBean book = new BookBean();
		BookDAO bookDao = new BookDAO();
		book.setBookId("B012324");

		book = bookDao.deleteBook(book);
		List<BookBean> list = new ArrayList<BookBean>();
		list = bookDao.getBookForId(book.getBookId());
		assertTrue(list.size() == 0);
		
	}
}
