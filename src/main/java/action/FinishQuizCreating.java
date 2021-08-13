package action;

import manager.UserManager;
import quiz.dao.QuizDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class FinishQuizCreating
 */
@WebServlet("/FinishQuizCreating")
public class FinishQuizCreating extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinishQuizCreating() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * Checks in Database that chosen quiz is finished.
		 * 
		 * */
		
		Integer quizid = Integer.parseInt((String)request.getParameter("quizid"));  
		
		UserManager uM = (UserManager)getServletContext().getAttribute("userM");
		QuizDao qzDao = uM.getQuizDao();
		qzDao.setQuizFinished(quizid);
		
	    request.getSession().removeAttribute("quizprocess");
		request.getSession().setAttribute("quizfinished", true);
		response.sendRedirect("createQuiz.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
