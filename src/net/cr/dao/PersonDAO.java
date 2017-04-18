package net.cr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.cr.beans.PersonBean;
import net.cr.connection.ConnectionCreator;

/**
 * 		PersonDAO.java : This Data Access Object class has methods to do operations on person.
 * @author nagr0616
 *
 */
public class PersonDAO implements PersonDAOIn{
	private Logger logger = Logger.getLogger(PersonDAO.class);
	//	register : this method is used to register the user to the system . 
	// User of may be librarian or common user.
	@Override
	public PersonBean register(PersonBean person) {
		Connection con = null;
		//Prepared statement used to perform jdbc operations 
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;

		//Getting all the details from PersonBean 
		String fname = person.getFname();
		String lname = person.getLname();   
		String username = person.getUsername();
		String password = person.getPassword();
		String phone = person.getPhone();
		String email = person.getEmail();
		String role = person.getRole();
		//registering the user based on his/her role.

		String query1 = "insert into Person values (?,?,?,?,?,?)";
		//Personroles table contain the columns of both person and roles table which acts as secondary columns to avoid data redundancy.  
		String query2 = "insert into personroles values (?,?)";

		try{

			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt1 = con.prepareStatement(query1);
			stmt1.setString(1, fname);
			stmt1.setString(2, lname);
			stmt1.setString(3, username);
			stmt1.setString(4, password);
			stmt1.setString(5, phone);
			stmt1.setString(6, email);
			int status1 = stmt1.executeUpdate();
			stmt2 = con.prepareStatement(query2);
			stmt2.setString(1, username);
			stmt2.setString(2, role);
			int status2 = stmt2.executeUpdate();
			if(status1 > 0 && status2 > 0){

				logger.debug(username + " : Record inserted succesesfully for the user ");
				//Setting the status of success in bean class. 
				person.setStatus(true);
			}
			else
			{
				logger.debug(username + " : Records insertion unsuccesesful for the user : ");
				//Setting the status of unsuccess of registration     
				person.setStatus(false);
			} 
		}
		catch (SQLException e) {
			if(e.getSQLState() == "23000"){
				logger.error("UserId already present please choose another.");
				person.setDuplicate(true);
			}
			//Handles when exception occurs at insertion or sql syntax.
			logger.error("Error in insertion of records into the table person : error may be in wild cards or syntax of sql quieries");
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
		return person;
	}

	// login : this method is used for authentication of the user.
	@Override
	public PersonBean login(PersonBean person){
		Connection con = null;
		ResultSet rs = null;

		PreparedStatement stmt = null;
		//Getting user name and password details from getters of PersonBean. 
		String username = person.getUsername();
		String password = person.getPassword();

		String query = "select * from Person where username= ? AND password= ? ";

		try {
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();	        

			// if user does not exist set the isValid variable to false
			if (!rs.isBeforeFirst()) 
			{
				logger.debug(username + " :  is  not a registered user! Please sign up first");
				person.setStatus(false);
			} 

			//if user exists set the isValid variable to true
			else 
			{
				//User is registered.
				person.setStatus(true);
			}
		} catch (SQLException e) {
			logger.error(username + "Error during login");

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

		return person;
	}

	// getRecords : This method returns the details of the particular user.
	@Override
	public PersonBean getRecords(PersonBean person){
		// bean contains username.
		Connection con = null;
		ResultSet rs = null;

		PreparedStatement stmt = null;
		//Getting username of the user from PersonBean
		String username = person.getUsername();
		//Retrieving all the details from person class based on the username.
		String query = "Select fname, lname, phoneno, email from person where username=?";
		try {

			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1,username);
			rs = stmt.executeQuery();
			if(rs.next()){
				logger.info(username + " : details are found");
				person.setFname(rs.getString(1));
				person.setLname(rs.getString(2));
				person.setPhone(rs.getString(3));
				person.setEmail(rs.getString(4));

			}
		} catch (SQLException e) {

			logger.error(username + " : Error at retrieving records");
		}
		finally{
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
				con = null;
			}
		}

		return person;
	}

	// update : this method is used to update the details of the person.
	@Override
	public PersonBean update(PersonBean person) {
		Connection con = null;

		PreparedStatement stmt = null;
		//Getting all the details from the person bean for updation or 
		//these are the changes need to be set to user's profile.
		String username = person.getUsername();
		String fname = person.getFname();
		String lname = person.getLname();
		String phone = person.getPhone();
		String email = person.getEmail();
		String query = "update person set fname = ?, lname = ?,phoneno= ?,email= ?  where username = ?";
		try{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, fname);
			stmt.setString(2, lname);
			stmt.setString(3, phone);
			stmt.setString(4, email);
			stmt.setString(5, username);
			int st = stmt.executeUpdate();
			if(st > 0){
				logger.info(username + " : Records updated succesefully");
				person.setStatus(true);
			}
			else{
				logger.debug(username + " : Records update unsuccess");
				person.setStatus(false);
			}

		}catch (SQLException e) {
			//Exception may be because of sql syntax.
			logger.error(username + " : Error at updation of person details for the user may be syntax problem of sql ");
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
		return person;
	}

	// getPersonRole : this method returns the role of the person 
	// roles may be user, librarian and administrator.
	@Override
	public PersonBean getPersonRole(PersonBean personRole) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null; 
		String username = personRole.getUsername();
		// personrole is the table contains the username and their role.
		String query = "select roleid from personroles where username = ? ";
		try{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			if(rs.next())
			{	
				String role = rs.getString("roleid");
				logger.info("Role of the " + username + " is " + role);
				// role will be set into the setter of person bean.
				personRole.setRole(role);
			}
		}catch (SQLException e) {
			//Exception may be because of sql syntax.
			logger.fatal(username + " : roles problem");
		}
		return personRole;
	}

	// removeUser : this method is invoked by admin to remove users from the system.
	// this method also ensures that user should not be taken any books from the libray.
	@Override
	public PersonBean removeUser(PersonBean bean) {
		Connection con = null;
		PreparedStatement stmt1 =null;
		PreparedStatement stmt2 =null;
		String username = bean.getUsername();
		String query1 = "delete from person where username=?";

		try{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt1 = con.prepareStatement(query1);
			stmt1.setString(1, username);
			int st = stmt1.executeUpdate();
			// This condition is used to check user contains any books or not.
			if(st > 0){
				logger.info(username + " : Removed user");
				bean.setStatus(true);
			}
			else {
				logger.debug(username + " : Can't remove user");
				bean.setStatus(false);
			}

		} catch(SQLException e){

			logger.error(username + " : Can't remove users for the user ");
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
	public List<PersonBean> getAllRecords(String username) {
		List<PersonBean> list = new ArrayList<PersonBean>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		String query = "select * from person where username = ?";
		try{
			ConnectionCreator connection = ConnectionCreator.getInstance();
			con = connection.createConnection();
			stmt = con.prepareStatement(query);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			PersonBean person = new PersonBean();
			if(rs.next()){
				person.setFname(rs.getString(1));
				person.setLname(rs.getString(2));
				person.setUsername(rs.getString(3));
				person.setPassword(rs.getString(4));
				person.setPhone(rs.getString(5));
				person.setEmail(rs.getString(6));
				list.add(person);
			}
			
			
		} catch(SQLException e){

				//Error in retrieval
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
