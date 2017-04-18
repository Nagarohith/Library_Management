<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.apache.log4j.Logger"%>
<html>
<head>
<title>Issue Books</title>
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	<div class="container">

		<div></div>
		<header>
			<h1>Issue Book</h1>
		</header>
		<section>
			<div id="container_demo">
				<div id="wrapper">
					<div>
						<form action="IssueBook" autocomplete="on" method="post">
							<%
                            	Logger logger = Logger.getLogger(this.getClass());
                            		// session should not be created here.
                            		session = request.getSession(false);
                            		String userName = (String) session.getAttribute("Librarian");
                            		response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
                            		response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
                            		response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
                            		response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
                            		//checks session is old.
                            		if(userName != null){
                            	%>
							<h1>Issue Book</h1>
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
                                Date d = new Date();
                              	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String date1 = sdf.format(d);
                                //Incrementing month field to set Due date to one month from the day of issuing the book by loading.
                                //If the librarian needs extra date he is allowed to change due date.
                                int i = d.getMonth();
                        		i++;
                        		d.setMonth(i);
                        		Date d1 =d;
                        		String date2 = sdf.format(d);
                        		%>
							<p>
								<label for="issueDate" class="uname"> Issue Date
									[dd/mm/yyyy]</label> <input id="issueDate" name="issueDate"
									required="required"
									pattern="[0-9]{1,2}[/]{1}[0-9]{1,2}[/]{1}[0-9]{4}"
									title="Please provide the date in the format : dd/mm/yyyy"
									type="text" value="<%=date1 %>" placeholder="dd/mm/yyyy " />
							</p>
							<p>
								<label for="dueDate" class="uname"> Due Date
									[dd/mm/yyyy]</label> <input id="dueDate" name="dueDate"
									required="required"
									pattern="[0-9]{1,2}[/]{1}[0-9]{1,2}[/]{1}[0-9]{4}"
									title="Please provide the date in the format : dd/mm/yyyy"
									type="text" value="<%=date2 %>" placeholder="dd/mm/yyyy" />
							</p>
							<p class="login button">
								<input type="submit" value="Issue" />
							</p>
							<%
								String message = request.getParameter("message");
								if(message!=null)
								{
									out.println("<h3> Insertion of book: successful </h3>");
								}
                            	}
                            	else{
                            		//Session in not created
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