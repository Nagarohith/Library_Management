package net.cr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.cr.beans.BookBean;
import net.cr.connection.ConnectionCreator;

/**
 * 		BookDAO.java :  This Data Object Class is used to perform the major operations of BookBean class
 * 						This class contain the methods to handle single row of the database.  
 * @author nagr0616
 *
 */
public class BookDAO implements BookDAOIn {
	private Logger logger = Logger.getLogger(BookDAO.class);   
	// UpdateBookDetails : Method is to update the book details for selected book.
	@Override
	public BookBean updateBookDetails(BookBean bean) {
		//bean contains the information of the book need to be updated.
		Connection con = null;
		PreparedStatement stmt = null;
		String bookId = bean.getBookId();
		String isbn = bean.getIsbn();
		String title = bean.getTitle();
		String authors = bean.getAuthors();
		String publisher = bean.getPublisher();
		String edition = bean.getEdition();
		String query = "update book set isbn = ?, title = ?,authors= ?,publisher= ?,edition= ?  where bookId = ?";

		try{
			// Creating connection, statement for database operations.
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, isbn);
			stmt.setString(2, title);
			stmt.setString(3, authors);
			stmt.setString(4, publisher);
			stmt.setString(5, edition);
			stmt.setString(6, bookId);
			stmt.executeUpdate();
			logger.info(bookId + " : Updated details successfully");
			bean.setValid(true);

		}catch (SQLException e) {
			//Exception may be because of sql syntax.
			logger.error(bookId + "Error at updation of book details for the book ");
		}
		finally 
		{
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
				con = null;
			}
		}
		return bean;
	}

	// deleteBook : This method is used to delete the book which is contained in bean : book 
	@Override
	public BookBean deleteBook(BookBean book) {
		Connection con = null;
		PreparedStatement stmt = null;
		String bookId = book.getBookId();
		//This query contains the statement used to delete particular book.
		String query = "delete from book where bookId = ?";

		try{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, bookId);
			stmt.executeUpdate();
			logger.info(bookId + ": Deleted succesesfully");
			book.setValid(true);
		}
		catch(SQLException e){

			logger.error(bookId + " : Error in removing book from the database for the book ");
		}
		finally 
		{
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
				con = null;
			}
		}
		return book;
	}
	// insertBookDetails : This method is used to insert new book details into the database.
	@Override
	public BookBean insertBookDetails(BookBean bean) {
		Connection con = null;
		PreparedStatement stmt = null;
		String bookId = bean.getBookId();
		String isbn = bean.getIsbn();
		String title = bean.getTitle();
		String authors = bean.getAuthors();
		String publisher = bean.getPublisher();
		String edition = bean.getEdition();
		//Inserting multiple values into book table of the database.
		String query = "insert into book values(?,?,?,?,?,?)";

		try{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, bookId);
			stmt.setString(2, isbn);
			stmt.setString(3, title);
			stmt.setString(4, authors);
			stmt.setString(5, publisher);
			stmt.setString(6, edition);
			stmt.executeUpdate();
			logger.info(bookId + " : Inserted successfully");
			bean.setValid(true);

		}catch (SQLException e) {
			//Exception may be because of sql syntax.
			logger.error(bookId + "Error at Insertion of book details for the book ");

		}
		finally 
		{
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
				con = null;
			}
		}
		return bean;
	}

	// getEditBook : This method is used to edit the book details and it will display book details.
	@Override
	public BookBean getEditBook(BookBean bean) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String bookId = bean.getBookId();
		String query = "select isbn,title,authors,publisher,edition from book where bookId = ?";
		try{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, bookId);
			rs = stmt.executeQuery();
			if(rs.next()){
				bean.setIsbn(rs.getString("isbn"));
				bean.setTitle(rs.getString("title"));
				bean.setAuthors(rs.getString("authors"));
				bean.setPublisher(rs.getString("publisher"));
				bean.setEdition(rs.getString("edition"));
				logger.info(bookId + " : edited success");
			}
		}catch(SQLException e){
			logger.error("Error in retrieval of books for bookid : " + bookId);
		}
		finally 
		{
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
				con = null;
			}
		}
		return bean;
	}
	@Override
	public List<BookBean> getBookForId(String bookID) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String bookId = bookID;
		List<BookBean> list = new ArrayList<BookBean>();
		String query = "select * from book where bookId = ?";
		try{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, bookId);
			rs = stmt.executeQuery();
			BookBean book = new BookBean();
			if(rs.next()){
				book.setIsbn(rs.getString("isbn"));
				book.setTitle(rs.getString("title"));
				book.setAuthors(rs.getString("authors"));
				book.setPublisher(rs.getString("publisher"));
				book.setEdition(rs.getString("edition"));
			}
			else {
				logger.info(bookId + " : Deleted already");
			}
		}catch(SQLException e){
			logger.error("Error in retrieval of books for bookid : " + bookId);
		}
		finally 
		{
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
				con = null;
			}
		}
		return list;
	}	
}
