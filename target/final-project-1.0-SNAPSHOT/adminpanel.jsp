<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
  <title>Free CSS template by ChocoTemplates.com</title>
  <link rel="stylesheet" href="style/admin.css" type="text/css" media="all" />
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
  <script src="http://malsup.github.com/jquery.form.js"></script>
  <script src="js/script.js"></script>
</head>
<body>


<!-- Header -->
<div id="header">
  <div class="shell">

    <div id="head">
      <h1><a href="#">Admin Panel</a></h1>
      <div class="right">
        <p>
          Welcome <a href="index.jsp"><strong><%=	request.getSession().getAttribute("username") %></strong></a>
          <a href="Logout">Logout</a>
        </p>
      </div>
    </div>

    <!-- Navigation -->
    <div id="navigation">
      <ul>
        <li data-target="AdminUser"><a href="#"><span>Users</span></a></li>
        <li data-target="AdminQuiz"><a href="#"><span>Quizzes</span></a></li>
        <li id="adminStatistic" data-target="AdminStatistic"><a href="#" class="active"><span>Statistics</span></a></li>
      </ul>
    </div>
    <!-- End Navigation -->

  </div>
</div>
<!-- End Header -->



<!-- Content -->
<div id="content" class="shell">

</div>

<!-- End Content -->
</div>

<!-- Footer -->
<div id="footer">
</div>
<!-- End Footer -->
</body>
</html>