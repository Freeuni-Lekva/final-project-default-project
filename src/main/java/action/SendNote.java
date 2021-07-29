package action;

import manager.MessageManager;
import manager.UserManager;
import user.bean.Messages;
import user.bean.User;
import user.dao.MessagesDao;
import user.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation class SendNote
 */
@WebServlet("/SendNote")
public class SendNote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendNote() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Calls Javascript.
		 * Sends note to user. Redirects to profile page.
		 * 
		 * */
		
		StringBuffer text = new StringBuffer(request.getParameter("note"));

	    String msg_to = (String) request.getParameter("user");
		String from_me = (String) request.getSession().getAttribute("username");
		MessageManager msgM = (MessageManager) getServletContext().getAttribute("mesM");
		MessagesDao msgD = msgM.getMessageDao();
		UserManager usrM = (UserManager) getServletContext().getAttribute("userM");
		UserDao usrD = usrM.getUserDao();
		
		User to = null;
		User from = null;
		try {
			to = usrD.getUserByName(msg_to);
			from = usrD.getUserByName(from_me);
			
			Messages msg = new Messages();
			msg.setSender(from.getUserId());
			msg.setReceiver(to.getUserId());
			msg.setMessage(new String(text));
			msg.setMType("note");
			msgD.addMessage(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("profile.jsp?profile="+msg_to);
	}

}
