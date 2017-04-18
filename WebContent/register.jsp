<%@ page import="org.apache.log4j.Logger"%>
<html>
<head>
<title>Registration form</title>

<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />

</head>
<body>
	<div class="container">

		<header>
			<h1>Registration Form</h1>

		</header>
		<section>
			<div id="container_demo">
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="Register" autocomplete="off"
							onsubmit="return validate()" method="post">
							<%
									response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
									response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
									response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
									response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward 
									Logger logger = Logger.getLogger(this.getClass());
                            		session = request.getSession(false);
                            		String role = "1";
                            		//session attributes are created in admin page for
                            		// registration of librarian.
                            		String usertype = (String)session.getAttribute("Admin");
                            		logger.debug("User type : " + usertype);
                            		if(usertype!=null){
                            	    	role = "2";
                            	    }
                            	    else{
                            	    	role="1";
                            	    }
                            		logger.debug("role in register jsp: " + role);
                            	%>

							<input type="hidden" name="role" value="<%= role %>">
							<h1>Register</h1>
							<p>
								<label for="firstname" class="uname" data-icon="u">
									First Name </label> <input id="firstname" name="fname"
									required="required" type="text" pattern="[A-Z]{1}[a-z]{1,19}"
									title="First letter should be Capital and should contain only letters"
									placeholder="eg. Naga" />
							</p>
							<p>
								<label for="lastname" class="uname" data-icon="u"> Last
									Name </label> <input id="lastname" name="lname" required="required"
									type="text" pattern="[A-Z]{1}[a-z]{1,19}"
									title="First letter should be Capital and should contain only letters"
									placeholder="eg. Rohith" />
							</p>
							<p>
								<label for="username" class="uname" data-icon="u"> User
									Name </label> <input id="username" name="username" required="required"
									type="text" pattern="[a-z]{4}[0-9]{4}"
									title="First 4 letters must contain only lowercase letters and followed by 4 digits"
									placeholder="eg. nagr0616" />
							</p>
							<p>
								<label for="password" class="youpasswd" data-icon="p">
									Your Password </label> <input id="password" name="password"
									required="required" type="password"
									pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
									title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
									placeholder="eg. X8df!90EO" />
							</p>
							<p>
								<label for="cpassword" class="youpasswd" data-icon="p">
									Please Confirm your password </label> <input id="cpassword"
									name="cpassword" required="required" type="password"
									pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
									title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
									placeholder="eg. X8df!90EO" />
							</p>
							<p>
								<label for="phone" class="youmobile" data-icon="PH">
									Mobile No </label> <input id="phone" name="phone" required="required"
									type="text" pattern="[0-9]{10}"
									title="Must contain 10 digits only"
									placeholder="eg. 9591729534" />
							</p>
							<p>
								<label for="Email" class="youmail" data-icon="e"> Email
								</label> <input id="Email" name="email" required="required" type="text"
									pattern="[a-zA-Z0-9.]{1,20}[@]{1}[a-zA-Z]{1,10}[.]{1}[a-zA-Z]{1,5}"
									title="Please use only letters (a-z), numbers, and periods."
									placeholder="eg. mymail@mail.com" />
							</p>
							<p class="login button">
								<input type="submit" value="SIGN UP" onclick="retun validate()" />
							</p>
						</form>
						<script>
                                //Checking whether both the passwords given are same.
                                function validate(){
                                	var fp = document.getElementById("password").value;
                                	var sp = document.getElementById("cpassword").value;
                                	if(fp == sp){
                                		return true;
                                	}
                                	else{
                                		alert("Both passwords must be same");
                                		<% logger.debug("Passwords are not matching"); %>
                                		return false;
                                	}
                                }
                                </script>
					</div>
				</div>
			</div>
		</section>
	</div>

</body>
</html>