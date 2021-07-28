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
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class SubmitQuestion
 */
@WebServlet("/EditQuestion")
public class EditQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditQuestion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * Question editing during test view.
		 * Redirects to main page, where is shown all questions.
		 * 
		 * */
		
		UserManager uM = (UserManager) getServletContext().getAttribute("userM");
		QuestionDao qDao = uM.getQuestionDao();
		
		int quizid = Integer.parseInt((String) request.getParameter("quizid"));
				
		String qst = (String) request.getParameter("quest");
		String answ = (String) request.getParameter("cansw");
		int qstid = Integer.parseInt((String)request.getParameter("questid"));
		String wansw = "";
		int ordered = 0;
		int answCount = Integer.parseInt((String)request.getParameter("correctC"));
		
		Question q = qDao.getQuestionById(qstid);
		q.setQuestion(qst);
		q.setCAnswer(answ);
		q.setAnswerCount(answCount);
		
		if(q.getType() == QuestionTypeEnum.PictureResponse) {
			((PictureResponse) q).setPicUrl(request.getParameter("picurl"));
		} else if(q.getType() == QuestionTypeEnum.MultipleChoice || q.getType() == QuestionTypeEnum.MultipleChoiceAnswer) {
			((MultipleChoice) q).setWAnswers(wansw);
			((MultipleChoice) q).setOrdered(ordered);
		} else if(q.getType() == QuestionTypeEnum.MultiAnswer) {
			((MultiAnswer) q).setIsOrdered(ordered);
		}
		
		qDao.updateQuestion(q);
		
		ArrayList<Question> quesList;
		try {
			quesList = qDao.getQuestionsByQuizId(quizid);  		
			request.setAttribute("questionlist", quesList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// wavides mtliani quizis sanaxavad
		
		response.sendRedirect("showQuiz.jsp?quizid=" + quizid);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
