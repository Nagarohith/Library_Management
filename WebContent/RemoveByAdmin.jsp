<%@ page import="org.apache.log4j.Logger"%>
<html>
<head>
<title>Remove users</title>
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	<div class="container">

		<div></div>
		<header>
			<h1>Remove users</h1>
		</header>
		<section>
			<div id="container_demo">
				<div id="wrapper">
					<div>
						<form action="RemoveUser" autocomplete="on">
							<%		
									response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
									response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
									response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
									response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
									Logger logger = Logger.getLogger(this.getClass());
                            		// Removal of user must be done by administratror.
                            		// session should not be created as new.
                            		session = request.getSession(false);
                            		// session should be created by administrator.
                            		String name = (String) session.getAttribute("administrator");
                            		if(name!=null){
                            			
                            		
                            	%>
							<h1>Remove</h1>
							<p>
								<label for="username" class="uname" data-icon="u">
									Enter User Name </label> <input id="username" name="username"
									required="required" type="text" placeholder="eg. nagr0616" />
							</p>
							<p class="login button">
								<input type="submit" value="Remove" />
							</p>
							<%
								
								}else {
                            			//If session is new then it'll get redirected to login page. 
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