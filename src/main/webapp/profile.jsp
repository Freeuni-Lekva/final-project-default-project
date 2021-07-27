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
    </style>
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

<header>
    <a href="index.jsp">Quiz Web Site</a>
</header>

<% if(logged_user_id == curr_user.getUserId()) { %>
<jsp:forward page = "index.jsp" />
<% } %>

<section>
    <br>
    <br>

</section>

<% } %>
<footer>
    <a href="index.jsp">Home page</a>
</footer>
</body>
</html>
