package net.cr.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.cr.beans.LibraryBean;
import net.cr.dao.LibraryDAO;

/**
 * 			IssueBookServlet.java : Servlet which is used to issue books to the authenticated user.  
 * @author nagr0616
 *
 */
public class IssueBookServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(IssueBookServlet.class);
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String userName = (String)session.getAttribute("Librarian");
		if(userName!=null){
			PrintWriter out = response.getWriter();
			String bookId = request.getParameter("bookId");
			String username = request.getParameter("username");
			String issueDate = request.getParameter("issueDate");
			String dueDate = request.getParameter("dueDate");
			// Date : Built in class under java.util package to generate current date and time.
			Date is = new Date();
			Date du = new Date();

			// DateFormat : Built in class under java.text to format the date which is specified
			// in the constructor of built in class java.text.SimpleDateFormat
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {

				is = (Date)formatter.parse(issueDate);
				du = (Date)formatter.parse(dueDate);

			} catch (ParseException e) {

				logger.error("Issue Book : Date format is in correct please change it");

			} 
			//Converting the date format which supports in database.
			// Example : 1/jan/2016
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
			String date1 = sdf.format(is);
			String date2 = sdf.format(du);
			LibraryBean bean = new LibraryBean();
			bean.setBookId(bookId);
			bean.setUsername(username);
			bean.setIssueDate(date1);
			bean.setDueDate(date2);
			bean = new LibraryDAO().issueBook(bean);
			if(bean.isAvailable()){
				if(bean.isStatus()){
					logger.info("Insertion success for book id " + bookId + " and for username " + username);
					response.sendRedirect("IssueBook.jsp?message=success");
				}
				else{
					//this block notifies that book is not present in library to issue.
					out.println("<html>");
					out.println("<head>");
					out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\"/>");
					out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />");
					out.println("</head>");
					out.println("<body>");
					out.println("<center><h1> The BookId you given is either dispatched or not present in library " + bookId + "</h1><br>"  );
					out.println("<p class=\"button\">"); 
					out.println("<a href=\"IssueBook.jsp\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\">Ok</a> <br><br>");
					out.println("</body>");
					out.println("</html>");
					logger.debug("Insertion Failure for book id " + bookId + " and for username " + username);
				}
			}
			else if(bean.isUserInvalid())
			{
				//This block of code indicates if the user is not valid.
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\"/>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />");
				out.println("</head>");
				out.println("<body>");
				out.println("<center><h1> " + username + " : Is not a valid user</h1><br>"  );
				out.println("<p class=\"button\">"); 
				out.println("<a href=\"IssueBook.jsp\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\">Ok</a> <br><br>");
				out.println("</body>");
				out.println("</html>");
				logger.debug(username + " : Is not a registered user");
			}
			else
			{
				// This block indicates that bookId is not valid or not present in library.
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\"/>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />");
				out.println("</head>");
				out.println("<body>");
				out.println("<center><h1> " + bookId + " : Is either not a valid or not present in library</h1><br>"  );
				out.println("<p class=\"button\">"); 
				out.println("<a href=\"IssueBook.jsp\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\">Ok</a> <br><br>");
				out.println("</body>");
				out.println("</html>");
				logger.debug(username + " : Is not a registered user");
			}
		}
		else{
			//If the session is created as new then it'll be redirected to login.jsp page.
			logger.error("Session is not created");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);  
		}
	}
}
