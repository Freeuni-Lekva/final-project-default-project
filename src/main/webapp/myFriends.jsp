<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>

</head>
<body>

<header>
  <a href="index.jsp">Quiz Web Site</a>
</header>
<%if (session.getAttribute("authorized") == null || session.getAttribute("logout") != null){ %>
<nav>
  <a href="login.jsp"  >Login</a> <br>
  <a href="register.jsp" >Register</a>
</nav>
<section>
</section>

<% } else { %>

<nav>
  <h2><a href="index.jsp">
    <%= session.getAttribute("username") %>
  </a></h2>
  <img src="<%= session.getAttribute("image") %>" alt="<%=session.getAttribute("username") %>" style="width:90px;height:90px;"><br>

  <%@ include file="panel.jsp" %>

  <div class="form">
    <form action="Logout" method="get">
      <button> Logout </button><br>
    </form>
  </div>
</nav>

<% } %>
<footer>
  <a href="index.jsp">Home page</a>
</footer>

</body>
</html>