package net.cr.book;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.cr.beans.BookBean;
import net.cr.dao.BookDAO;
import net.cr.user.LoginServlet;

/**
 * 		DeleteBookServlet : This servlet is used to delete the book from the database and done by librarian. 
 * @author nagr0616
 *
 */
public class DeleteBookServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(DeleteBookServlet.class);
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String bookId = request.getParameter("bookId");
		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("Librarian");
		//Checking whether session already started or not. If it is new session then it is not valid flow.
		if(userName != null){
			BookBean book = new BookBean();
			book.setBookId(bookId);
			book = new BookDAO().deleteBook(book);
			if(book.isValid()){
				logger.info(bookId + " : Book Is deleted from library");
				response.sendRedirect("BookDetails.jsp");
			}
		}
		else {
			//If the session is created as new then it'll be redirected to login.jsp page.
			logger.error("Session is not created");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);  
		}
	}
}
