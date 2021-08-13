<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html >
<head>
    <title> Admin Login </title>
    <link rel="stylesheet" type="text/css" href="mystyles.css" />
    <script type="text/javascript">
        function newPopup(url) {
            popupWindow = window.open(
                url,'popUpWindow','height=30px, width=50px,left=250,top=150,resizable=no,status=yes')
        }
    </script>
</head>

<body>
<header>
    <h1>Admin Login</h1>
</header>

<section>
    <% if(session.getAttribute("registered") != null) { %>
    <p style="color:blue" > <i>
        <font size="2">You have registered successfully. Please log in. </font>
    </i> </p>
    <% session.removeAttribute("registered"); %>
    <% } %>
    <% if(session.getAttribute("wronguser") != null) { %>
    <p style="color:red" > <i>
        <font size="2">Incorrect credentials or your access level is not enough </font>
    </i> </p>
    <% session.removeAttribute("wronguser"); %>
    <% } %>

    <div class="form">
        <form action="AdminLogin" method="get">
            <input type="text" placeholder="Username" name="user" />
            <input type="password" placeholder="Password" name="pass" />
            <button> Sign in </button>
        </form>
    </div>
</section>

<footer>
    <a href="index.jsp">Home page</a>
</footer>
</body>
</html>