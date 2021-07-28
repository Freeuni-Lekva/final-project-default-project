<%--
  Created by IntelliJ IDEA.
  User: LevanOzashvili
  Date: 7/27/2021
  Time: 11:47 PM
  To change this template use File | Settings | File Templates.
--%>


<input type="submit" id=<%=request.getParameter("quizid") %> value="Question-Response" name="QR" onClick="addQuestion(this)">
<input type="submit" id=<%=request.getParameter("quizid") %> value="Fill-in-the-Blank" name="FB" onClick="addQuestion(this)">
<input type="submit" id=<%=request.getParameter("quizid") %> value="Multiple-Choice" name="MC" onClick="addQuestion(this)">
<input type="submit" id=<%=request.getParameter("quizid") %> value="Matching" name="M" onClick="addQuestion(this)"><br>
<input type="submit" id=<%=request.getParameter("quizid") %> value="Picture-Response" name="PR" onClick="addQuestion(this)">
<input type="submit" id=<%=request.getParameter("quizid") %> value="Multi-Answer" name="MA" onClick="addQuestion(this)">
<input type="submit" id=<%=request.getParameter("quizid") %> value="Multiple-Choice-Answers" name="MCA" onClick="addQuestion(this)"><br>
