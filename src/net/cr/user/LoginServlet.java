package net.cr.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.cr.beans.PersonBean;
import net.cr.dao.PersonDAO;

/**
 * 			LoginServlet.java : This servlet redirects to different pages based on their role.
 * 								 which is decided by role column of database table.
 * @author nagr0616
 *
 */
public class LoginServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(LoginServlet.class);
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;

		//Getting all the details from Login.jsp file through get method for login authentication.
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		logger.info("For New Session Login model is created");

		PersonBean personRole = new PersonBean();
		personRole.setUsername(username);
		personRole = new PersonDAO().getPersonRole(personRole);
		String role = (String) personRole.getRole();
		if(role!=null){
			int r = Integer.parseInt(role);
			logger.debug("Username : " + username + " and Role : " + r);

			//Creating object of PersonBean class to invoke setters and getters of it.
			PersonBean person = new PersonBean();
			person.setUsername(username);
			person.setPassword(password);
			logger.info(username + " is logged in as normal user");
			//Invoking the login method of PersonDAO [Data Access Object class] with the parameter of PersonBean's object.
			person = new PersonDAO().login(person);

			//person object will have now updated information includes status value which is updated in PersonDAO class.
			if(person.getStatus()){
				logger.info("Login succesefull for the user"+username);
				// If the value of r is 1 then the user is ordinary user.
				if(r==1){
					// Creating new session
					session=request.getSession();
					session.setAttribute("User", username);  
					logger.info(username + " is logged in as normal user");
					response.sendRedirect("Person.jsp");
				}
				// If the value of r is 2 then the user is librarian
				else if(r==2){
					// Creating new session
					session=request.getSession();  
					session.setAttribute("Librarian",username);  
					logger.info(username + " is logged in as Librarian");
					response.sendRedirect("Librarian.jsp");	
				}
				// Role is admin
				else{
					// Creating new session
					session=request.getSession();  
					session.setAttribute("administrator",username);  
					logger.info(username + " is logged in as administrator");
					response.sendRedirect("Admin.jsp");
				}
			}
			else
			{
				logger.debug(username + "tried to login to the system");
				response.sendRedirect("login.jsp?message=invalid");
			}
		}
		else
		{	
			logger.debug(username + " : Invalid user");
			response.sendRedirect("login.jsp?message=invalid");
		}
	}
}
