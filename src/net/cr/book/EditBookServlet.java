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
 * 			EditBookServlet : This servlet is to change the book details by librarian.
 * @author nagr0616
 *
 */
public class EditBookServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(EditBookServlet.class);
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		String userName = (String) session.getAttribute("Librarian");
		//Checking whether session already started or not. If it is new session then it is not valid flow.
		if(userName != null){
			String bookId = request.getParameter("bookId");
			String isbn = request.getParameter("isbn");
			String title = request.getParameter("title");
			String authors = request.getParameter("authors");
			String publisher = request.getParameter("publisher");
			String edition = request.getParameter("edition");

			//Creating object of BookBean class to invoke setters and getters of it.
			BookBean book = new BookBean();

			book.setBookId(bookId);
			book.setIsbn(isbn);
			book.setTitle(title);
			book.setAuthors(authors);
			book.setPublisher(publisher);
			book.setEdition(edition);

			//Invoking the update method of BookDAO [Data Access Object class] with the parameter of BookBean's object. 
			book = new BookDAO().updateBookDetails(book);

			//person object will have now updated information includes status value which is updated in PersonDAO class.
			if(book.isValid()){
				logger.debug(bookId + " : is edited succesesfully");
				response.sendRedirect("Librarian.jsp");
			}
		}
		else {
			// If the session is new then it'll be redirected to login.jsp page
			logger.error("Session is not created");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);  
		}
	}
}

