package action;

import manager.MessageManager;
import manager.UserManager;
import quiz.dao.QuizDao;
import user.bean.User;
import user.dao.MessagesDao;
import user.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class MyChallenges
 */
@WebServlet("/MyChallenges")
public class MyChallenges extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyChallenges() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("username");
        UserManager usrM = (UserManager) getServletContext().getAttribute("userM");
        UserDao usrD = usrM.getUserDao();
        QuizDao qd = usrM.getQuizDao();
        User me = null;

        MessageManager msgM = (MessageManager) getServletContext().getAttribute("mesM");
        MessagesDao msgD = msgM.getMessageDao();
        List<user.bean.Messages> chall = null;
        try {
            me = usrD.getUserByName(user);
            if (me == null)
                return;
            chall =  msgD.getUserChallenges(me.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(chall != null) {
            String resp = "<body>";
            resp += "<h1> You have " + chall.size() + " Challeges</h1><ul>";
            for (int i = 0; i < chall.size(); i++) {
                try {
                    String sender_name = usrD.getUserById(chall.get(i).getSender()).getUserName();
                    String quiz_name = qd.getNameByQuizId(chall.get(i).getQuizId());
                    resp +=  "<li>User: "
                            + "<a href=\"profile.jsp?profile=" + sender_name + "\">"+ sender_name + "'s </a> Best Score: " +
                            qd.getBestScore(chall.get(i).getSender(), chall.get(i).getQuizId()) +
                            "<ul><li>Quiz: "+ "<a href=\"startQuiz.jsp?quizid=" + chall.get(i).getQuizId() + "\">"
                            + quiz_name + "</a><div>" +
                            "<form action=\"DeclineChallenge\" method=\"post\">" +
                            "<input type=\"hidden\" name=\"challengeId\" value=\"" + chall.get(i).getId() + "\">"+
                            "<input type=\"submit\" value=\"Decline\" />"+
                            "</form>"
                            + "<form action=\"AcceptChallenge\" method=\"post\">" +
                            "<input type=\"hidden\" name=\"challengeId\" value=\"" + chall.get(i).getId() + "\">"+
                            "<input type=\"submit\" value=\"Accept\" />"+
                            "</form></div></li></ul>";
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            resp += "</ul></body>";
            PrintWriter out = response.getWriter();
            out.write(resp);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}