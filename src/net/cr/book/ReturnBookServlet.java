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
 * 			ReturnBookServlet.java : This servlet handles returns of book or taking book from user by librarian.
 * @author nagr0616
 *
 */
public class ReturnBookServlet extends HttpServlet {
	
	private Logger logger = Logger.getLogger(ReturnBookServlet.class);
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("Librarian");
		if(userName!=null){
			PrintWriter out = response.getWriter();
			// Date : Built-in class in java.util package to generate current date of the system. 
			// Here it indicates that user is returning the book on this date.
			Date rs = new Date();
			String bookId = request.getParameter("bookId");
			String username = request.getParameter("username");
			String returnDate = request.getParameter("returnDate");
			//This class DateFormat is built in class under java.text package 
			// to format the date in user required format .
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			try {
				
				rs = (Date)formatter.parse(returnDate);
			
			} catch (ParseException e) {
			
				logger.error("Date format is in correct please change it");
				
			} 
			//Converts the data to the format which fits into the database.
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
			String date1 = sdf.format(rs);
			//Creating the LibraryBean object to invoke its setters and getters which helps in return book operations.
	        LibraryBean bean = new LibraryBean();
			bean.setBookId(bookId);
			bean.setUsername(username);
			bean.setReturnDate(date1);
			//Creating object of LibraryDAO to call the function returnBook.
			bean = new LibraryDAO().returnBook(bean);
			//Checks whether book is taken from user or not.
			if(bean.isRemoveBook()){
					logger.info(bookId + " : Deletion success for book id  and for username " + username);
					response.sendRedirect("ReturnBook.jsp?message=Deleted");
			}
			else{
				//This method handles when either bookId or username or incorrect and they are not belong to the system.
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\"/>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />");
				out.println("</head>");
				out.println("<body>");
					out.println("<center><h1> The BookId or username are  not valid " + bookId + "</h1><br>"  );
						out.println("<p class=\"button\">"); 
	                       out.println("<a href=\"ReturnBook.jsp\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\">Ok</a> <br><br>");
				    out.println("</body>");
				out.println("</html>");
				logger.debug("Deletion Failure for book id " + bookId + " and for username " + username);
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