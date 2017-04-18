<%@ page import="net.cr.beans.BookBean"%>
<%@ page import="net.cr.dao.BookDAO"%>
<%@ page import="org.apache.log4j.Logger"%>

<html>
<head>
<title>Edit Books</title>
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />

</head>
<body>
	<div class="container">
		<header>
			<h1>Book Details</h1>

		</header>
		<section>
			<div id="container_demo">
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="EditBook" autocomplete="on">
							<% 
								response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
								response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
								response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
								response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
								Logger logger = Logger.getLogger(this.getClass()); 
                            	String bookId = request.getParameter("bookId");
                            	//Session should be created as new
                            	session = request.getSession(false);
                            	String userName = (String) session.getAttribute("Librarian");
                            	//Session must be new and bookId must not be null
                            	if(userName != null && bookId!=null){
                        		BookBean book = new BookBean();	
                        		book.setBookId(bookId);
                        		book = new BookDAO().getEditBook(book);
                        		String isbn = book.getIsbn();
                        		String title = book.getTitle();
                        		String authors = book.getAuthors();
                        		String publisher = book.getPublisher();
                        		String edition = book.getEdition();
                        		
                        		
                            	%>
							<input type="hidden" name="bookId" id="bookId"
								value="<%= bookId %>" />
							<!-- If we disabled then we can't send its value through get or post -->
							<h1>You Can Edit</h1>

							<p>
								<label for="isbn" class="uname"> ISBN </label> <input id="isbn"
									name="isbn" required="required" type="text" pattern="[0-9]{13}"
									title="Must contain 13 digits" placeholder="eg. 9898454545452"
									value="<%= isbn %>" />
							</p>
							<p>
								<label for="title" class="uname"> Title </label> <input
									id="title" name="title" required="required" type="text"
									pattern="[A-Za-z ]{1,19}"
									title="Should contain letters and spaces"
									placeholder="eg. Java Programming" value="<%= title %>" />
							</p>
							<p>
								<label for="authors" class="uname"> Authors </label> <input
									id="authors" name="authors" required="required" type="text"
									pattern="[A-Za-z, ]{1,100}"
									title="Authors name may contain spaces,comma and letters"
									placeholder="eg. xyz,abc" value="<%= authors %>" />
							</p>
							<p>
								<label for="publisher" class="uname"> Publishers </label> <input
									id="publisher" name="publisher" required="required" type="text"
									pattern="[A-Za-z, ]{1,40}"
									title="Publishers may contain spaces, comma and letters"
									placeholder="eg. abc publications" value="<%= publisher %>" />
							</p>
							<p>
								<label for="edition" class="uname"> Edition </label> <input
									id="edition" name="edition" required="required" type="text"
									pattern="[0-9]{4}" title="edition may contain 4 digit year"
									placeholder="eg. 2016" value="<%= edition %>" />
							</p>

							<p class="login button">
								<input type="submit" value="SAVE" />
							</p>
							<%
								}else
								{
									//If the session is new then it will be redirected to login.
									logger.debug("session is not created");
							   		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
							   		rd.forward(request, response); 
								}
							%>
						</form>
					</div>
				</div>
			</div>
		</section>
	</div>

</body>
</html>