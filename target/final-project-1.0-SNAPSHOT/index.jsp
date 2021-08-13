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
        MessagesDao mDao = ((MessageManager) getServletConfig().getServletContext().getAttribute("mesM")).getMessageDao();
        User me = uDao.getUserByName((String)request.getSession().getAttribute("username"));
        QuizDao qDao = uM.getQuizDao();
        ArrayList<Quiz> allquiz = qDao.getQuizList();
        ArrayList<Quiz> topquiz = qDao.getTopQuizes();
        ArrayList<Quiz> newquiz = qDao.getNewQuizes();
        ArrayList<String> categ = qDao.getCategories();
    %>
    <div id="content">
        <h3 style="color: red;">áƒáƒ“áƒ›áƒ˜áƒœáƒ˜áƒ¡áƒ¢áƒ áƒáƒªáƒ˜áƒ˜áƒ¡ áƒ¬áƒ”áƒ áƒ˜áƒšáƒ˜: áƒ¥áƒ•áƒ˜áƒ–áƒ˜ 1 - áƒ•áƒ˜áƒœ áƒªáƒ®áƒáƒ•áƒ áƒ”áƒ‘áƒ¡ áƒ§áƒ•áƒ”áƒšáƒáƒ–áƒ” áƒ›áƒáƒ¦áƒáƒš áƒ¡áƒáƒ áƒ—áƒ£áƒšáƒ–áƒ”?</h3>

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

        <div class="boxes">
            <p> Top Quizzes </p>
            <% for (int i = 0; i < topquiz.size(); i++) { %>
            <a href=<%= "startQuiz.jsp?quizid=" +  topquiz.get(i).getQuizId()
            %>><%= (i+1) +". " + topquiz.get(i).getQuizName() %></a><br>
            <% } %>
        </div>

        <div class="boxes">
            <p> New Quizzes </p>
            <% for (int i = 0; i < newquiz.size(); i++) { %>
            <a href=<%= "startQuiz.jsp?quizid=" +  newquiz.get(i).getQuizId()
            %>><%= (i+1) +". " + newquiz.get(i).getQuizName() %></a><br>
            <% } %>
        </div>

        <div class="boxes">
            <p> Quiz Categories </p>
            <% for (int i = 0; i < categ.size(); i++) { %>
            <input type="submit" value="<%=categ.get(i) %>" name="<%=categ.get(i) %>" onClick="categoryQuizes(this)"><br>
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