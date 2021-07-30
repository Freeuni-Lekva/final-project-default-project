package action;

import manager.UserManager;
import quiz.bean.Quiz;
import quiz.dao.QuestionDao;
import quiz.dao.QuizDao;
import user.bean.User;
import user.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class AdminQuiz
 */
@WebServlet("/AdminQuiz")
public class AdminQuiz extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager userManager = (UserManager) getServletContext().getAttribute("userM");

        // Check if user is authorized to access admin panel
        Object authorized = request.getSession().getAttribute("authorized");
        Object userAccess = request.getSession().getAttribute("id");
        if (authorized == null || userAccess == null) {
            response.sendRedirect("admin.jsp");
            request.getSession().setAttribute("authorized", false);
            return;
        }
        boolean isAuthorized = (boolean) authorized;
        int userStr = (int) userAccess;
        if (userStr == 0 || isAuthorized == false) {
            response.sendRedirect("admin.jsp");
            return;
        }
        UserDao dao = userManager.getUserDao();
        User accessUser = null;
        try {
            accessUser = dao.getUserById(userStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (accessUser == null || accessUser.getPriority() == 0) {
            response.sendRedirect("admin.jsp");
            request.getSession().setAttribute("authorized", false);
            return;
        }
        // End of check if user is authorized to access admin panel

        QuizDao quizDao = userManager.getQuizDao();
        UserDao userDao = userManager.getUserDao();

        String html = "";
        try {
            List<Quiz> quizList = quizDao.getQuizList();
            String path = getServletContext().getRealPath("templates/quiz_template.html");
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String itemHTML = "";
            String line;
            while ((line = reader.readLine()) != null) {
                itemHTML += line;
            }
            reader.close();
            for (Quiz quiz: quizList) {
                User user = userDao.getUserById(quiz.getAuthorId());
                line = new String(itemHTML);
                line = line.replace("[[:quiz_id:]]", "" + quiz.getQuizId());
                line = line.replace("[[:author_name:]]", user.getUserName());
                line = line.replace("[[:quiz_name:]]", quiz.getQuizName());
                line = line.replace("[[:quiz_description:]]", quiz.getDescription());
                String text = "";
                if (quiz.isRandomized()) {
                    text = "checked";
                }
                line = line.replace("[[:is_random:]]", text);
                text = "";
                if (quiz.getPages() > 0) {
                    text = "checked";
                }
                line = line.replace("[[:is_multipage:]]", text);
                text = "";
                if (quiz.getCorrection() > 0) {
                    text = "checked";
                }
                line = line.replace("[[:is_correction:]]", text);
                html += line;
            }
            response.getWriter().write(html);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager userManager = (UserManager) getServletContext().getAttribute("userM");
        QuestionDao questionDao = userManager.getQuestionDao();
        QuizDao quizDao = userManager.getQuizDao();
        String quizString = request.getParameter("quiz_id");
        int quizID = Integer.parseInt(quizString);
        String action = request.getParameter("action");
        try {
            if (action != null && action.equals("delete")) {
                quizDao.deleteHistory(quizID);
                questionDao.deleteQuestionByQuizId(quizID);
                quizDao.deleteQuiz(quizID);
                String html = "<div class='message thank-message'>";
                html += String.format("<p> <strong> Quiz %s has been deleted </p> </strong>", quizID);
                html += "</div>";
                response.getWriter().write(html);
                return;
            }
            if (action != null && action.equals("clear")) {
                quizDao.deleteHistory(quizID);
                String html = "<div class='message thank-message'>";
                html += String.format("<p> <strong> History of quiz %s has been cleared </p> </strong>", quizID);
                html += "</div>";
                response.getWriter().write(html);
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String html = "<div class='message error-message'>";
        html += "<p> <strong> Action couldn't be completed </p> </strong>";
        html += "</div>";
        response.getWriter().write(html);
        return;
    }

}