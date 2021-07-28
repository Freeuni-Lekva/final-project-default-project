<%--
  Created by IntelliJ IDEA.
  User: SuperUser
  Date: 7/27/2021
  Time: 11:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>

<%@ page import="java.util.ArrayList" %>
<%@ page import="manager.*" %>
<%@ page import="quiz.dao.*" %>

<%
    UserManager um = (UserManager) getServletConfig().getServletContext().getAttribute("userM");
    QuizDao qd = um.getQuizDao();
    ArrayList<String> q_cat = qd.getCategories();
%>


<select name="category">
    <% for (int i=0; i< q_cat.size(); i++) { %>
    <option value=<%= q_cat.get(i) %>> <%= q_cat.get(i)  %> </option>
    <% } %>
</select>

</body>
