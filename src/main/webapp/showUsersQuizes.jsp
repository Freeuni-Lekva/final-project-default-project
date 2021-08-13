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
<%@ page import="quiz.bean.*" %>
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
  <p>- Lorem Ipsum ...</p>
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
  FriendsDao fDao = ((FriendManager)getServletConfig().getServletContext().getAttribute("friM")).getFriendDao();

  try {
    usr = udao.getUserByName(username);
  } catch (SQLException e) {
    e.printStackTrace();
  }
  boolean areFriends = fDao.isFriend(logged_user_id, usr.getUserId());
  boolean isRequested = fDao.isRequested(logged_user_id, usr.getUserId());
  boolean reverseRequested = fDao.isRequested(usr.getUserId(), logged_user_id);

  ArrayList<Friends> frs = (ArrayList<Friends>) fDao.getFriendList(usr.getUserId());

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

  <img src= <%= usr.getUserpic() %> alt=<%= usr.getUserName()%> style="width:90px;height:90px;"/>

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
<%
  UserDao uDao = ((UserManager)getServletConfig().getServletContext().getAttribute("userM")).getUserDao();
  User curr_user = uDao.getUserByName(request.getParameter("profile"));
  QuizDao qDao = ((UserManager)getServletConfig().getServletContext().getAttribute("userM")).getQuizDao();
  ArrayList<Quiz> quizes = qDao.getQuizByCreator(curr_user.getUserId());
%>
<section>
  <div id="content">
    <br>
    <% for (int i=0; i<quizes.size(); i++) { %>
    <%= (i+1) + ". " %> <a href=<%="startQuiz.jsp?quizid=" + quizes.get(i).getQuizId() %>> <%= quizes.get(i).getQuizName() %></a> <br>
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
