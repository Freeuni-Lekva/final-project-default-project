package action;

import manager.UserManager;
import quiz.dao.QuestionDao;
import quiz.dao.QuizDao;
import user.bean.User;
import user.dao.AchievementDao;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class AdminStatistic
 */
@WebServlet("/AdminStatistic")
public class AdminStatistic extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminStatistic() {
        super();
    }

    private List<Statistics> getStatistics(UserManager userManager) {
        QuestionDao questionDao = userManager.getQuestionDao();
        QuizDao quizDao = userManager.getQuizDao();
        UserDao userDao = userManager.getUserDao();
        AchievementDao achievementDao = userManager.getAchievementDao();
        List<Statistics> answer = new ArrayList<>();
        try {
            answer.add(new Statistics("Users", "Total number of users registered",
                    userDao.allUsers().size()));
            answer.add(new Statistics("Quizzes", "Total number of quizzes",
                    quizDao.getQuizList().size()));
            answer.add(new Statistics("Achievements", "Total number of achievements received by users",
                    achievementDao.getAchievementCount()));
            answer.add(new Statistics("Questions", "Total number of questions",
                    questionDao.getQuestionCount()));
            answer.add(new Statistics("Quizzes completed", "Total number of quizzes completed by all users",
                    quizDao.getHistoryCount()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
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

        String html = "";
        String line = "";
        String path = getServletContext().getRealPath("templates/statistics_template.html");
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String itemHTML = "";
        while ((line = reader.readLine()) != null) {
            itemHTML += line;
        }
        reader.close();

        List<Statistics> statList = getStatistics(userManager);
        for (Statistics stat: statList) {
            line = new String(itemHTML);
            line = line.replace("[[:title:]]", stat.getName());
            line = line.replace("[[:value:]]", stat.getValue() + "");
            line = line.replace("[[:description:]]", stat.getDescription());
            html += line;
        }

        response.getWriter().write(html);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private class Statistics {
        String name;
        String description;
        int value;

        public Statistics(String name, String description, int value) {
            this.name = name;
            this.description = description;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getValue() {
            return value;
        }
    }
}