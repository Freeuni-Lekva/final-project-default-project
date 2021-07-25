<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>

<header>Sign up</header>

<body>
	<section>
		   <% if(session.getAttribute("wrongdata") != null) { %>
			<p style="color:red" > <i>
		  			<font size="2"> <%= session.getAttribute("wrongdata") %> </font> 
		  		</i> </p>
		  		<% session.removeAttribute("wrongdata"); %>
		  <% } %>
		  <% if(session.getAttribute("userexists") != null) { %>
			<p style="color:red" > 
				<i>
		  			<font size="2"> User already exists </font>
		 	 	</i>
		 	</p>
		  		<% session.removeAttribute("userexists"); %>
		  <% } %>
		  
		  <div class="form">
		      <form action="Register" method="post">
			    <input type="text" placeholder="Username" name="user" />
			    <input type="password" placeholder="Password" name="pass" />
			    <input type="text" placeholder="Email" name="email" />
			    <input type="text" placeholder="Photo" name="photo" />
		    	<button>Create</button>
		    </form>
		  </div>
  </section>
  <footer>
  		<a href="index.jsp">Home page</a>
  </footer>
</body>
</html>