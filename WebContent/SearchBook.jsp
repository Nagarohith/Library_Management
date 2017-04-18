<%@page import="net.cr.beans.BookBean"%>
<%@page import="net.cr.beans.BooksBean"%>
<%@page import="net.cr.dao.BookDAO"%>
<%@page import="net.cr.dao.BooksDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
	background-color: #f2f2f2
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
</style>
</head>
<body>
	<form action="SearchBook.jsp">
		<center>
			<%	
				response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
				response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
				response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
				response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
				Logger logger = Logger.getLogger(this.getClass());
				// Session should not be created here.
				session = request.getSession(false);
				String username = (String) session.getAttribute("User");
				// Session should be created during login 
				if(username != null){
					 
					BookBean book = new BookBean();
					logger.debug(username + " : User's book details ");
					out.println("<h1> " + username + "</h1>");
					out.println("<br><hr>");
					book.setBookUser(username);
					BooksBean booksBean = new BooksBean();
					List<BookBean> list = new ArrayList<BookBean>();
					list = new BooksDAO().getUserBooks(book);
					if(book.isValid()){
						List<BookBean> books = booksBean.getUserBooks();
						out.println("<h2 style=\"color:blue;\">Book(s) found</h2><br>");
		%>
			<!-- Print start of table and column headers -->
			<table>
				<tr>
					<th>BookId</th>
					<th>Title</th>
					<th>Publisher</th>
					<th>Edition</th>
					<th>IssueDate</th>
					<th>DueDate</th>
				</tr>

				<%
				//Printing the table with the content of the database by array lists.
					for(int i=0;i<list.size();i++){
						out.println("<tr>");
						out.println("<td>" + list.get(i).getBookId() + "</td>");
						out.println("<td>" + list.get(i).getTitle() + "</td>");
						out.println("<td>" + list.get(i).getPublisher() + "</td>");
						out.println("<td>" + list.get(i).getEdition() + "</td>");
						out.println("<td>" + list.get(i).getIssueDate() + "</td>");
						out.println("<td>" + list.get(i).getDueDate() + "</td>");
						out.println("</tr>");
					}
					out.println("</table><br><br><br>");		
				}
				else {
					out.println("<h2 style=\"color:red;\">No Books found</h2><br><br><br> ");
				}

				
	%>
				<a href="Person.jsp" class="button">OK</a>
				<br>
				<br>
				<%
				}
				else{
					//If the session is not created then redirect to login.
					logger.debug("Session is not created");
    		   		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
    		   		rd.forward(request, response);
				}
		
		
		%>
				</center>
				</form>
</body>
</html>