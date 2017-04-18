<%@ page import="org.apache.log4j.Logger"%>
<html>
<head>
<title>User Page</title>
<style>
.buttonlink {
	background-color: #20B2AA;
	border: solid;
	color: white;
	padding: 15px 300px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
	width: 20%
}
</style>
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />

</head>

<body>
	<form>
		<center>
		
		<%
		response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
		Logger logger = Logger.getLogger(this.getClass());
		//Session should not be old.
		session=request.getSession(false);
		String username = (String) session.getAttribute("Librarian");
		// checks whether seession is cerated or not.
		if(username!=null){
		out.println("<h1 style=\"font-size: 40px;\"> " +username + "'s Library page");
		%>
			<hr>
			<br> <a href="AddBook.jsp" class="buttonlink">Add Book</a> <br>
			<a href="BookDetails.jsp" class="buttonlink">Search Book</a> <br>
			<a href="IssueBook.jsp" class="buttonlink">Issue Book</a> <br> <a
				href="ReturnBook.jsp" class="buttonlink">Return Book</a> <br> <a
				href="Logout" class="buttonlink">Logout</a> <br>
			<br>
			<%
        }
        else{
        	//Session is not present.
        	logger.debug("Session is not created");
	   		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
	   		rd.forward(request, response);  
    	}
        %>
		</center>
	</form>
</body>
</html>