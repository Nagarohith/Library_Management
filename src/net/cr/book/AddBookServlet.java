package net.cr.book;

import java.io.IOException;
import java.io.PrintWriter;

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
 * 			AddBookServlet : This servlet performs adding new book to the database.
 * @author nagr0616
 *
 */
public class AddBookServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(AddBookServlet.class);
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if(session.getAttribute("Librarian")!=null){
			String bookId = request.getParameter("bookId");
			String isbn = request.getParameter("isbn");
			String title = request.getParameter("title");
			String authors = request.getParameter("authors");
			String publisher = request.getParameter("publisher");
			String edition = request.getParameter("edition");
			PrintWriter out = response.getWriter();

			//Creating object of BookBean class to invoke setters and getters of it.
			BookBean book = new BookBean();

			book.setBookId(bookId);
			book.setIsbn(isbn);
			book.setTitle(title);
			book.setAuthors(authors);
			book.setPublisher(publisher);
			book.setEdition(edition);

			//Invoking the update method of BookDAO [Data Access Object class] with the parameter of BookBean's object. 
			book = new BookDAO().insertBookDetails(book);

			//book object will have now updated information includes status value which is updated in BookDAO class.
			if(book.isValid()){
				logger.info(bookId + " : Book Inserted succesefully");
				response.sendRedirect("AddBook.jsp?message=success");
			}
			else{
				//In this else part it indicates that the entries which are there are not valid to the context.
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\"/>");
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\" />");
				out.println("</head>");
				out.println("<body>");
				logger.debug(bookId + " : Book Insertion Unsuccess");
				out.println("<form action = AddBook.jsp>");
				out.println("<center><h1>Error in entered details : Please Enter the details properly </h1><br>");
				out.println("<p class=\"button\">"); 
				out.println("<input type=\"submit\" value=\"OK\" style=\"font-size: 25px; background-color : #20B2AA boder: solid; color:blue; text-align: center;text-decoration: none;display: inline-block;margin: 4px 2px;	padding: 10px 30px;\"/></p>"); 
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");
			}
		}
		else{
			//If the session was not created earlier
			logger.error("Session is not created");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);  
		}
	}
}
