package net.cr.dao;

import net.cr.beans.LibraryBean;

/**
 * 		LibraryDAOIn.java : This interface has methods related to library they are used 
 * 							issue and return books.
 * @author nagr0616
 *
 */
public interface LibraryDAOIn {
	public LibraryBean issueBook(LibraryBean bean);
	public LibraryBean returnBook(LibraryBean bean);
}
