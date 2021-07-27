<%--
  Created by IntelliJ IDEA.
  User: LevanOzashvili
  Date: 7/27/2021
  Time: 09:42 PM
  To change this template use File | Settings | File Templates.
--%>
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
<%@ page import="quiz.bean.*" %>

<header>
    <a href="index.jsp">Quiz Web Site</a>
</header>
<%if (session.getAttribute("authorized") == null || session.getAttribute("logout") != null){ %>
<nav>
    <a href="login.jsp"  >Login</a> <br>
    <a href="register.jsp" >Register</a>
</nav>
<section>
    <p>- Lorem Ipsum </p>
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
    User me = udao.getUserByName((String)session.getAttribute("username"));

    int uid = me.getUserId();

    ArrayList<Quiz> myquizes = qdao.getQuizByCreator(uid);
    ArrayList<Quiz> unfinished = qdao.getUnfinishedQuizByCreator(uid);
%>

<section>
    <div id="content">
        <br>
        <% if(request.getParameter("challenged") != null) { %>
        <p style="color:red" > <i>
            <font size="2"> <%= request.getParameter("challenged") %> </font>
        </i> </p>
        <%}%>

        <h2> My Quizes </h2>
        <br>
        <h4> Finished: </h4>
        <% for (int i = 0; i < myquizes.size(); i++) { %>
        <% String name = qdao.getNameByQuizId(myquizes.get(i).getQuizId());
        %>
        <p> <%= (i+1) + ". " %> <a href=<%= "startQuiz.jsp?quizid=" + myquizes.get(i).getQuizId()%>> <%= name %> </a>
            &nbsp;&nbsp;
            <a type="likeabutton" href= <%= "showQuiz.jsp?quizid=" + myquizes.get(i).getQuizId() %>>Edit quiz</a>
        </p>
        <% } %>
        <hr>
        <h4> In process: </h4>
        <% for (int i = 0; i < unfinished.size(); i++) { %>
        <% String name = qdao.getNameByQuizId(unfinished.get(i).getQuizId());
        %>
        <p> <%= (i+1) + ". " %> <a href=<%= "startQuiz.jsp?quizid=" + unfinished.get(i).getQuizId()%>> <%= name %> </a>
            &nbsp;&nbsp;
            <a href= <%= "showQuiz.jsp?quizid=" + unfinished.get(i).getQuizId() %>>Edit quiz</a>
        </p>
        <% } %>

    </div>
</section>
<% } %>
<aside>
<footer>
    <a href="index.jsp">Home page</a>
</footer>

</body>
</html>