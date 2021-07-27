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

        .boxes {
            float: left;
            margin: 10px;
            padding: 10px;
            max-width: 300px;
            height: 200px;
            border: 1px solid black;
            overflow: scroll;
        }

    </style>
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
    %>
    <div id="content">
        <div class="boxes">
            <p> Quiz List </p>
            <% for (int i = 0; i < allquiz.size(); i++) { %>
            <% if (i > 10) {
                break;
            }%>
            <a href=<%= "startQuiz.jsp?quizid=" +  allquiz.get(i).getQuizId()
            %>><%= (i+1) +". " + allquiz.get(i).getQuizName() %></a><br>
            <% } %>
        </div>
    </div>
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