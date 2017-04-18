<%@page import="net.cr.beans.BookBean"%>
<%@page import="net.cr.beans.BooksBean"%>
<%@page import="net.cr.dao.BookDAO"%>
<%@page import="net.cr.dao.BooksDAO"%>
<%@page import="java.util.List"%>
<%@ page import="org.apache.log4j.Logger"%>
<html>
<head>
<title>Book Details</title>

<style>
table {
	border: 1px solid black;
	width: 50%;
	background-color: #ffffff
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #e9e9e9
}

th {
	background-color: #20B2AA;
	color: white;
}

.button {
	background-color: #20B2AA;
	border: none;
	color: white;
	padding: 5px 1px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	width: 10%
}

a {
	background-color: #20B2AA;
	border: solid;
	color: white;
	padding: 10px 30px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	margin: 4px 2px;
	cursor: pointer;
	width: 20%
}

.submit {
	background-color: #20B2AA;
	border: solid;
	color: white;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	margin: 4px 2px;
	padding: 10px 30px;
}
</style>
</head>
<body>
	<form action="BookDetails.jsp" method="post">

		<center>
			<h1>Search Book</h1>
			<br>
			<br> Enter the Book Name to search : <input type="text"
				name="bookName" required><br> <input type="submit"
				class="submit" value="Search">
			<%	
				response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
				response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
				response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
				response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
				Logger logger = Logger.getLogger(this.getClass());
				// Accessing the old session.
				session = request.getSession(false);
				String userName = (String) session.getAttribute("Librarian");
				String bookName = request.getParameter("bookName");
				//Session should not be new
				if(userName != null){
					//Bookname should not be empty to display books.
					if(bookName!=null && bookName!=""){
						BooksBean booksBean = new BooksBean();
						BookBean book = new BookBean();
						book.setTitle(bookName);							
						booksBean = new BooksDAO().getSearchBooks(book);   
		
						if(book.isValid()){
							List<BookBean> books = booksBean.getBooks();
							out.println("<h2 style=\"color:blue;\">Book(s) found</h2><br>");
					
		%>
			<!-- Print start of table and column headers -->
			<table>
				<tr>
					<th>BookId</th>
					<th>ISBN</th>
					<th>Title</th>
					<th>Authors</th>
					<th>Publisher</th>
					<th>Edition</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>

				<%
				//Printing the table with the content of the database by array lists.
				for(int i=0;i<books.size();i++){
					out.println("<tr>");
					out.println("<td>" + books.get(i).getBookId() + "</td>");
					out.println("<td>" + books.get(i).getIsbn() + "</td>");
					out.println("<td>" + books.get(i).getTitle() + "</td>");
					out.println("<td>" + books.get(i).getAuthors() + "</td>");
					out.println("<td>" + books.get(i).getPublisher() + "</td>");
					out.println("<td>" + books.get(i).getEdition() + "</td>");
					out.println("<td>");
					out.println("<a href= BookEdit.jsp?bookId=" + books.get(i).getBookId() +">Edit</a>");
					out.println("</td>");
					out.println("<td>");
					out.println("<a href= DeleteBook?bookId=" + books.get(i).getBookId()+">Delete</a>");
					
					out.println("</td>");
					out.println("</tr>");
				}
			
			out.println("</table><br><br><br>");
			
				}
				else {
					out.println("<h2 style=\"color:red;\">No Books found</h2><br><br><br> ");
				}

				}
				}else{
					//Session should be old if not redirected to login. 
					logger.debug("Session is not created");
					RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
					rd.forward(request, response);  
				}
%>
				</center>
				</form>
</body>
</html>