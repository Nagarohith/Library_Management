package net.cr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.cr.beans.BookBean;
import net.cr.beans.BooksBean;
import net.cr.connection.ConnectionCreator;

/**
 * 			BooksDAO.java : this Data Access Object class contain the methods to perform
 * 							on multiple rows or multiple columns of the database. 
 * @author nagr0616
 *
 */
public class BooksDAO implements BooksDAOIn{
	private Logger logger = Logger.getLogger(BooksDAO.class);
	// getBooks : This method return List of BooksBean type contains details of the book
	// which matches the pattern in the below.
	@Override
	public List<BookBean> getBooks(BookBean bean) {
		Connection currentCon = null;
		ResultSet rs = null; 
		List<BookBean> list=new ArrayList<BookBean>();
		//preparing some objects for connection 
		PreparedStatement stmt = null; 
		// Get book title which is entered by the visitor to search the book from database.
		String bookTitle = bean.getTitle(); 
		//Query to be executed to list the books with other attributes. 
		String searchQuery = "select title, authors, count(title) as available from Book where lower(title) like ? or upper(title) like ? or initcap(title) like ? group by Title, authors";
		// "System.out.println" prints in the console; Normally used to trace the process
		logger.debug(bookTitle + " : searching visitor ");          

		try 
		{
			//creating object to the class Connection Manager to get connection to the database.
			ConnectionCreator connection = ConnectionCreator.getInstance();
			currentCon = connection.createConnection();
			//The createStatement() method of Connection interface is used to create statement.
			//The object of statement is responsible to execute queries with the database.
			stmt=currentCon.prepareStatement(searchQuery);
			stmt.setString(1, bookTitle+"%");
			stmt.setString(2, bookTitle+"%");
			stmt.setString(3, bookTitle+"%");
			//The executeQuery() method of Statement interface is used to execute queries to the database.
			//This method returns the object of ResultSet that can be used
			//to get all the records of a table.
			rs = stmt.executeQuery();

			//To know whether number of records found or not

			// if user does not exist set the isValid variable to false
			if (!rs.isBeforeFirst()) 
			{
				bean.setValid(false);
				logger.debug(bookTitle + " : Searching failed for visitor that is book not found");
			} 
			else{
				//Loop through results of query.

				BookBean bean1 = null;
				while(rs.next()) 
				{
					bean1 = new BookBean();
					bean1.setTitle(rs.getString("title"));
					bean1.setAuthors(rs.getString("authors"));
					bean1.setCounts(rs.getInt("available"));
					list.add(bean1);
				}

				//if user exists set the isValid variable to true


				bean.setValid(true);
			} 
		} catch (SQLException e) {
			//SQL errors will be caught here
			logger.error(bookTitle + " : Error in retrieval of books may happened due wrong syntax in sql statements" + e);

		} 

		// Finally block will execute some important things with out caring exception.
		finally 
		{
			//By closing connection object statement and ResultSet will be closed automatically.
			//The close() method of Connection interface is used to close the connection.
			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {}
				currentCon = null;
			}
		}
		//Bean contains all the required information updated by using bean's setters.
		return list;
	}
	// getUserBooks : This method is used to retrieve all the books which are taken by particular user.
	@Override
	public List<BookBean> getUserBooks(BookBean bean) {
		//bean contains the details of the user whose book need to be displayed.
		Connection con = null;
		ResultSet rs = null;
		List<BookBean> list = new ArrayList<BookBean>();
		//BooksBean object is to store book details.
		PreparedStatement stmt = null;
		// username is unique and contained in bean.
		String username = bean.getBookUser();
		String query = "select library.BookId, title,publisher,edition, to_char(issuedate,'dd/mm/yyyy') as issue,to_char(duedate,'dd/mm/yyyy') as due from library, book where book.bookId = library.bookid and username = ?";
		try{

			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			rs = stmt.executeQuery();

			BookBean bean1 = null;
			// This condition checks whether there are multiple rows or not.
			if(rs.isBeforeFirst()){
				logger.info(username + " : searching for books");
				while(rs.next()){
					bean1 = new BookBean();
					bean1.setBookId(rs.getString("bookid"));
					bean1.setTitle(rs.getString("title"));
					bean1.setPublisher(rs.getString("publisher"));
					bean1.setEdition(rs.getString("edition"));
					bean1.setIssueDate(rs.getString("issue"));
					bean1.setDueDate(rs.getString("due"));
					list.add(bean1);
				}
				//Setting the boolean value to true says books found for the user.
				bean.setValid(true);
			}
			else
			{
				logger.debug(username + " : Don't have any books");
				bean.setValid(false);
			}
		}catch(SQLException e){

			//username is not a valid user in this system.
			logger.error(username + " : Error in selecting book details for the user.");
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

	// getSearchBooks : this method is used to display the books for particular particular book name.
	@Override
	public BooksBean getSearchBooks(BookBean bean) {
		Connection con = null;
		ResultSet rs = null;
		BooksBean books = new BooksBean();
		PreparedStatement stmt = null;
		List<BookBean> list = new ArrayList<BookBean>();
		String bookName = bean.getTitle();
		String query = "select * from Book where BookId not in (select Bookid from library) and lower(title) like ? or upper(title) like ? or initcap(title) like ?";
		try{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			// % is used in sql pattern matching it indicates 0 or n  characters. 
			stmt.setString(1, bookName+"%");
			stmt.setString(2, bookName+"%");
			stmt.setString(3, bookName+"%");
			rs = stmt.executeQuery();
			BookBean bean1 = null;
			if(rs.isBeforeFirst()){
				logger.info(bookName + " : search succesesful");
				while(rs.next()){
					bean1 = new BookBean();
					bean1.setBookId(rs.getString("bookid"));
					bean1.setIsbn(rs.getString("isbn"));
					bean1.setTitle(rs.getString("title"));
					bean1.setAuthors(rs.getString("authors"));
					bean1.setPublisher(rs.getString("publisher"));
					bean1.setEdition(rs.getString("edition"));
					//Storing all the details into one list to return.
					list.add(bean1);
				}
				books.setBooks(list);
				bean.setValid(true);
			}else{
				logger.debug(bookName + " : search unsucceseful");
				bean.setValid(false);

			}

		}catch(SQLException e){
			logger.error(bookName + " : Error in retrieval of books from the database ");
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
		return books;
	}
	@Override
	public List<BookBean> getAllBooks(String bookId) {
		
		Connection currentCon = null;
		ResultSet rs = null; 
		List<BookBean> list=new ArrayList<BookBean>();
		//preparing some objects for connection 
		PreparedStatement stmt = null; 
		//Query to be executed to list the books with other attributes. 
		String searchQuery = "select * from book where bookId = ?";
		
		try 
		{
			//creating object to the class Connection Manager to get connection to the database.
			ConnectionCreator connection = ConnectionCreator.getInstance();
			currentCon = connection.createConnection();
			//The createStatement() method of Connection interface is used to create statement.
			//The object of statement is responsible to execute queries with the database.
			stmt=currentCon.prepareStatement(searchQuery);
			stmt.setString(1, bookId);
			//The executeQuery() method of Statement interface is used to execute queries to the database.
			//This method returns the object of ResultSet that can be used
			//to get all the records of a table.
			rs = stmt.executeQuery();

			//To know whether number of records found or not

			// if user does not exist set the isValid variable to false
		
				BookBean bean = null;
				while(rs.next()) 
				{
					bean = new BookBean();
					bean.setBookId(rs.getString(1));
					bean.setIsbn(rs.getString(2));
					bean.setTitle(rs.getString(3));
					bean.setAuthors(rs.getString(4));
					bean.setPublisher(rs.getString(5));
					bean.setEdition(rs.getString(6));
					
				}
				list.add(bean);
				//if user exists set the isValid variable to true


		} catch (SQLException e) {
			//SQL errors will be caught here
			logger.error("Error in retrieval of books from database " + e);

		} 

		// Finally block will execute some important things with out caring exception.
		finally 
		{
			//By closing connection object statement and ResultSet will be closed automatically.
			//The close() method of Connection interface is used to close the connection.
			if (currentCon != null) {
				try {
					currentCon.close();
				} catch (Exception e) {}
				currentCon = null;
			}
		}
		//Bean contains all the required information updated by using bean's setters.
		return list;
	}
}
