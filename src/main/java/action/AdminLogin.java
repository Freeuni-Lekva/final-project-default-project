package action;

import connection.DataBase;
import manager.UserManager;
import user.bean.User;
import user.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogin() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getParameter("user");
        String password = (String)request.getParameter("pass");
        User acc = new User();		// Input user object
        acc.setUserName(user);
        acc.GenerateHashedPassword(password);
        try {
            DataBase db = new DataBase();
            DataBase.db = db;
            UserManager usrM = (UserManager) getServletContext().getAttribute("userM");
            UserDao dao = usrM.getUserDao();
            User found_acc = dao.getUserByName(acc.getUserName());

            if (found_acc != null && found_acc.getHashedPassword().equals(acc.getHashedPassword()) && found_acc.getPriority() > 0) {
                HttpSession session = request.getSession();
                session.setAttribute("authorized", true);

                ArrayList<User> all_user = (ArrayList<User>) dao.allUserExcept(found_acc.getUserId());
                /* All User List */
                session.setAttribute("alluser", all_user);
                session.setAttribute("username", user);
                session.setAttribute("image", found_acc.getUserpic());
                session.setAttribute("id", found_acc.getUserId());
                response.sendRedirect("adminpanel.jsp");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("wronguser", true);
                response.sendRedirect("admin.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}