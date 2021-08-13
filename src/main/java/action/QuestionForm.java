package action;


import quiz.bean.QuestionTypeEnum;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class AddQuestion
 */
@WebServlet("/QuestionForm")
public class QuestionForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionForm() {
        super();
    }
        
    private int quizid;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* 
		 * Called from javascript function.
		 * Display question form for creator.
		 * */
		QuestionTypeEnum type = QuestionTypeEnum.values()[Integer.parseInt(request.getParameter("type"))];
		String c = (String) request.getParameter("cansc");
		String w = (String) request.getParameter("wansc");
		
		int quizid = Integer.parseInt((String)request.getParameter("quizid"));
		this.quizid = quizid;

		if(c.contains(".")) {
			c = c.substring(0, c.indexOf("."));
		} 
		if (c.charAt(0) == '-') {
			c = c.substring(1);
		}
		if(w.contains(".")) {
			w = w.substring(0, w.indexOf("."));
		}
		if (w.charAt(0) == '-') {
			w = w.substring(1);
		}
		
		int ansc =  Integer.parseInt(c);
		int wansc = Integer.parseInt(w);

		String question = "";
		
		if (type == QuestionTypeEnum.QuestionResponse || type == QuestionTypeEnum.FillBlank) {
			question = General(type); 
		}
		else if(type == QuestionTypeEnum.MultipleChoice) {
			Integer wAnswCount = wansc;
			question = MultipleChoice(wAnswCount, type);
		} 
		else if(type == QuestionTypeEnum.PictureResponse) {
			question += PictureResponse(type);
		} 
		else if(type == QuestionTypeEnum.MultiAnswer){
			Integer cAnswCount = ansc;
			int ordered =  Integer.parseInt((String) request.getParameter("ord"));
			question += MultiAnswer(cAnswCount, ordered, type);
		} 
		else if(type == QuestionTypeEnum.MultipleChoiceAnswer){
			Integer cAnswCount = ansc;
			Integer wAnswCount = wansc;
			question += MultipleChoiceAnswer(cAnswCount, wAnswCount, type);
		} 
		else if(type == QuestionTypeEnum.Matching) {
			Integer cAnswCount = ansc;
			question += Matching(cAnswCount, type);
		}
		
		getServletContext().setAttribute("questionshown", true);
		
        PrintWriter out = response.getWriter();
	    out.write(question);
	}
	
	
	private String General(QuestionTypeEnum tp)
	{
		String html = 
				"<div class=\"form\">" +
					"<form action=\"AddQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ 
						"<i>Question</i>"
						+ "<input type=\"text\" placeholder=\"Write question...\" name=\"quest\" /><br>" +
						"<i>Answer:</i>"
						+ "<input type=\"text\" placeholder=\"Write answer\" name=\"cansw\" /><br>"+
						"<input type=\"hidden\" name=\"correctC\" value=\"1\" />"+
						"<input type=\"hidden\" name=\"wrongC\" value=\"0\" />"+
						"<input type=\"hidden\" name=\"type\" value=\"" + tp.ordinal() + "\" />"+
						"<button> Submit <button>" +
					"</form> " +
			    "<div> <br>";
		return html;
	}
	
	private String PictureResponse(QuestionTypeEnum type)
	{
		String html = 
				"<div class=\"form\">" +
					"<form action=\"AddQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ 
						"<i>Question</i>"
						+ "<input type=\"text\" placeholder=\"Write question...\" name=\"quest\" /><br>" +
						"<i>Picture: </i>"
						+ "<input type=\"text\" placeholder=\"Write picture url...\" name=\"picurl\" /><br>" +
						"<i>Answer:</i>"
						+ "<input type=\"text\" placeholder=\"Write answer\" name=\"cansw\" /><br>"+
						"<input type=\"hidden\" name=\"correctC\" value=\"1\" />"+
						"<input type=\"hidden\" name=\"wrongC\" value=\"0\" />"+
						"<input type=\"hidden\" name=\"type\" value=\"" +  type.ordinal()  + "\" />"+
						"<button> Submit <button>" +
					"</form> " +
			    "<div> <br>";
		return html;
	}
	
	private String MultiAnswer(int cAnswCount, int ordered, QuestionTypeEnum type)
	{
		String html = 
				"<div class=\"form\">"  +
				"<form action=\"AddQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ "<i>Question:  </i>" +
				"<input type=\"text\" placeholder=\"Write question..\" name=\"quest\" /><br>"+
				"<input type=\"hidden\" name=\"type\" value=\"" + type.ordinal() + "\" />"+
				"<input type=\"hidden\" name=\"ordered\" value=\"" + ordered + "\" />"+
				GenerateMultipleCorrect(cAnswCount) + 
				"<button> Submit <button> </form> " +
			    "<div> <br>";
		return html;
	}
	
	private String MultipleChoice(int wAnswCount, QuestionTypeEnum type)
	{
		String html = 
				"<div class=\"form\">"  +
				"<form action=\"AddQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ "<i>Question</i>" +
				"<input type=\"text\" placeholder=\"Write question..\" name=\"quest\" /><br>" +
				"<i>Correct answer:</i><input type=\"text\" p"
				+ "laceholder=\"Correct answer..\" name=\"cansw\" /><br>"+
				"<input type=\"hidden\" name=\"type\" value=\"" + type.ordinal() + "\" />"+
				GenerateMultipleWrong(wAnswCount) + 
				"<button> Submit <button></form> " +
			    "<div> <br>";
		return html;
	}
	
	private String MultipleChoiceAnswer(int cAnswCount, int wAnswCount, QuestionTypeEnum type)
	{
		String html = 
				"<div class=\"form\">"  +
				"<form action=\"AddQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ "<i>Question</i>" +
				"<input type=\"text\" placeholder=\"Write question..\" name=\"quest\" /><br>" +
				"<input type=\"hidden\" name=\"type\" value=\"" + type.ordinal() + "\" />"+
				GenerateMultipleCorrectWrong(cAnswCount, wAnswCount) + 
				"<button> Submit <button></form> " +
			    "<div> <br>";
		return html;
	}
	
	private String Matching(int couples, QuestionTypeEnum type){
		String html = 
				"<div class=\"form\">"  +
				"<form action=\"AddQuestion?quizid=" + this.quizid + "\" method=\"post\">"+ "<i>Question</i>" +
				"<input type=\"text\" placeholder=\"Write question..\" name=\"quest\" /><br>" +
				MultipleCouple(couples) +
				"<input type=\"hidden\" name=\"type\" value=\"" + type.ordinal() + "\" />"+
				"<button> Submit <button></form> " +
			    "<div> <br>";
		return html;
	}
	
	
	private String GenerateMultipleCorrectWrong(int correctC, int wrongC){
		String content = "";
		for (int i=0; i<correctC; i++) 
		{
			content += 
					"<i>Correct answer " + (i+1) + ": </i>" +
					"<input type\"text\" placeholder=\"Correct answer\" name=\"cansw" + (i+1) + "\" "
					+ "id=\"cansw" + (i+1) + "\" /><br>";
		}
		
		for (int i=0; i<wrongC; i++) 
		{
			content += 
					"<i>Wrong answer " + (i+1) + ": </i>" +
					"<input type\"text\" placeholder=\"Wrong answer\" name=\"wansw" + (i+1) + "\" "
					+ "id=\"wansw" + (i+1) + "\" /><br>";
		}
		
		content += "<input type=\"hidden\" name=\"correctC\" value=\"" + correctC + "\" />";
		content += "<input type=\"hidden\" name=\"wrongC\" value=\"" + wrongC + "\" />";
		return content;
	}
	
	
	private String GenerateMultipleCorrect(int count){
		String content = "";
		for (int i=0; i<count; i++) {
			content += 
					"<i>Correct answer " + (i+1) + ": </i>" +
					"<input type\"text\" placeholder=\"Correct answer\" name=\"cansw" + (i+1) + "\" "
					+ "id=\"cansw" + (i+1) + "\" /><br>";
		}
		
		content += "<input type=\"hidden\" name=\"correctC\" value=\"" + count + "\" />";
		content += "<input type=\"hidden\" name=\"wrongC\" value=\"" + 0 + "\" />";
		return content;
	}
	
	private String GenerateMultipleWrong(int count){
		String content = "";
		for (int i=0; i<count; i++) {
			content += 
					"<i>Wrong answer " + (i+1) + ": </i>" +
					"<input type\"text\" placeholder=\"Wrong answer\" name=\"wansw" + (i+1) + "\" "
					+ "id=\"wansw" + (i+1) + "\" /><br>";
		}
		
		content += "<input type=\"hidden\" name=\"correctC\" value=\"" + 1 + "\" />";
		content += "<input type=\"hidden\" name=\"wrongC\" value=\"" +count + "\" />";
		return content;
	}
	
	
	private String MultipleCouple(int count) {
		String content = "";
		for (int i=0; i<count; i++) {
			content += 
					"<i>Couple " + (i+1) + ": </i>" +
					"<input type\"text\" placeholder=\"Devide couple with '-' symbol\" name=\"cansw" + (i+1) + "\" "
					+ "id=\"cansw" + (i+1) + "\" /><br>";
		}
		
		content += "<input type=\"hidden\" name=\"correctC\" value=\"" + count + "\" />";
		return content;
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
