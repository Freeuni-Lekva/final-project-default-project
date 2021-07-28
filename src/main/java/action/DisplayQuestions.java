package action;

import manager.UserManager;
import quiz.bean.*;
import quiz.dao.QuestionDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class DisplayQuestions
 */
@WebServlet("/DisplayQuestions")
public class DisplayQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayQuestions() {
        super();
    }
    
    private int quizid;
        
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * Servlet is called by Javascript function.
		 * Generates html string for each type of question.
		 * 
		 * */
				
		UserManager uM = (UserManager) getServletContext().getAttribute("userM");
		QuestionDao qDao = uM.getQuestionDao();
		
		Integer quizid = Integer.parseInt((String)request.getParameter("quizid")); 
		this.quizid = quizid;
		
		ArrayList<Question> qList = null;
		
		try {
			qList = qDao.getQuestionsByQuizId(quizid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String jsp  = "";
		
		
		for (int i=0; i<qList.size(); i++) {
		 	Question curr = qList.get(i); 
			QuestionTypeEnum qType = curr.getType();

			if(qType == QuestionTypeEnum.QuestionResponse ||  qType == QuestionTypeEnum.FillBlank)
			{ 
				jsp += General(curr);
			}
			else if(qType == QuestionTypeEnum.MultipleChoice)
			{ 
				MultipleChoice elem = (MultipleChoice) qList.get(i);
				jsp += MultipleChoice(elem);
			} 
			else if(qType == QuestionTypeEnum.MultipleChoiceAnswer)
			{
				curr = (MultipleChoice) curr;
				jsp += MultipleChoiceAnswer((MultipleChoice)curr);	
			}
			else if(qType == QuestionTypeEnum.MultiAnswer)
			{
				curr = (MultiAnswer) curr;
				jsp += MultiAnswer((MultiAnswer)curr);
			} 
			else if(qType == QuestionTypeEnum.PictureResponse)
			{
				curr = (PictureResponse) curr;
				jsp += PictureResponse((PictureResponse)curr);
			}
			else if(qType == QuestionTypeEnum.Matching)
			{
				jsp += Matching((Matching)curr);
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(jsp);
	}
	
	private String General(Question curr){
		String html = 
				"<div class=\"form\">" +
					"<form action=\"EditQuestion?quizid=" + this.quizid + "\" method=\"post\">"+
						"<i>Question:  </i>"
						+ "<input type=\"text\" value=\""+ curr.getQuestion() +
						"\" name=\"quest\" /><br>" +
						"<i>Answer</i>"
						+ "<input type=\"text\" value=\"" + curr.getCAnswer() +"\" name=\"cansw\" /><br>"+
						"<input type=\"hidden\" name=\"correctC\" value=\"1\" />"+
						"<input type=\"hidden\" name=\"wrongC\" value=\"0\" />"+
						"<input type=\"hidden\" name=\"questid\" value=\"" + curr.getQuestionId() + "\" />"+
						"<button> Edit <button>" +
					"</form> " +
			    "</div> <br>";
		return html;
	}
	
	private String PictureResponse(PictureResponse curr)
	{
		String html = 
				"<div class=\"form\">" +
					"<form action=\"EditQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ 
						"<i>Question</i>"
						+ "<input type=\"text\" value=\"" + curr.getQuestion() + "\" name=\"quest\" /><br>" +
						"<i>Picture: </i>"
						+ "<input type=\"text\" value=\"" + curr.getPicUrl() + "\" name=\"picurl\" /><br>" +
						"<i>Answer:</i>"
						+ "<input type=\"text\" value=\"" + curr.getCAnswer() + "\" name=\"cansw\" /><br>"+
						"<input type=\"hidden\" name=\"correctC\" value=\"1\" />"+
						"<input type=\"hidden\" name=\"wrongC\" value=\"0\" />"+
						"<button> Edit <button>" +
					"</form> " +
			    "</div> <br>";
		return html;
	}
	
	private String MultiAnswer(MultiAnswer curr) 
	{
		String html = 
				"<div class=\"form\">"  +
				"<form action=\"EditQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ "<i>Question:  </i>" +
				"<input type=\"text\" value=\"" + curr.getQuestion() + "\" name=\"quest\" /><br>"+
				"<input type=\"hidden\" name=\"type\" value=\"MA\" />"+
				"<input type=\"hidden\" name=\"ordered\" value=\"" + curr.getIsOrderd() + "\" />"+
				GenerateMultipleCorrect(curr.getAnswerCount(), curr.getAnswerList()) + 
				"<button> Edit <button>" +
				"</form> " +
			    "</div> <br>";
		return html;
	}
	
	private String MultipleChoice(MultipleChoice curr) 
	{
		String html = 
				"<div class=\"form\">"  +
				"<form action=\"EditQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ "<i>Question</i>" +
				"<input type=\"text\" value=\"" + curr.getQuestion() + "\" name=\"quest\" /><br>" +
				"<i>Correct answer:</i><input type=\"text\" "
				+ "value=\"" + curr.getCAnswer() + "\" name=\"cansw\" /><br>"+
				"<input type=\"hidden\" name=\"type\" value=\"MC\" />"+
				GenerateMultipleWrong(curr.countWrongAnswers(), curr.getWrongAnsweList()) + 
				"<button> Edit <button>" +
				"</form> " +
			    "</div> <br>";

		return html;
	}
	
	private String MultipleChoiceAnswer(MultipleChoice curr) 
	{
		String html = 
				"<div class=\"form\">"  +
				"<form action=\"EditQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ "<i>Question</i>" +
				"<input type=\"text\" value=\"" + curr.getQuestion() + "\" name=\"quest\" /><br>" +
				"<input type=\"hidden\" name=\"type\" value=\"MCA\" />"+
				GenerateMultipleCorrectWrong(curr.countCorrectAnswers(), curr.countWrongAnswers(), 
						curr.getCorrectAnsweList(), curr.getWrongAnsweList()) + 
				"<button> Edit <button>"+
				"</form> " +
			    "</div> <br>";

		return html;
	}
	
	
	private String Matching(Matching curr) 
	{
		String html = 
				"<div class=\"form\">"  +
				"<form action=\"EditQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ "<i>Question:  </i>" +
				"<input type=\"text\" value=\"" + curr.getQuestion() + "\" name=\"quest\" /><br>"+
				"<input type=\"hidden\" name=\"type\" value=\"M\" />"+
				MultipleCouple(curr.getAnswerCount(), curr.getCouples()) + 
				"<button> Edit <button>" +
				"</form> " +
			    "</div> <br>";
		return html;
	}
	
	private String MultipleCouple(int count, List<String> ans) {
		String content = "";
		for (int i=0; i<count; i++) {
			content += 
					"<i>Couple " + (i+1) + ": </i>" +
					"<input type\"text\" value=\"" + ans.get(i) + "\" name=\"cansw" + (i+1) + "\" "
					+ "id=\"cansw" + (i+1) + "\" /><br>";
		}
		
		content += "<input type=\"hidden\" name=\"correctC\" value=\"" + count + "\" />";
		return content;
	}
	
	
	private String GenerateMultipleCorrectWrong(int correctC, int wrongC, List<String> correct, List<String> wrong){
		String content = "";
		for (int i=0; i<correctC; i++) 
		{
			content += 
					"<i>Correct answer " + (i+1) + ": </i>" +
					"<input type\"text\" value=\"" + correct.get(i) + "\" name=\"cansw" + (i+1) + "\" "
					+ "id=\"cansw" + (i+1) + "\" /><br>";
		}
		
		for (int i=0; i<wrongC; i++) 
		{
			content += 
					"<i>Wrong answer " + (i+1) + ": </i>" +
					"<input type\"text\" value=\"" + wrong.get(i) + "\" name=\"wansw" + (i+1) + "\" "
					+ "id=\"wansw" + (i+1) + "\" /><br>";
		}
		
		content += "<input type=\"hidden\" name=\"correctC\" value=\"" + correctC + "\" />";
		content += "<input type=\"hidden\" name=\"wrongC\" value=\"" + wrongC + "\" />";
		return content;
	}
	
	
	private String GenerateMultipleCorrect(int count, List<String> answerList){
		String content = "";
		for (int i=0; i<count; i++) {
			content += 
					"<i>Correct answer " + (i+1) + ": </i>" +
					"<input type\"text\" value=\"" + answerList.get(i) + "\" name=\"cansw" + (i+1) + "\" "
					+ "id=\"cansw" + (i+1) + "\" /><br>";
		}
		
		content += "<input type=\"hidden\" name=\"correctC\" value=\"" + count + "\" />";
		content += "<input type=\"hidden\" name=\"wrongC\" value=\"" + 0 + "\" />";
		return content;
	}
	
	private String GenerateMultipleWrong(int count, List<String> wrong){
		String content = "";
		for (int i=0; i<count; i++) {
			content += 
					"<i>Wrong answer " + (i+1) + ": </i>" +
					"<input type\"text\" value=\"" + wrong.get(i) + "\" name=\"wansw" + (i+1) + "\" "
					+ "id=\"wansw" + (i+1) + "\" /><br>";
		}
		
		content += "<input type=\"hidden\" name=\"correctC\" value=\"" + 1 + "\" />";
		content += "<input type=\"hidden\" name=\"wrongC\" value=\"" +count + "\" />";
		return content;
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
