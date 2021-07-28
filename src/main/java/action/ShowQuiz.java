package action;

import manager.UserManager;
import quiz.bean.Question;
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
 * Servlet implementation class showQuiz
 */
@WebServlet("/ShowQuiz")
public class ShowQuiz extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowQuiz() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * Servlet for showing whole quiz.
         * Shows for creator and able to edit.
         *
         * */
        UserManager uM = (UserManager)getServletContext().getAttribute("userM");

        int quizid = Integer.parseInt((String) request.getParameter("quizid"));

        QuestionDao qDao = uM.getQuestionDao();

        try {
            ArrayList<Question> quesList = qDao.getQuestionsByQuizId(quizid);
            request.setAttribute("questionlist", quesList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("showQuiz.jsp?quizid=" + quizid);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
