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
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * Search for another users.
		 * Calls Javascript.
		 * Returns user's data if found or nothing otherwise.
		 * 
		 * */
		
		String search_name = (String)request.getParameter("searching");
		String myname = (String) request.getSession().getAttribute("username");
		String resp = "";
		
		UserDao uDao = ((UserManager)getServletContext().getAttribute("userM")).getUserDao();
		
		try {
			User found = uDao.getUserByName(search_name);
			if (found != null) {
				if(myname.equals(search_name)){
					resp += "<h1> haha vapshe ar mecineba. </h1>";
				} else {
					resp += "<h1> User Found </h1>";
					resp += "<div class=\"friends\">" +
					"<a href= \"profile.jsp?profile=" + found.getUserName() + 
					"\">" + found.getUserName() +"</a><br>"+
				    "<a href=\"profile.jsp?profile=" + found.getUserName() +"\">" +
				    "<img src=\"" + found.getUserpic() +"\" alt=\""+ found.getUserName() +"\"" +
				    "style=\"width:70px;height:70px;\"></a></div>";
				}
			} else {
				resp += "<h1> User " + search_name  + " not found </h1>";
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
