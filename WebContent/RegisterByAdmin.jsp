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
	<form method="post">
		<center>
			<%
			Logger logger = Logger.getLogger(this.getClass());
			// New session couldn't be created here.
			session = request.getSession(false);
			String name = (String) session.getAttribute("administrator");
			//If session is created in login and name as administrator.
			if(name!=null){
				out.println("<h1 style=\"font-size: 40px;\"> Admin page</h1>");
			%>
			<hr>
			<a href="register.jsp" class="buttonlink">Add User</a> <br>
			<br> <input type="hidden" name="usertype" value="librarian">
			<a href="register.jsp?usertype=librarian" class="buttonlink">Add
				Librarian</a> <br>
			<br> <a href="logout" class="buttonlink">Logout</a> <br>
			<br>
			<% 
			}
			else {
				//If the session is not created redirects to login page.
    			logger.debug("session is not created");
		   		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		   		rd.forward(request, response);                        			
    		}
			
			%>
		</center>
	</form>
</body>
</html>