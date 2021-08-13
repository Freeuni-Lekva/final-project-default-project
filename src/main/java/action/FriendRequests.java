package action;

import manager.MessageManager;
import manager.UserManager;
import user.bean.User;
import user.dao.MessagesDao;
import user.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class Messages
 */
@WebServlet("/FriendRequests")
public class FriendRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequests() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * Calling Javascript.
		 * Takes User's friend requests from database and returns
		 * html string to javascript.
		 * 
		 * */
		
		String user = (String)  request.getSession().getAttribute("username");
		UserManager usrM = (UserManager) getServletContext().getAttribute("userM");
		UserDao usrD = usrM.getUserDao();
		User me = null;
		
		MessageManager msgM = (MessageManager) getServletContext().getAttribute("mesM");
		MessagesDao msgD = msgM.getMessageDao();
		List<user.bean.Messages> msg = null;
		try {
			me = usrD.getUserByName(user);
			if (me == null)
				return;
			msg =  msgD.getFriendRequests(me.getUserId());
			HttpSession session = request.getSession();
			session.setAttribute("message_list", msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String resp = "<body>";
		resp += "<h1> You have " + msg.size() + " Friend Request</h1><ul>";
		for (int i = 0; i < msg.size(); i++) {
			try {
				String sender_name = usrD.getUserById(msg.get(i).getSender()).getUserName();
				resp +=  "<li>User: " + sender_name +
						  "<ul><li>" + msg.get(i).getMessage() + 
						  "<div class=\"form\">" +
						  "<form action=\"AcceptRequest\" method=\"post\">" +
						  		"<input type=\"hidden\" name=\"acceptfrom\" value=\"" + sender_name + "\">"+ 
							    "<input type=\"submit\" value=\"Accept\" />"+
						"</form>" +
						"<form action=\"CancelReceivedRequest\" method=\"post\">" +
					  		"<input type=\"hidden\" name=\"cancelTo\" value=\"" + sender_name + "\">"+ 
						    "<input type=\"submit\" value=\"Cancel\" />"+
						 "</form></div></li></ul>";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		resp += "</ul></body>";
	    PrintWriter out = response.getWriter();
	    out.write(resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
