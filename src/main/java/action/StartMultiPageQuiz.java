package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manager.AchievementManager;
import manager.QuestionFormatter;
import manager.UserManager;
import quiz.bean.*;
import quiz.dao.QuestionDao;
import quiz.dao.QuizDao;
import user.dao.UserDao;

/**
 * Servlet implementation class StartMultiPageQuiz
 */
@WebServlet("/StartMultiPageQuiz")
public class StartMultiPageQuiz extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartMultiPageQuiz() {
        super();
    }

    private ArrayList<String> getAnswerType(HttpServletRequest request, Question q, int i) {
        String ans;
        ArrayList<String> s = new ArrayList<>();
        if(q.getType() == QuestionTypeEnum.QuestionResponse)
        {
            ans = (String)request.getParameter(i + "");
            s.add(ans);
        }
        else if(q.getType() == QuestionTypeEnum.FillBlank)
        {
            FillInTheBlank q2 = (FillInTheBlank)q;
            int count = q2.getCorrectAnswerList().size();
            for(int j=0; j<count; j++) {
                ans = (String)request.getParameter(i + "x" + j);
                s.add(ans);
            }
        }
        else if(q.getType() == QuestionTypeEnum.PictureResponse)
        {
            ans = (String)request.getParameter(i + "");
            s.add(ans);
        }
        else if(q.getType() == QuestionTypeEnum.MultipleChoice)
        {
            ans = request.getParameter(i + "");
            s.add(ans);
        }
        else if(q.getType() == QuestionTypeEnum.MultipleChoiceAnswer)
        {
            String[] checked = request.getParameterValues(i+"");
            if (checked != null) {
                for(int j=0; j<checked.length; j++) {
                    s.add(checked[j]);
                }
            }
        }
        else if(q.getType() == QuestionTypeEnum.MultiAnswer)
        {
            MultiAnswer q2 = (MultiAnswer)q;
            int count = q2.getAnswerCount();
            for(int j=0; j<count; j++) {
                ans = request.getParameter(i + "x" + j);
                s.add(ans);
            }
        }
        else if(q.getType() == QuestionTypeEnum.Matching)
        {
            Matching q2 = (Matching)q;
            int count = q2.getAnswerCount();
            for(int j=0; j<count; j++) {
                ans = request.getParameter(i + "x" + j);
                s.add(ans);
            }
        }
        return s;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserManager manager = (UserManager) getServletContext().getAttribute("userM");
        QuizDao quizDao = manager.getQuizDao();
        QuestionDao questionDao = manager.getQuestionDao();

        Integer quizID = Integer.parseInt((String) request.getParameter("quizid"));
        Scoring sc = new Scoring();

        boolean displayMessage = false;
        boolean correctAnswer = false;

        String questionStr = request.getParameter("question");
        Integer questionNumber = 0;
        if (questionStr != null) {
            questionNumber = Integer.parseInt(questionStr);
        }

        List<Question> questionList = null;
        List<Integer> questionIDList = (List<Integer>) request.getSession().getAttribute("question_ids");
        ArrayList<ArrayList<String> > answers = (ArrayList<ArrayList<String>>) request.getSession().getAttribute("answers");
        Question current = null;
        Quiz quiz = null;
        if (questionNumber == 0 || questionIDList == null) {
            questionIDList = new ArrayList<>();
            answers = new ArrayList<>();
            try {
                questionList = questionDao.getQuestionsByQuizId(quizID);
                quiz = quizDao.getQuizById(quizID);

                if (quiz.isRandomized()) {
                    Collections.shuffle(questionList);
                }

                for (Question question: questionList) {
                    questionIDList.add(question.getQuestionId());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();

            request.getSession().setAttribute("starttime", dateFormat.format(date));

        } else {
            try {
                quiz = quizDao.getQuizById(quizID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            questionList = new ArrayList<>();
            for (Integer qID: questionIDList) {
                questionList.add(questionDao.getQuestionById(qID));
            }

            current = questionList.get(questionNumber - 1);

            ArrayList<String> currentAnswer = getAnswerType(request, current, questionNumber-1);
            int ordered = 0;
            if (current.getType() == QuestionTypeEnum.MultiAnswer) {
                ordered = ((MultiAnswer)current).getIsOrderd();
            }
            if (quiz.getCorrection() == 1) {
                displayMessage = true;
            }
            correctAnswer = false;
            int score = sc.getScore(current.getCAnswer(), currentAnswer, current.getType(), ordered);
            if (score > 0) {
                correctAnswer = true;
            }
            answers.add(currentAnswer);
        }

        QuestionFormatter formatter = new QuestionFormatter();
        if (questionNumber < questionList.size()) {
            current = questionList.get(questionNumber);
        }
        String html = "";

        html += "<br>\n";
        if (displayMessage) {
            if (correctAnswer) {
                html += String.format("<span style='background: #00FF00;'> Your answer for question number %s was correct </span>", questionNumber);
            } else {
                html += String.format("<span style='background: RED;'> Your answer for question number %s was incorrect </span>", questionNumber);
            }
        }
        html += "<form id=\"callForm\" onsubmit=\"return false;\">";
        html += formatter.QuestionForm(current, questionNumber);
        html += String.format("<input id=\"%s\" name=\"%s\" value=%s type=\"hidden\" /> <br>\n", "quizid", "quizid", quizID);
        html += String.format("<input id=\"%s\" name=\"%s\" value=%s type=\"hidden\" /> <br>\n", "question", "question", questionNumber + 1);
        if (questionNumber + 1 < questionList.size()) {
            html += "<button onclick=\"multiPageAdvance()\"> Next </button>\n";
        } else {
            html += "<button onclick=\"multiPageFinish()\"> Finish </button>\n";
        }
        html += "</form>";

        if (questionNumber >= questionList.size()) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String starttime = (String) request.getSession().getAttribute("starttime");
            request.getSession().removeAttribute("starttime");
            String endtime = dateFormat.format(date);

            UserDao ud = manager.getUserDao();

            String me = (String) request.getSession().getAttribute("username");
            int uid = -1;;
            try {
                uid = ud.getUserByName(me).getUserId();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int score = sc.countForQuiz((ArrayList<Question>) questionList, answers);
            int duration = SaveData(uid, quizID, score, starttime, endtime);

            AchievementManager achieveManager = new AchievementManager(manager);
            achieveManager.addHighScoreAchievement(uid, quizID);
            request.getSession().removeAttribute("answers");
            request.getSession().removeAttribute("question_ids");
            response.sendRedirect("quizFinished.jsp?quizid=" + quizID + "&score="+score + "&time=" + duration);
            html = "";
            return;
        }

        request.getSession().setAttribute("answers", answers);
        request.getSession().setAttribute("question_ids", questionIDList);

        PrintWriter out = response.getWriter();
        out.write(html);
        return;

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
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
