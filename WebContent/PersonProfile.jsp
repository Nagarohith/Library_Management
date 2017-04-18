<%@ page import="net.cr.beans.PersonBean"%>
<%@ page import="net.cr.dao.PersonDAO"%>
<%@ page import="org.apache.log4j.Logger"%>
<html>
<head>
<title>Person profile</title>
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />

</head>
<body>
	<div class="container">
		<header>
			<h1>User Details</h1>

		</header>
		<section>
			<div id="container_demo">
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="Edit" autocomplete="on" method="post">
							<%  	
								response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
								response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
								response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
								response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
								Logger logger = Logger.getLogger(this.getClass());
                            	//String username = request.getParameter("username");
                            	//session should not be created here.
                            	session=request.getSession(false);  
                            	String username = (String) session.getAttribute("User");
                            	// checks whether session is created new or not.
                            	if(username!=null){
                            	PersonBean person = new PersonBean();
                        		person.setUsername(username);
                        		person = new PersonDAO().getRecords(person);
                        		String fname = person.getFname();
                        		String lname = person.getLname();
                        		String phone= person.getPhone();
                        		String email= person.getEmail();
                        		%>
							<input type="hidden" name="username" id="username"
								value="<%= username %>" />
							<!-- If we disabled then we can't send its value through get or post -->
							<h1>You Can Edit</h1>
							<p>
								<label for="username" class="uname" data-icon="u"> User
									Name </label> <input id="username" name="username" required="required"
									type="text" value="<%= username %>" disabled />
							</p>
							<p>
								<label for="fname" class="uname" data-icon="u"> First
									Name </label> <input id="fname" name="fname" required="required"
									type="text" pattern="[A-Z]{1}[a-z]{1,19}"
									title="First letter should be Capital and should contain only letters"
									placeholder="eg. Naga" value="<%= fname %>" />
							</p>
							<p>
								<label for="lname" class="uname" data-icon="u"> Last
									Name </label> <input id="lname" name="lname" required="required"
									type="text" pattern="[A-Z]{1}[a-z]{1,19}"
									title="First letter should be Capital and should contain only letters"
									placeholder="eg. Rohith" value="<%= lname %>" />
							</p>
							<p>
								<label for="phone" class="youmobile" data-icon="PH">
									Mobile No </label> <input id="phone" name="phone" required="required"
									type="text" pattern="[0-9]{10}"
									title="Must contain 10 digits only"
									placeholder="eg. 9591729534" value="<%= phone %>" />
							</p>
							<p>
								<label for="email" class="youmail" data-icon="e"> Email
								</label> <input id="email" name="email" required="required" type="email"
									pattern="[a-zA-Z0-9.]{1,20}[@]{1}[a-zA-Z]{1,10}[.]{1}[a-zA-Z]{1,5}"
									title="Please use only letters (a-z), numbers, and periods."
									placeholder="eg. mymail@mail.com" value="<%= email %>" />
							</p>
							<p class="login button">
								<input type="submit" value="SAVE" />
							</p>
							<%
                        		}
                        		else {
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