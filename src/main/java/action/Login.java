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
 * Servlet implementation class login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Creates main session attributes and saves in servlet context.
		 * */
		String user = request.getParameter("user");
		String password = request.getParameter("pass");
		User acc = new User();
		acc.setUserName(user);
		acc.GenerateHashedPassword(password);
		try {
			DataBase.db = new DataBase();
			UserManager usrM = (UserManager) getServletContext().getAttribute("userM");
			UserDao dao = usrM.getUserDao();
			User found_acc = dao.getUserByName(acc.getUserName());

			HttpSession session = request.getSession();
			if (found_acc != null && found_acc.getHashedPassword().equals(acc.getHashedPassword())) {
				session.setAttribute("authorized", true);

				/* All User List */
				ArrayList<User> all_user = dao.allUserExcept(found_acc.getUserId());
				session.setAttribute("alluser", all_user);
				session.setAttribute("username", user);
				session.setAttribute("image", found_acc.getUserpic());
				session.setAttribute("id", found_acc.getUserId());
		        response.sendRedirect("index.jsp");
			} else {
				session.setAttribute("wronguser", true);
				response.sendRedirect("login.jsp");
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
