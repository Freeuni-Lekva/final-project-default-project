package action;

import manager.UserManager;
import quiz.bean.*;
import quiz.dao.QuizDao;
import user.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Servlet implementation class SumbitQuiz
 */
@WebServlet("/SubmitQuiz")
public class SubmitQuiz extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitQuiz() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * Servlet for user, who filled chosen quiz.
         * Saves user's history, score, quiz_data.
         *  */

        UserManager um = (UserManager) getServletContext().getAttribute("userM");
        UserDao ud = um.getUserDao();

        String me = (String) request.getSession().getAttribute("username");
        int uid = -1;;
        try {
            uid = ud.getUserByName(me).getUserId();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int qid = Integer.parseInt((String) request.getParameter("quizid"));

        @SuppressWarnings("unchecked")
        ArrayList<Question> qstlist = (ArrayList<Question>) request.getSession().getAttribute("qstlist");
        request.getSession().removeAttribute("qstlist");

        int quest_count = qstlist.size();
        ArrayList<ArrayList<String>> answers = new ArrayList<>();

        for(int i=0; i<quest_count; i++) {
            String ans = "";

            Question q = qstlist.get(i);

            if(q.getType() == QuestionTypeEnum.QuestionResponse)
            {
                ArrayList<String> s = new ArrayList<>();
                ans = (String)request.getParameter(i + "");
                s.add(ans);
                answers.add(s);
            }
            else if(q.getType() == QuestionTypeEnum.FillBlank)
            {
                FillInTheBlank q2 = (FillInTheBlank)q;
                ArrayList<String> s = new ArrayList<>();

                int count = q2.getCorrectAnswerList().size();
                for(int j=0; j<count; j++) {
                    ans = (String)request.getParameter(i + "x" + j);
                    s.add(ans);
                }
                answers.add(s);
            }
            else if(q.getType()  == QuestionTypeEnum.PictureResponse)
            {
                ArrayList<String> s = new ArrayList<>();
                ans = (String)request.getParameter(i + "");
                s.add(ans);
                answers.add(s);
            }
            else if(q.getType() == QuestionTypeEnum.MultipleChoice)
            {
                ArrayList<String> s = new ArrayList<>();
                ans = request.getParameter(i + "");
                s.add(ans);
                answers.add(s);
            }
            else if(q.getType()  == QuestionTypeEnum.MultipleChoiceAnswer)
            {
                ArrayList<String> s = new ArrayList<>();
                String[] checked = request.getParameterValues(i+"");
                if (checked != null) {
                    for(int j=0; j<checked.length; j++) {
                        s.add(checked[j]);
                    }
                }
                answers.add(s);
            }
            else if(q.getType()  == QuestionTypeEnum.MultiAnswer)
            {
                MultiAnswer q2 = (MultiAnswer)q;
                ArrayList<String> s = new ArrayList<>();
                int count = q2.getAnswerCount();
                for(int j=0; j<count; j++) {
                    ans = request.getParameter(i + "x" + j);
                    s.add(ans);
                }
                answers.add(s);
            }
            else if(q.getType()  == QuestionTypeEnum.Matching)
            {
                Matching q2 = (Matching)q;
                ArrayList<String> s = new ArrayList<>();
                int count = q2.getAnswerCount();
                for(int j=0; j<count; j++) {
                    ans = request.getParameter(i + "x" + j);
                    s.add(ans);
                }
                answers.add(s);
            }
        }

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String starttime = (String) request.getSession().getAttribute("starttime");
        request.getSession().removeAttribute("starttime");
        String endtime = dateFormat.format(date);

        Scoring sc = new Scoring();
        int score = sc.countForQuiz(qstlist, answers);
        int duration = SaveData(uid, qid, score, starttime, endtime);

        QuizDao qDao = ((UserManager)getServletContext().getAttribute("userM")).getQuizDao();
        qDao.quizFilled(qid);

        response.sendRedirect("quizFinished.jsp?quizid=" + qid + "&score="+score + "&time=" + duration);
    }

    private int SaveData(int userid, int quizid, int score, String starttime, String endtime) {
        UserManager um = (UserManager) getServletContext().getAttribute("userM");
        QuizDao qd = um.getQuizDao();

        History h = new History();
        h.setQuiz_id(quizid);
        h.setUser_id(userid);
        h.setScore(score);
        h.setStartTime(starttime);
        h.setEndtime(endtime);
        int duration = getSeconds(endtime) - getSeconds(starttime);
        h.setTime(duration);

        qd.addUserHistory(h);
        return duration;
    }

    private int getSeconds(String time) {
        String[] units = time.split(":");
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        int seconds = Integer.parseInt(units[2]);
        int transf = 3600 * hours + 60 * minutes + seconds;
        return transf;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}