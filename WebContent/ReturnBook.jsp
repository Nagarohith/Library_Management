<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.apache.log4j.Logger"%>
<html>
<head>
<title>Return Book</title>
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	<div class="container">

		<div></div>
		<header>
			<h1>Return Book</h1>
		</header>
		<section>
			<div id="container_demo">
				<div id="wrapper">
					<div>
						<form action="ReturnBook" autocomplete="on" method="post">
							<%	Logger logger = Logger.getLogger(this.getClass());
                            	// Session should be new.
                            	session = request.getSession(false);
                            	String userName = (String) session.getAttribute("Librarian");
                            	if(userName!=null){	
                            %>
							<h1>Return Book</h1>
							<p>
								<label for="bookId" class="uname"> Enter Book Id </label> <input
									id="bookId" name="bookId" required="required" type="text"
									placeholder="eg. B001" />
							</p>
							<p>
								<label for="username" class="uname"> Enter User Name </label> <input
									id="username" name="username" required="required" type="text"
									placeholder="eg. nagr0616" />
							</p>
							<%
                                //Date is the class under java.util and it is used to generate current date. 
                                Date d = new Date();
                              	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String date1 = sdf.format(d);
                                int i = d.getMonth();
                                // due date will be set one month from issuedate.
                        		i++;
                        		d.setMonth(i);
                        		Date d1 =d;
                        		String date2 = sdf.format(d);
                        		%>
							<p>
								<label for="returnDate" class="uname"> Return Date
									[dd/mm/yyyy]</label> <input id="returnDate" name="returnDate"
									required="required"
									pattern="[0-9]{1,2}[/]{1}[0-9]{1,2}[/]{1}[0-9]{4}"
									title="Please provide the date in the format : dd/mm/yyyy"
									type="text" value="<%=date1 %>" placeholder="dd/mm/yyyy " />
							</p>
							<p class="login button">
								<input type="submit" value="Take Book" />
							</p>
							<%
								String message = request.getParameter("message");
								if(message!=null)
								{
									out.println("<h3> Return successful </h3>");
								}
                            	}
                            	else{
                            		//If the session is not created then redirect to login.
                            		logger.debug("Session is not created");
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