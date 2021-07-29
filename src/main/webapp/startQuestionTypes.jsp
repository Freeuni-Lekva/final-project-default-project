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
<script src=""></script>

<nav>

  <h2><a href="index.jsp">
    <%= session.getAttribute("username") %>
  </a></h2>
  <img src="<%= session.getAttribute("image") %>" alt="<%= session.getAttribute("username") %>" style="width:90px;height:90px;"><br>

  <%@ include file="panel.jsp" %>

  <div class="form">
    <form action="Logout" method="get">
      <button> Logout </button><br>
    </form>
  </div>
</nav>

<section>

  <div id="content">

    <input type="submit" id=<%=request.getParameter("quizid") %> value="Question-Response" name="QR" onClick="addQuestion(this)">
    <input type="submit" id=<%=request.getParameter("quizid") %> value="Fill-in-the-Blank" name="FB" onClick="addQuestion(this)">
    <input type="submit" id=<%=request.getParameter("quizid") %> value="Multiple-Choice" name="MC" onClick="addQuestion(this)">
    <input type="submit" id=<%=request.getParameter("quizid") %> value="Matching" name="M" onClick="addQuestion(this)"><br>
    <input type="submit" id=<%=request.getParameter("quizid") %> value="Picture-Response" name="PR" onClick="addQuestion(this)">
    <input type="submit" id=<%=request.getParameter("quizid") %> value="Multi-Answer" name="MA" onClick="addQuestion(this)">
    <input type="submit" id=<%=request.getParameter("quizid") %> value="Multiple-Choice-Answers" name="MCA" onClick="addQuestion(this)"><br>
    <br> <br> <br>    <br> <br> <br>

    <a href=<%="ShowQuiz?quizid=" + request.getParameter("quizid") %>>Preview</a><br>

  </div><br>


  <form action=<%= "SaveUnfinishedQuiz?quizid="+request.getParameter("quizid")%> method="post">
    <button> Save and Continue later </button>
  </form><br>

  <form action=<%= "FinishQuizCreating?quizid="+request.getParameter("quizid")%> method="post">
    <button> Finish Quiz </button>
  </form><br>


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