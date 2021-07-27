<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>

<%@ page import="quiz.bean.*" %>
<%@ page import="user.dao.*" %>
<%@ page import="user.bean.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="manager.*" %>
<%@ page import="quiz.dao.*" %>
<%@ page import="java.sql.SQLException" %>

<header>
  <a href="index.jsp">Quiz Web Site</a>
</header>
<%if (session.getAttribute("authorized") == null || session.getAttribute("logout") != null){ %>
<nav>
  <a href="login.jsp"  >Login</a> <br>
  <a href="register.jsp" >Register</a>
</nav>
<section>
  <p>- Lorem Ipsum</p>
</section>

<% } else { %>
<script src="myscripts.js"></script>

<%
  UserManager man = (UserManager) getServletConfig().getServletContext().getAttribute("userM");
  QuizDao qdao = man.getQuizDao();
  UserDao udao = man.getUserDao();
  String username = (String) request.getParameter("profile");
  User usr = null;
  Integer logged_user_id = (Integer) session.getAttribute("id");

  try {
    usr = udao.getUserByName(username);
  } catch (SQLException e) {
    e.printStackTrace();
  }
%>
<nav>
  <p style="color:blue;"> <a href=<%= "profile.jsp?profile=" + request.getParameter("profile") %>>
    <%= request.getParameter("profile") + "'s Profile" %></a></p>

  <img src="<%= usr.getUserpic() %>" alt="<%= usr.getUserName()%>" style="width:90px;height:90px;"/>

  <div class="form">
    <form action="AcceptRequest" method="post">
      <input type="hidden" name="acceptfrom" value="<%=usr.getUserName() %>">
      <input type="hidden" name="returntouser" value="<%=usr.getUserName() %>">
      <input type="submit" value="Accept Request" />
    </form>
  </div>

  <a href=<%= "showHistory.jsp?profile=" +  usr.getUserName() %>> History </a><br>
  <a href=<%= "showUsersQuizes.jsp?profile=" +  usr.getUserName() %>> Quizes </a><br>

</nav>

<nav>
  <p style="color:blue;"> <a href=<%= "profile.jsp?profile=" + request.getParameter("profile") %>>
    <%= request.getParameter("profile") + "'s Profile" %></a></p>

  <img src= <%= usr.getUserpic() %> alt=<%= usr.getUserName()%> style="width:90px;height:90px;"/>

  <a href=<%= "showHistory.jsp?profile=" +  usr.getUserName() %>> History </a><br>
  <a href=<%= "showUsersQuizes.jsp?profile=" +  usr.getUserName() %>> Quizes </a><br>
</nav>

<%
  UserDao uDao = ((UserManager)getServletConfig().getServletContext().getAttribute("userM")).getUserDao();
  User curr_user = uDao.getUserByName(request.getParameter("profile"));
  QuizDao qDao = ((UserManager)getServletConfig().getServletContext().getAttribute("userM")).getQuizDao();
  ArrayList<Quiz> quizes = qDao.getQuizByCreator(curr_user.getUserId());
%>
<section>
  <div id="content">
    <br>
    <% for (int i=0; i<quizes.size(); i++) { %>
    <%= (i+1) + ". " %> <a href=<%="startQuiz.jsp?quizid=" + quizes.get(i).getQuizId() %>>
      <%= quizes.get(i).getQuizName() %></a> <br>
    <% } %>
  </div>
</section>
<% } %>

<footer>
  <a href="index.jsp">Home page</a>
</footer>

</body>
