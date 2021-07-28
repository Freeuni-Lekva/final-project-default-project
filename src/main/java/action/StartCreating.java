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
 * Servlet implementation class StartCreating
 */
@WebServlet("/StartCreating")
public class StartCreating extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartCreating() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * Servlet is called after clicking Start Quiz Create button.
         * Redirects to Types-Page.
         *
         * */
        if (request.getSession().getAttribute("quizprocess") != null)
            request.getSession().removeAttribute("quizprocess");

        UserManager uM = (UserManager) getServletContext().getAttribute("userM");
        QuizDao qzDao = uM.getQuizDao();
        String quizName = request.getParameter("quizName");
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        int isRandom = -1;
        int isFinished = 0;
        int isMultiPage = -1;
        int isCorrected = 0;
        String[] random = request.getParameterValues("isRandom");
        String[] multiP = request.getParameterValues("multiPage");
        String[] correction = request.getParameterValues("correction");
        if (random == null) {
            isRandom = 0;
        } else
            isRandom = 1;

        if (multiP == null) {
            isMultiPage = 0;
        } else
            isMultiPage = 1;

        if (correction == null) {
            isCorrected = 0;
        } else {
            isCorrected = 1;
        }
        Integer authorId = (Integer) request.getSession().getAttribute("id");
        String quizid = "" + qzDao.createNewQuiz(quizName, description, authorId, category, isRandom, isFinished, isMultiPage, isCorrected);

        request.getSession().setAttribute("quizprocess", quizid);
        response.sendRedirect("startQuestionTypes.jsp?quizid=" + quizid); //gadasvla questenebis gasaketebel page-ze
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}