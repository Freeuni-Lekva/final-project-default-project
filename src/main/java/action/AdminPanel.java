package action;

import manager.UserManager;
import user.bean.User;
import user.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation class AdminPanel
 */
@WebServlet("/AdminPanel")
public class AdminPanel extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPanel() {
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

        response.sendRedirect("adminpanel.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}