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
<%@ page import="user.bean.*" %>
<%@ page import="manager.*" %>
<%@ page import="user.dao.*" %>

<%
    Integer logged_user_id = (Integer) session.getAttribute("id");
    UserDao uDao = ((UserManager) getServletConfig().getServletContext().getAttribute("userM")).getUserDao();
    User curr_user = uDao.getUserByName(request.getParameter("profile"));
%>

<% if (curr_user == null) { %>
<% session.setAttribute("deactivated", request.getParameter("profile")); %>
<jsp:forward page = "index.jsp" />
<% } else { %>

<%

%>

<header>
    <a href="index.jsp">Quiz Web Site</a>
</header>

<% if(logged_user_id == curr_user.getUserId()) { %>
<jsp:forward page = "index.jsp" />
<% } %>

<nav>
    <p style="color:blue;"> <a href=<%= "profile.jsp?profile=" + request.getParameter("profile") %>>
        <%= request.getParameter("profile") + "'s Profile" %></a></p>

    <img src="<%= curr_user.getUserpic() %>" alt="<%= curr_user.getUserName()%>" style="width:90px;height:90px;"/>

    <div class="form">
        <form action="AcceptRequest" method="post">
            <input type="hidden" name="acceptfrom" value="<%=curr_user.getUserName() %>">
            <input type="hidden" name="returntouser" value="<%=curr_user.getUserName() %>">
            <input type="submit" value="Accept Request" />
        </form>
    </div>

    <a href=<%= "showHistory.jsp?profile=" +  curr_user.getUserName() %>> History </a><br>
    <a href=<%= "showUsersQuizes.jsp?profile=" +  curr_user.getUserName() %>> Quizes </a><br>
    <a href="society.jsp"> Society </a><br>
</nav>

<% } else { %>
<nav>
    <p style="color:blue;"> <a href=<%= "profile.jsp?profile=" + request.getParameter("profile") %>>
        <%= request.getParameter("profile") + "'s Profile" %></a></p>

    <img src="<%= curr_user.getUserpic() %>" alt="<%= curr_user.getUserName()%>" style="width:90px;height:90px;"/>

    <% if (isRequested) { %>
    <div class="form">
        <form action="CancelRequest" method="post">
            <input type="hidden" name="cancelTo" value="<%=curr_user.getUserName() %>">
            <input type="submit" value="Cancel Request" />
        </form>
    </div>
    <% } else {%>
    <div class="form">
        <form action="AddFriend" method="post">
            <input type="hidden" name="messageTo" value="<%=curr_user.getUserName() %>">
            <input type="submit" value="Add Friend" />
        </form>
    </div>
    <% } %>
</nav>
<% } %>

<section>
    <br>
    <br>
    <div class="content">
        <h2> Send Message</h2>
        <form action="SendNote" method="Post">
            <textarea placeholder="Write message..." rows=3 cols=25 name="note"></textarea><br>
            <input type="hidden" name="user" value=<%= curr_user.getUserName() %> />
            <button> Send note </button>
        </form>
    </div>

</section>

<% } %>
<footer>
    <a href="index.jsp">Home page</a>
</footer>
</body>
</html>