<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <script type="text/javascript">
        function newPopup(url) {
            popupWindow = window.open(
                url,'popUpWindow','height=30px, width=50px,left=250,top=150,resizable=no,status=yes')
        }
    </script>
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
    <p>- Lorem Ipsum Something...</p>
 </section>

<% } else { %>
<script src="myscripts.js"></script>

<nav>
    <h2><a href="index.jsp">
        <%= session.getAttribute("username") %>
    </a></h2>
    <img src="<%= session.getAttribute("image") %>" alt="<%= session.getAttribute("username") %>" style="width:90px;height:90px;"><br>

    <form action="Logout" method="get">
        <button> Logout </button><br>
    </form>

</nav>

<% if(session.getAttribute("deactivated") != null) { %>
<section> <h1> <%= session.getAttribute("deactivated") + "'s account is Deactivated" %></h1></section>
<% session.removeAttribute("deactivated"); %>
<% } else { %>
<section>
    <%@ page import="quiz.bean.*" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="manager.*" %>
    <%@ page import="quiz.dao.*" %>
    <%@ page import="user.dao.*" %>
    <%@ page import="user.bean.*" %>

    <% UserManager uM = (UserManager) getServletConfig().getServletContext().getAttribute("userM");
        UserDao uDao = uM.getUserDao();
        User me = uDao.getUserByName((String)request.getSession().getAttribute("username"));
        QuizDao qDao = uM.getQuizDao();
        ArrayList<Quiz> allquiz = qDao.getQuizList();
        ArrayList<Quiz> topquiz = qDao.getTopQuizes();
        ArrayList<Quiz> newquiz = qDao.getNewQuizes();
        ArrayList<String> categ = qDao.getCategories();
    %>
  
</section>
<% } %>
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