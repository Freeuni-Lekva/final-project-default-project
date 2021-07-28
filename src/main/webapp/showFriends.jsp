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
</section>

<% } else { %>

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
<% } %>

<section>
    <div id="content">
        <br>
        <% if(frs.size() == 0) {  %>
        <h2><%= username + " has no friends." %> </h2>
        <% } else { %>
        <h2> <%= username + "'s Friend List" %></h2>
        <% for (int i = 0; i < frs.size(); i++) { %>
        <%User f =  udao.getUserById(frs.get(i).getFriendId());
            String f_name = f.getUserName(); %>

        <div class="friends"> <a href= <%="profile.jsp?profile=" +  f_name %>> <%= f_name %> </a><br>
            <a href= <%="profile.jsp?profile=" + f_name %>>
                <img src="<%=f.getUserpic() %>" alt= "<%= f_name %>"
                     style="width:70px;height:70px;"> </a>
        </div>

        <% } %>
        <% } %>
    </div>
</section>
<% } %>


<footer>
    <a href="index.jsp">Home page</a>
</footer>

</body>