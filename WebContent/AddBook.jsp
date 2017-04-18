<%@ page import="java.util.Random"%>
<%@ page import="org.apache.log4j.Logger"%>

<html>
<head>
<title>Add Book</title>
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	<div class="container">
		<header>
			<h1>Add Book Into Library</h1>

		</header>
		<section>
			<div id="container_demo">
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="AddBook" autocomplete="on" method="post">
							<%
								response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
								response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
								response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
								response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
   								Logger logger = Logger.getLogger(this.getClass());
   								// Random is built in class under java.util package is used to generate random number with bound.
   								int rand = new Random().nextInt(99999);
   								//BookId must start with 'B'.
   								String BID = "B"+rand;
   								session = request.getSession(false);
   								String userName = (String) session.getAttribute("Librarian");
   								if(userName!=null){
   							%>

							<h1>You Can Edit</h1>
							<input name="bookId" type="hidden" value=<%=BID %> />

							<p>
								<label for="isbn" class="uname"> ISBN </label> <input id="isbn"
									name="isbn" required="required" type="text" pattern="[0-9]{13}"
									title="Must contain 13 digits" placeholder="eg. 9898454545452" />
							</p>
							<p>
								<label for="title" class="uname"> Title </label> <input
									id="title" name="title" required="required" type="text"
									pattern="[A-Za-z ]{1,19}"
									title="Should contain letters and spaces"
									placeholder="eg. Java Programming" />
							</p>
							<p>
								<label for="authors" class="uname"> Authors </label> <input
									id="authors" name="authors" required="required" type="text"
									pattern="[A-Za-z, ]{1,100}"
									title="Authors name may contain spaces,comma and letters"
									placeholder="eg. xyz,abc" />
							</p>
							<p>
								<label for="publisher" class="uname"> Publishers </label> <input
									id="publisher" name="publisher" required="required" type="text"
									pattern="[A-Za-z, ]{1,40}"
									title="Publishers may contain spaces, comma and letters"
									placeholder="eg. abc publications" />
							</p>
							<p>
								<label for="edition" class="uname"> Edition </label> <input
									id="edition" name="edition" required="required" type="text"
									pattern="[0-9]{4}" title="edition may contain 4 digit year"
									placeholder="eg. 2016" />
							</p>
							<p class="login button">
								<input type="submit" value="Insert" />
							</p>
							<%
								String message = request.getParameter("message");
								if(message!=null)
								{
									out.println("<h3> Book Inserted succesefully. </h3>");
								}
   								}else{
   									//If the session was not created earlier
   									logger.debug("Session is valid");
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
