package net.cr.dao;

import java.util.List;

import net.cr.beans.BookBean;

/**
 * 			BookDAOIn.java : Interface which has the method declarations and must be implemented by the class 
 * 							 which implement this interface.
 * @author nagr0616
 *
 */
public interface BookDAOIn {
	 public BookBean updateBookDetails(BookBean bean);
	 public BookBean deleteBook(BookBean book);
	 public BookBean insertBookDetails(BookBean book);
	 public BookBean getEditBook(BookBean bean);
	 public List<BookBean> getBookForId(String bookID);
	
}
