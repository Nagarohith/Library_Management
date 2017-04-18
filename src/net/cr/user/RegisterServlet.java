package net.cr.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.cr.beans.PersonBean;
import net.cr.dao.PersonDAO;

/**
 * 			RegisterServlet.java : This servlet is used to register new users(Users or Librarians)
 * 								   to the system.
 * @author nagr0616
 *
 */
public class RegisterServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(RegisterServlet.class);
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//All the details are from Register.jsp for the login into the system.
		String fname = request.getParameter("fname");
		String lname =request.getParameter("lname");
		String username =request.getParameter("username");
		String password =request.getParameter("password");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		//The role value will be 1 for the user type : User
		String role = request.getParameter("role");
		//Creating objects for the PersonBean class to invoke its getters and setters.
		PersonBean person = new PersonBean();
		person.setFname(fname);
		person.setLname(lname);
		person.setUsername(username);
		person.setPassword(password);
		person.setPhone(phone);
		person.setEmail(email);
		person.setRole(role);
		//Creating instance of PersonDAO of PersonBean type to register the details of the user. 

		person = new PersonDAO().register(person);

		PrintWriter out = response.getWriter();

		//Checking the status of registration.
		if(person.getStatus()){
			logger.info("Registered succesefully" + username);
			if(Integer.parseInt(role)==1){
				response.sendRedirect("index.jsp");
			}else {
				response.sendRedirect("Admin.jsp");
			}
		}
		// This block checks whether username is duplicate or not
		else if(person.isDuplicate())
		{
			logger.debug("Registration unsuccessful to the user " + username);
			out.println("<html>");
			out.println("<head>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\"/>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />");
			out.println("</head>");
			out.println("<body>");
			out.println("<center><h1>Username already present : Please select other user name </h1><br>");
			out.println("<p class=\"button\">"); 
			if(Integer.parseInt(role)==1)
				out.println("<a href=\"register.jsp?role=1\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\">Ok</a> <br><br>");
			else
				out.println("<a href=\"register.jsp?role=2\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\">Ok</a> <br><br>");

			out.println("</body>");
			out.println("</html>");
		}
		else{
			//This block indicates the details are not proper.
			logger.debug("Error in registering the user");
			out.println("<html>");
			out.println("<head>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\"/>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />");
			out.println("</head>");
			out.println("<body>");
			out.println("<center><h1>Registration unsuccesefull: please Enter proper details </h1><br>");
			out.println("<p class=\"button\">"); 
			if(Integer.parseInt(role)==1)
				out.println("<a href=\"register.jsp?role=1\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\">Ok</a> <br><br>");
			else
				out.println("<a href=\"register.jsp?role=2\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\">Ok</a> <br><br>");


			out.println("</body>");
			out.println("</html>");
		}
	}
}
