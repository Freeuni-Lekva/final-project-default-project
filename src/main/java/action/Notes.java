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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class Notes
 */
@WebServlet("/Notes")
public class Notes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notes() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * Servlet is called from Javascript function.
		 * Finds received notes in DataBase.
		 * Generates output html string and sends to Javascript to display dynamically. 
		 *
		 * */
		
		String user = (String) request.getSession().getAttribute("username");
		UserManager usrM = (UserManager) getServletContext().getAttribute("userM");
		UserDao usrD = usrM.getUserDao();
		User me = null;
		
		MessageManager msgM = (MessageManager) getServletContext().getAttribute("mesM");
		MessagesDao msgD = msgM.getMessageDao();
		List<user.bean.Messages> notes = null;
		try {
			me = usrD.getUserByName(user);
			if (me == null)
				return;
			notes =  msgD.getUserNotes(me.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(notes != null) {
			String resp = "<body>";
			resp += "<h1> You have " + notes.size() + " Note</h1><ul>";
			for (int i = 0; i < notes.size(); i++) {
				try {
					String sender_name = usrD.getUserById(notes.get(i).getSender()).getUserName();
					resp +=  "<li>User: "
							 + "<a href=\"profile.jsp?profile=" + sender_name + "\">"+ sender_name + "</a>" +
							  "<ul><li>" + notes.get(i).getMessage() + 
							  "<div>" +
								"<form action=\"DeleteNote\" method=\"post\">" +
							  		"<input type=\"hidden\" name=\"noteId\" value=\"" + notes.get(i).getId() + "\">"+ 
								    "<input type=\"submit\" value=\"Delete\" />"+
								 "</form></div></li></ul>";
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			resp += "</ul></body>";
		    PrintWriter out = response.getWriter();
		    out.write(resp);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
