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
			//Session should not create new.
			session=request.getSession(false);
			String username = (String) session.getAttribute("User");
			if (username == null ) {
		   		logger.debug("Error! Session has ended.  Please login.");
		   		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		   		rd.forward(request, response);
			}
			else{
		  		out.println("<h1 style=\"font-size: 40px;\"> " +username + "'s User page");
		  		
			%>
			<hr>
			<a href="PersonProfile.jsp" class="buttonlink">User Profile</a> <br>
			<br> <a href="SearchBook.jsp" class="buttonlink">Book
				Details</a> <br>
			<br> <a href="Logout" class="buttonlink">Logout</a> <br>
			<br>
			<%
        	}
        	%>
		</center>
	</form>
</body>
</html>