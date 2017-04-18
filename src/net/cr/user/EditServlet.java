package net.cr.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.cr.beans.PersonBean;
import net.cr.dao.PersonDAO;

/**
 * 			EditServlet.java : This servlet is used to edit the details of the person by the same user. 
 * @author nagr0616
 *
 */
public class EditServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(EditServlet.class);
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Checking the flow is proper or not
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("User");
		if(userName!=null){
			// Getting all the details from PersonProfile.jsp file through post method.
			String username = request.getParameter("username");
			String fname = request.getParameter("fname");
			String lname = request.getParameter("lname");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");

			//Creating object of PersonBean class to invoke setters and getters of it.
			PersonBean person = new PersonBean();

			person.setUsername(username);
			person.setFname(fname);
			person.setLname(lname);
			person.setPhone(phone);
			person.setEmail(email);

			//Invoking the update method of PersonDAO [Data Access Object class] with the parameter of PersonBean's object. 
			person = new PersonDAO().update(person);

			//person object will have now updated information includes status value which is updated in PersonDAO class.
			if(person.getStatus()){
				logger.info(username + " : Details are edited");
				response.sendRedirect("Person.jsp");

			}
		}
		else{
			// If the control flow is improper than it will be redirected to login.jsp
			logger.error("Session is not created");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);  
		}
	}


}
