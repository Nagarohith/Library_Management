package net.cr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import net.cr.beans.LibraryBean;
import net.cr.connection.ConnectionCreator;

/**
 * 			LibraryDAO.java : This Data Access Object class contains the methods to perform library operations 
 * 							  like issuing the book and return the book. 
 * @author nagr0616
 *
 */
public class LibraryDAO implements LibraryDAOIn {

	private Logger logger = Logger.getLogger(LibraryDAO.class);
	// issueBook : this method is used to issue book to the particular user and the user must be belong the system.
	@Override
	public LibraryBean issueBook(LibraryBean bean) {
		// bean contains the information of book that is bookId and username to whom the book is to be issued.
		Connection con = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		String bookId = bean.getBookId();
		String username = bean.getUsername();
		String issueDate = bean.getIssueDate();
		String dueDate = bean.getDueDate();
		String query1 = "insert into library values(?,?,?,?,?)";
		// This query represents the details of book which are not allocated to any user yet.
		String query2 = "select * from book where bookId not in (select bookId from library) and bookId = ?";
		try
		{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt2 = con.prepareStatement(query2);
			stmt2.setString(1, bookId);
			rs = stmt2.executeQuery();
			if(rs.next()){
				logger.info(username + ": Is valid to issue books");
				bean.setAvailable(true);
				stmt1 = con.prepareStatement(query1);
				stmt1.setString(1, bookId);
				stmt1.setString(2, username);
				stmt1.setString(3, issueDate);
				stmt1.setString(4,  dueDate);
				stmt1.setString(5, null);
				int st = stmt1.executeUpdate();
				if( st > 0){
					//States that book is present in library.
					bean.setStatus(true);
					//states that user is valid and can issue the books.
					bean.setUserInValid(false);
				}
			}
			else
			{
				bean.setAvailable(false);
				logger.debug(bookId + "is either taken or not found in library");
			}
		}catch(SQLException e)
		{
			//If any primary key violations then it will return the state 23000.
			if(e.getSQLState() == "23000")
			{
				bean.setUserInValid(true);
			}
			logger.fatal(bookId + " : Primary key violation");
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

	//	returnBook : This method is used to handle returning of the book by the user.
	// bookId must be present in database and username must be present.
	@Override
	public LibraryBean returnBook(LibraryBean bean) {
		//bean contains the details of both user and bookId
		Connection con = null;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		String bookId = bean.getBookId();
		String username = bean.getUsername();
		String returnDate = bean.getReturnDate();
		String query1 = "update library set returndate = ? where bookId = ?";
		String query2 = "delete from library where bookId = ? and username = ?";  
		try
		{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt1 = con.prepareStatement(query1);
			stmt1.setString(1, returnDate);
			stmt1.setString(2, bookId);
			int st = stmt1.executeUpdate();
			stmt2 = con.prepareStatement(query2);
			stmt2.setString(1, bookId);
			stmt2.setString(2, username);
			int st1 = stmt2.executeUpdate();
			if(st > 0 && st1 > 0)
			{
				// If the particular book is returned to the library then it will set as true
				bean.setRemoveBook(true);
				logger.info(bookId + " : is returned by : " + username);
			}
		}catch(SQLException e){
			System.out.println(bookId + " : Error at returnBook for the user " + username);
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

}
