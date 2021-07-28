<%--
  Created by IntelliJ IDEA.
  User: nikushaozashvili
  Date: 7/29/2021
  Time: 12:24 AM
  To change this template use File | Settings | File Templates.
--%>
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
  <p>- Lorem Ipsum ...</p>
</section>

<% } else { %>
<script src="myscripts.js"></script>

<nav>
  <h2><a href="index.jsp">
    <%= session.getAttribute("username") %>
  </a></h2>
  <img src="<%= session.getAttribute("image") %>" alt="<%= session.getAttribute("username") %>" style="width:90px;height:90px;"><br>

  <%@ include file="panel.jsp" %>

  <button >Scores</button><br>

  <form action="Logout" method="get">
    <button> Logout </button><br>
  </form>

</nav>
<section>
  <div id="content">
    <%@ page import="quiz.dao.*" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="manager.*" %>
    <%@ page import="quiz.bean.*" %>
    <%@ page import ="javax.servlet.ServletContext" %>


    <h2> TOP QUIZES </h2>
    <% try { %>
    <% ServletContext cont = request.getServletContext();
      UserManager uM = (UserManager) cont.getAttribute("userM");
      QuizDao qzDao = uM.getQuizDao();
      ArrayList<Quiz> top = qzDao.getTopQuizes();
    %>
    <% for (int i = 0; i < top.size(); i++) { %>
    <a href=<%= "###.jsp?quizid=" + top.get(i).getQuizId()%>> <%= top.get(i).getQuizName() %></a><br>
    <% } %>
    <%} catch (NullPointerException e) { %>
    <a> Nobody </a>
    <%}%>
  </div>
</section>
<% } %>

<aside>
  <input type="search" id="mySearch" placeholder="Search for friends..">
  <input type="submit" onclick="searchFunc()"/>
</aside>

<footer>
  <a href="index.jsp">Home page</a>
</footer>

</body>
</html>