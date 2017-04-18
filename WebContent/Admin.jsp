<%@ page import="org.apache.log4j.Logger"%>
<html>
<head>
<title>Admin Page</title>
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
	<form action="register.jsp" action="post">
		<center>
			<%
				response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
				response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
				response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
				response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
				Logger logger = Logger.getLogger(this.getClass());
				session = request.getSession(false);
				String username = (String) session.getAttribute("administrator");
				//Setting new attribute for the session and it will be handled in registration form for registration of librarian.
				session.setAttribute("Admin", "Librarian");
				if (username != null) {
					out.println("<h1 style=\"font-size: 40px;\"> Admin page</h1>");
			%>

			<hr>
			<input type="hidden" name="username" value="<%=username%>"> <a
				href="register.jsp" class="buttonlink">Add Librarian</a> <br> <br>
			<a href="RemoveByAdmin.jsp" class="buttonlink">Remove Users</a> <br>
			<br> <a href="Logout" class="buttonlink">Logout</a> <br> <br>
			<%
				} else {
					//If the session is not created then this is not the correct flow.
					logger.debug("session is not created");
					RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
					rd.forward(request, response);
				}
			%>
		</center>
	</form>
</body>
</html>