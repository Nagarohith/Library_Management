package net.cr.dao;

import java.util.List;

import net.cr.beans.BookBean;
import net.cr.beans.BooksBean;

/**
 * 			BooksDAOIn.java : Interface which has the methods to perform multiple rows or multiple colums
 * 							  these are methods to be defined when ever any class is implemented this interface.
 * @author nagr0616
 *
 */
public interface BooksDAOIn {

	public List<BookBean> getBooks(BookBean bean);

	public List<BookBean> getUserBooks(BookBean bean);

	public BooksBean getSearchBooks(BookBean bean);

	public List<BookBean> getAllBooks(String bookId);

}
