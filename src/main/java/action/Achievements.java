package action;

import manager.UserManager;
import user.bean.Achievement;
import user.dao.AchievementDao;
import user.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class Achievements
 */
@WebServlet("/Achievements")
public class Achievements extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Achievements() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserManager userManager = (UserManager) getServletContext().getAttribute("userM");
		AchievementDao achievementDao = userManager.getAchievementDao();
		UserDao ud = userManager.getUserDao();
		
		String me = (String) request.getSession().getAttribute("username");
		int uid = -1;;
		List<Achievement> achievements = null;
		try {
			uid = ud.getUserByName(me).getUserId();
			achievements = achievementDao.getUserAchievements(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String html = "<h1> Achievements </h1> <br>";
		
		for (Achievement current: achievements) {
			String name = current.getName();
			String text = current.getDescription();
			html += "<div class='achievement'>";
			html += "<div class='ribbon'>"
					+ "<div class='ribbon-stitches-top'>"
					+ "</div>"
					+ "<strong class='ribbon-content'>"
					+ "<h3>" + name + "</h3>"
					+ "<div class='ac_text'>" + text + "</div>"
					+ "</strong>"
					+ "<div class='ribbon-stitches-bottom'>"
					+ "</div></div>";
			html += "</div>";
		}
		
		request.getSession().setAttribute("achievements", html);
		response.sendRedirect("achievements.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
