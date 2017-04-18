package net.cr.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * 			ConnectionCreator.java : Global class to provide connection between java and oracle database.
 * @author nagr0616
 *
 */
public class ConnectionCreator {
	Connection con;
	String url;
	private Logger logger = Logger.getLogger(ConnectionCreator.class);
	private static ConnectionCreator instance = new ConnectionCreator();

	//make the constructor private so that this class cannot be
	//instantiated
	private ConnectionCreator(){}

	//Get the only object available
	public static ConnectionCreator getInstance(){
		return instance;
	}
	public Connection createConnection(){
		String url = "jdbc:oracle:thin:@localhost:1521:XE"; 
		//The forName() method of Class class is used to register the driver class.
		//This method is used to dynamically load the driver class.
		try {

			Class.forName("oracle.jdbc.OracleDriver");

		}catch (ClassNotFoundException e) {
			//This catch will handle when there is wrong in class name.			    		  
			logger.error("Oracle class error : It is not found or misspelled class name");
		}

		//The getConnection() method of DriverManager class is used to 
		//establish connection with the database.
		try {

			con = DriverManager.getConnection(url,"system","netcracker");

		} catch (SQLException e) {

			//This catch will handle when there is anything wrong in connection, user name or password.
			logger.error("Error occured at connection creator: url or username or password may be incorrect");
		} 
		return con;
	}
}
