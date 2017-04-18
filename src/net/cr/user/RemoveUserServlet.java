package net.cr.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.cr.beans.PersonBean;
import net.cr.dao.PersonDAO;

/**
 * 			RemoveUserServlet : This servlet responsible to remove user from the system.
 * @author nagr0616
 *
 */
public class RemoveUserServlet extends HttpServlet implements Servlet {
	private Logger logger = Logger.getLogger(RemoveUserServlet.class);
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Creating session object for accessing old one.
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("administrator");
		if(userName!=null){
			String username = request.getParameter("username");
			PersonBean bean = new PersonBean();
			bean.setUsername(username);
			bean = new PersonDAO().removeUser(bean);
			//This condition contains true when removal of user is done.
			if(bean.getStatus()){
				response.sendRedirect("RemoveByAdmin.jsp");
			}			
			else{
				PrintWriter out = response.getWriter();
				logger.debug(username + " : Remove  unsuccessful to the user ");
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\"/>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />");
				out.println("</head>");
				out.println("<body>");
				out.println("<center><h1>Either user name not exist or user took some books from the library </h1><br>");
				out.println("<a href=\"RemoveByAdmin.jsp\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\">Ok</a> <br><br>");
				out.println("</center");
				out.println("</body>");
				out.println("</html>");	
			}
		}
		else{
			// If the control flow is improper than it will be redirected to login.jsp
			logger.debug("session is not created");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);  
		}
	}
}
