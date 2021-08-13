<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
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

<%@ page import="quiz.bean.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="manager.*" %>
<%@ page import="quiz.dao.*" %>
<%@ page import="user.dao.*" %>

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

  <form action="Logout" method="get">
    <button> Logout </button><br>
  </form>

</nav>
<section>
  <div id="content">
    <br>
    <%
      String myname = (String) session.getAttribute("username");
      UserManager um = (UserManager) getServletConfig().getServletContext().getAttribute("userM");
      QuizDao qd = um.getQuizDao();
      UserDao ud = um.getUserDao();
      QuestionDao qsd = um.getQuestionDao();
      Integer qid = Integer.parseInt((String) request.getParameter("quizid"));
      String quiz_name = qd.getNameByQuizId(qid);
      Quiz curr =  qd.getQuizById(qid);

      int author_id = curr.getAuthorId();
      String description = curr.getDescription();
      ArrayList<Question> qstlist = qsd.getQuestionsByQuizId(qid);
      String author = ud.getUserById(author_id).getUserName();
      int isMultiPage = curr.getPages();
    %>

    <h2> <%="Quiz:   " + quiz_name %></h2>
    <% if(author.equals(myname)) {%>
    <a href= <%= "showQuiz.jsp?quizid=" + qid %>>Edit quiz</a><br>
    <% } %>

    <h4> Author: &nbsp; <a href=<%= "profile.jsp?profile=" + author %>> <%= author %> </a></h4>

    <p><b>Description:</b> <i> <%= description %></i></p>

    <br>
    <% if (isMultiPage == 0) { %>
    <input type="button" id= <%=qid %> value="Start Quiz" name=<%=qid %> onClick="showQuiz(this)"><br>
    <% } else { %>
    <input type="button" id= <%=qid %> value="Start Quiz" name=<%=qid %> onClick="multiPageQuiz(this)"><br>
    <% } %>
    <br><br><hr>

    <%@ page import="quiz.bean.*" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="manager.*" %>
    <%@ page import="quiz.dao.*" %>
    <%@ page import="user.dao.*" %>

    <% UserManager uM = (UserManager) getServletConfig().getServletContext().getAttribute("userM");
      QuizDao qDao = uM.getQuizDao();
      UserDao uDao = uM.getUserDao();
      ArrayList<History> recents = qDao.getRecentTakers(qid);
      ArrayList<History> top = qDao.getTopScores(qid);
    %>

    <div class="boxes" >
      Recent test takers:<br>
      <% for (int i=0; i<recents.size(); i++) {%>
      <% if (i > 5 ) break;
        String uName = uDao.getUserById(recents.get(i).getUser_id()).getUserName();
      %>
      <%=(i+1) +". "  %><a href=<%= "profile.jsp?profile=" +  uName
    %>><%= uName %></a> <i> Score: <%=recents.get(i).getScore() %> </i><br>
      <% } %>
    </div>

    <div class="boxes" >
      TOP 5:<br>
      <% for (int i=0; i<top.size(); i++) {%>
      <% if (i > 5 ) break;
        String uName = uDao.getUserById(top.get(i).getUser_id()).getUserName();
      %>
      <%=(i+1) +". "  %><a href=<%= "profile.jsp?profile=" +  uName
    %>><%= uName %></a> <i> Score: <%=top.get(i).getScore() %> </i>&nbsp;&nbsp;
      <i> Time: <%=top.get(i).getTime() %> </i><br>
      <% } %>
    </div>
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