<%@ page import="net.cr.beans.BookBean"%>
<%@ page import="net.cr.beans.BooksBean"%>
<%@ page import="net.cr.dao.BookDAO"%>
<%@ page import="net.cr.dao.BooksDAO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.apache.log4j.Logger"%>

<html>
<head>
<title>Visitor's page</title>
<style>
<!--
		/* @group Blink */
		.blink {
			-webkit-animation: blink .75s linear infinite;
			-moz-animation: blink .75s linear infinite;
			-ms-animation: blink .75s linear infinite;
			-o-animation: blink .75s linear infinite;
	 		animation: blink .75s linear infinite;
		}
		@-webkit-keyframes blink {
			0% { opacity: 1; }
			50% { opacity: 1; }
			50.01% { opacity: 0; }
			100% { opacity: 0; }
		}
		@-moz-keyframes blink {
			0% { opacity: 1; }
			50% { opacity: 1; }
			50.01% { opacity: 0; }
			100% { opacity: 0; }
		}
		@-ms-keyframes blink {
			0% { opacity: 1; }
			50% { opacity: 1; }
			50.01% { opacity: 0; }
			100% { opacity: 0; }
		}
		@-o-keyframes blink {
			0% { opacity: 1; }
			50% { opacity: 1; }
			50.01% { opacity: 0; }
			100% { opacity: 0; }
		}
		@keyframes blink {
			0% { opacity: 1; }
			50% { opacity: 1; }
			50.01% { opacity: 0; }
			100% { opacity: 0; }
		}
		-->
table {
	border: 1px solid black;
	width: 50%;
	background-color: #ffffff
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #f2f2f2
}

th {
	background-color: #20B2AA;
	color: white;
}

.button {
	background-color: #20B2AA;
	border: none;
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

#sbutton {
	background-color: #20B2AA;
	border: none;
	color: white;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	padding: 10px 25px;
}

body {
	margin: 60px;
	background-color: #fefefe;
	border-style: groove;
	align: center;
}

hr {
	width: 60%;
}

.search {
	visibility: hidden
}
</style>

<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />


</head>
<body class="body">
	<form action="index.jsp" method="post">
		<center>
			<h1 style="font-size: 40px;">Library Portal</h1>

			<hr>
			<br>
			<blink>
				<h2 style="color: blue; font-size: 30px" class="tab blink">
					Visitor's Page</h2>
			</blink>
			<hr>
			<br>
			<br> <a href="login.jsp" class="button">Click here to Login</a>
			<br>
			<br> <a href="register.jsp" class="button">Click here to
				register</a> <br>
			<br> <a onclick="myFunction()" href="#" class="button">
				Search the Book</a><br>
			<script>
	//This function used to disable the hidden property applied. 
	function myFunction() {
    	document.getElementById("slabel").style.visibility = "visible";
    	document.getElementById("stext").style.visibility = "visible";
    	document.getElementById("sbutton").style.visibility = "visible";
    	document.getElementById("stable").style.visibility = "visible";
	}
	</script>
			<br> <label class="search" id="slabel">Enter the Book
				Name : </label> <input type="text" class="search" name="bookname" id="stext">
			<br>
			<br> <input type="submit" class="search" id="sbutton"
				value="check"><br>
			<br>
			<%
		//If bookname text doesn't contain any bookname then our code shouldn't create table.
		Logger logger = Logger.getLogger(this.getClass());
		if(request.getParameter("bookname")!=null && request.getParameter("bookname")!=""){
				BookBean book = new BookBean();
				BooksBean book1 = new BooksBean();
				logger.info(request.getParameter("bookname") + " : Book Name searching by visitor  ");	
				book.setTitle(request.getParameter("bookname"));
				List<BookBean> list = new ArrayList<BookBean>();
				
				list =  new BooksDAO().getBooks(book);
		
				if(book.isValid()){
					List<BookBean> books = list;
					out.println("<h2 style=\"color:blue;\">Book(s) found</h2><br>");
	%>
			<!-- Print start of table and column headers -->
			<table>
				<tr>
					<th>Title</th>
					<th>Authors</th>
					<th>Available</th>
				</tr>

				<%
				//Printing the table with the content of the database by array lists.
				for(int i=0;i<books.size();i++){
					out.println("<tr>");
					out.println("<td>" + books.get(i).getTitle() + "</td>");
					out.println("<td>" + books.get(i).getAuthors() + "</td>");
					out.println("<td>" + books.get(i).getCounts() + "</td>");
					out.println("</tr>");
				}
			out.println("</table><br><br><br>");		
				}
				else {
					out.println("<h2 style=\"color:red;\">No Books found</h2><br><br><br> ");
				}
		}

	%>
				</center>
				</form>
</body>
</html>