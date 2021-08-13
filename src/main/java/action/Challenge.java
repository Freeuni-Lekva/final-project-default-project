package action;

import manager.MessageManager;
import manager.UserManager;
import quiz.bean.Quiz;
import quiz.dao.QuizDao;
import user.bean.Messages;
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

/**
 * Servlet implementation class Challenge
 */
@WebServlet("/Challenge")
public class Challenge extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Challenge() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String quiz = (String) request.getParameter("quizid");
        String toname = (String) request.getParameter("user");
        String myname = (String)  request.getSession().getAttribute("username");

        UserManager um = (UserManager) getServletContext().getAttribute("userM");
        QuizDao qd = um.getQuizDao();
        UserDao ud = um.getUserDao();
        MessageManager mm = (MessageManager) getServletContext().getAttribute("mesM");
        MessagesDao md = mm.getMessageDao();

        String resp = "";
        try {
            int myid = ud.getUserByName(myname).getUserId();

            user.bean.User to = ud.getUserByName(toname);
            if(to != null) {
                int toid = to.getUserId();
                int quizid = Integer.parseInt(quiz);
                Quiz q = qd.getQuizById(quizid);

                Messages chell = new Messages();
                chell.setSender(myid);
                chell.setReceiver(toid);
                chell.setMType("challenge");
                chell.setQuizId(quizid);
                chell.setMessage("Challenge from "+toname+"  -  Quiz: "+q.getQuizName());
                md.addChallenge(chell);
                resp = "Challenge sent to "+toname;
            } else {
                resp = "User "+toname+" not found";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        out.write(resp);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}