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
    <p>- Oposum Lorem... </p>
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
    FriendsDao fDao = ((FriendManager) getServletConfig().getServletContext().getAttribute("friM")).getFriendDao();
    ArrayList<quiz.bean.History> hist = null;

    try {
        usr = udao.getUserByName(username);
        hist = qdao.getUserHistory(usr.getUserId());
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    boolean areFriends = fDao.isFriend(logged_user_id, usr.getUserId());
    boolean isRequested = fDao.isRequested(logged_user_id, usr.getUserId());
    boolean reverseRequested = fDao.isRequested(usr.getUserId(), logged_user_id);

%>

<% if (areFriends) { %>
<nav>
    <p style="color:blue;"> <a href=<%= "profile.jsp?profile=" + request.getParameter("profile") %>>
        <%= request.getParameter("profile") + "'s Profile" %></a></p>

    <img src="<%= usr.getUserpic() %>" alt="<%= usr.getUserName()%>" style="width:90px;height:90px;"/>
    <div class="form">
        <form action="Unfriend" method="post">
            <input type="hidden" name="unfriendTo" value="<%=usr.getUserName() %>">
            <input type="submit" value="Unfriend" />
        </form>
    </div>

    <a href=<%= "showFriends.jsp?profile=" +  usr.getUserName() %>> Friends </a><br>
    <a href=<%= "showHistory.jsp?profile=" +  usr.getUserName() %>> History </a><br>
    <a href=<%= "showUsersQuizes.jsp?profile=" +  usr.getUserName() %>> Quizes </a><br>
    <a href="society.jsp"> Society </a><br>
</nav>
<% } else if (reverseRequested){%>
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

    <a href=<%= "showFriends.jsp?profile=" +  usr.getUserName() %>> Friends </a><br>
    <a href=<%= "showHistory.jsp?profile=" +  usr.getUserName() %>> History </a><br>
    <a href=<%= "showUsersQuizes.jsp?profile=" +  usr.getUserName() %>> Quizes </a><br>
    <a href="society.jsp"> Society </a><br>
</nav>

<% } else { %>
<nav>
    <p style="color:blue;"> <a href=<%= "profile.jsp?profile=" + request.getParameter("profile") %>>
        <%= request.getParameter("profile") + "'s Profile" %></a></p>

    <img src="<%= usr.getUserpic() %>" alt="<%= usr.getUserName()%>" style="width:90px;height:90px;"/>

    <% if (isRequested) { %>
    <div class="form">
        <form action="CancelRequest" method="post">
            <input type="hidden" name="cancelTo" value="<%=usr.getUserName() %>">
            <input type="submit" value="Cancel Request" />
        </form>
    </div>
    <% } else {%>
    <div class="form">
        <form action="AddFriend" method="post">
            <input type="hidden" name="messageTo" value="<%=usr.getUserName() %>">
            <input type="submit" value="Add Friend" />
        </form>
    </div>
    <% } %>

    <a href=<%= "showFriends.jsp?profile=" +  usr.getUserName() %>> Friends </a><br>
    <a href=<%= "showHistory.jsp?profile=" +  usr.getUserName() %>> History </a><br>
    <a href=<%= "showUsersQuizes.jsp?profile=" +  usr.getUserName() %>> Quizes </a><br>
    <a href="society.jsp"> Society </a><br>
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

<aside>
    <input type="search" id="mySearch" placeholder="Search for friends..">
    <input type="submit" onclick="searchFunc()"/>
</aside>

<footer>
    <a href="index.jsp">Home page</a>
</footer>

</body>
