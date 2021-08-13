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
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet implementation class CreateNote
 */
@WebServlet("/CreateNote")
public class CreateNote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNote() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * Note Create servlet.
		 * Called by javscript and returns status, sent or not generated note.
		 * 
		 * */
		String msg_to = (String) request.getParameter("user");
		String text = (String) request.getParameter("note");
		String from_me = (String) request.getSession().getAttribute("username");
		MessageManager msgM = (MessageManager) getServletContext().getAttribute("mesM");
		MessagesDao msgD = msgM.getMessageDao();
		UserManager usrM = (UserManager) getServletContext().getAttribute("userM");
		UserDao usrD = usrM.getUserDao();
		
		User to = null;
		User from = null;
		String resp = "";
		try {
			to = usrD.getUserByName(msg_to);
			from = usrD.getUserByName(from_me);
			if(to != null)  {
				Messages msg = new Messages();
				msg.setSender(from.getUserId());
				msg.setReceiver(to.getUserId());
				msg.setMessage(new String(text));
				msg.setMType("note");
				msgD.addMessage(msg);
				resp = "Note sent to " + to.getUserName();
			}
			else
				resp = "User not found";
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
