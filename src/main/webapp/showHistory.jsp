<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
</head>
<body>

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
    ArrayList<quiz.bean.History> hist = null;

    try {
        usr = udao.getUserByName(username);
        hist = qdao.getUserHistory(usr.getUserId());
    } catch (SQLException e) {
        // TODO Auto-generated catch block
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
</nav>

<% } else { %>
<nav>
    <p style="color:blue;"> <a href=<%= "profile.jsp?profile=" + request.getParameter("profile") %>>
        <%= request.getParameter("profile") + "'s Profile" %></a></p>

    <img src="<%= usr.getUserpic() %>" alt="<%= usr.getUserName()%>" style="width:90px;height:90px;"/>

    <a href=<%= "showHistory.jsp?profile=" +  usr.getUserName() %>> History </a><br>
</nav>
<% } %>

<section>
    <div id="content">
        <br>
        <h2> History </h2>
        <% for (int i=0; i<hist.size(); i++) { %>
        <% String name = qdao.getNameByQuizId(hist.get(i).getQuiz_id()); %>
        <p> <%= (i+1) + ". " %> <a href=<%="startQuiz.jsp?quizid=" + hist.get(i).getQuiz_id() %>><%= name %></a>
            &nbsp;&nbsp;&nbsp;
            <i>  <%= "Score: " + hist.get(i).getScore() %></i> </p>
        <% } %>
    </div>
</section>
<% } %>

<footer>
    <a href="index.jsp">Home page</a>
</footer>

</body>
