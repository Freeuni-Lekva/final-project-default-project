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

    <div class="form">
        <form action="Logout" method="get">
            <button> Logout </button><br>
        </form>
    </div>
</nav>

<section>
    <div id="content">
        <br>
        <br>
        <%if (session.getAttribute("quizfinish") != null) { %>
        <p> Your quiz is added. </p>
        <%} else if (session.getAttribute("quizprocess") != null){ %>


        <form action="ShowQuiz" method="get">
            <button>Continue Started Quiz</button>
        </form>
        <br>
        <br>
        <p> OR </p>
        <br>
        <br>
        <%} %>
        <h3> Create your own quiz :)</h3> <br>
        <form action="StartCreating" method="post">
            <i>Quiz Name:</i> <input type="text" name="quizName"><br>
            <i>Description: </i><br>
            <textarea rows=3 cols=15 name="description"></textarea><br>

            <i>Category:</i>
            <%@ include file="quizCategories.jsp" %><br>
            <i>Random Order:</i> <input type="checkbox" name="isRandom" value="random"><br>
            <i>Multiple Page:</i> <input type="checkbox" name="multiPage" value="multi"> <br>
            <i>Correction:</i> <input type="checkbox" name="correction" value="correction">
            <br><br>
            <button>Start new Quiz</button>

        </form>
    </div>
</section>
<% } %>

<footer>
    <a href="index.jsp">Home page</a>
</footer>

</body>
</html>