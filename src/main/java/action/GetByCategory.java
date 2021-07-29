package action;

import manager.UserManager;
import quiz.bean.Quiz;
import quiz.dao.QuizDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class GetByCategory
 */
@WebServlet("/GetByCategory")
public class GetByCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetByCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * Servlet is called from Javascript function.
		 * Generates Quiz list by requested category.
		 * Returns html string to Javascript to display dynamically.
		 * */
		
		UserManager uM = (UserManager) getServletContext().getAttribute("userM");
		QuizDao qDao = uM.getQuizDao();
		
		String c = (String) request.getParameter("cat");
		
		try {
			ArrayList<Quiz> bycat = qDao.getQuizByCategory(c);
			String resp = "<div class=\"boxes\">  <p>" + c + " quizes</p> ";
			
			for(int i=0; i<bycat.size(); i++) {
				resp += 
						"<a href=\"startQuiz.jsp?quizid=\" "+  bycat.get(i).getQuizId() + "&quizname=" +bycat.get(i).getQuizName() + "\">" +  (i+1) +". " + bycat.get(i).getQuizName() 
						+ "</a><br>";
			}
			
			resp += "</div> ";
			
			PrintWriter out = response.getWriter();
		    out.write(resp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
