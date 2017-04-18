<html>
<head>
<title>Login Page</title>
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />

</head>
<body>
	<div class="container">

		<div></div>
		<header>
			<h1>Login Form</h1>
		</header>
		<section>
			<div id="container_demo">
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="Login" autocomplete="off" method="post">

							<h1>Log in</h1>
							<p>
								<label for="username" class="uname" data-icon="u"> User
									Name </label> <input id="username" name="username" required="required"
									type="text" placeholder="eg. nagr0616" />
							</p>
							<p>
								<label for="password" class="youpasswd" data-icon="p">
									Password </label> <input id="password" name="password"
									required="required" type="password" placeholder="eg. X8df!90EO" />
							</p>

							<p class="login button">
								<input type="submit" value="Login" />
							</p>
							<%
								String message = request.getParameter("message");
								if(message!=null)
								{
									out.println("<h3> Invalid user </h3>");
								}
								%>
							<p class="change_link">
								Not a member yet ? <a href="index.jsp" class="to_register">Join
									us</a>
							</p>

						</form>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>
</html>