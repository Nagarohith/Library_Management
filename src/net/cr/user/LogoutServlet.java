package net.cr.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


/**
 * 			LogoutServlet.java : This servlet used to logout the user from the system.
 * 								 Here the session will get invalidate which is created
 * 								 during login.
 * @author nagr0616
 *
 */
public class LogoutServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(LogoutServlet.class);
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Getting the session here
		HttpSession session=request.getSession(false);
		logger.info("Logout succesefull");
		// Invalidating the session 
		session.invalidate();
		response.sendRedirect("login.jsp");

	}

}
