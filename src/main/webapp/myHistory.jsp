<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="mystyles.css" />
  <script type="text/javascript">
    function newPopup(url) {
      popupWindow = window.open(
              url,'popUpWindow','height=30px, width=50px,left=250,top=150,resizable=no,status=yes')
    }
  </script>

  <style>
    input[type=button] {
      background:none!important;
      border:none;
      padding:0!important;
      font-family:arial,sans-serif;
      font-size: 15px;
      color:green;
      display:inline-block;
      text-decoration:underline;
      cursor:pointer;
    }
  </style>
</head>
<body>

<%@ page import="user.dao.*" %>
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
  <p>- Lorem Oposum..</p>
 </section>

<% } else { %>
<script src="myscripts.js"></script>

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

<%
  UserManager man = (UserManager) getServletConfig().getServletContext().getAttribute("userM");
  QuizDao qdao = man.getQuizDao();
  UserDao udao = man.getUserDao();

  String me = (String) session.getAttribute("username");
  int uid = -1;;
  try {
    uid = udao.getUserByName(me).getUserId();
  } catch (SQLException e) {
    e.printStackTrace();
  }

  ArrayList<quiz.bean.History> hist = null;
  hist = qdao.getUserHistory(uid);
%>

<section>
  <div id="content">
    <br>
    <%
      if(request.getParameter("challenged") != null) { %>
    <p style="color:red" > <i>
      <font size="2"> <%= request.getParameter("challenged") %> </font>
    </i> </p>
    <%}%>

    <h2> My History </h2>
    <% for (int i=0; i<hist.size(); i++) { %>
    <% String name = qdao.getNameByQuizId(hist.get(i).getQuiz_id());
      int bestScore = qdao.getBestScore(uid, hist.get(i).getQuiz_id());
    %>
    <p> <%= (i+1) + ". " %> <a href=<%= "startQuiz.jsp?quizid=" + hist.get(i).getQuiz_id()%>> <%= name %> </a></p>
    <i>  <%= "   Score:  " + hist.get(i).getScore() %></i><br>
    <i>  <small><%= "  Best Score:  " + bestScore %></small></i>
    <input type="Submit" value="Challenge User" name=<%=hist.get(i).getQuiz_id() %> onClick="challengeUser(this)"><br>
    <br>
    <% } %>
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