package action;

import manager.FriendManager;
import manager.UserManager;
import user.bean.Friends;
import user.bean.User;
import user.dao.UserDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class FriendList
 */
@WebServlet("/FriendList")
public class FriendList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendList() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * Logged User's Friend list.
         * Returns html string to javascript. Displays dynamically.
         *
         * */

        ServletContext sCont = request.getServletContext();
        String user = (String)  request.getSession().getAttribute("username");
        UserManager usrM = null;
        FriendManager frM = null;
        User me = null;
        List<Friends> friends = new ArrayList<Friends>();
        List<User> friend_list = new ArrayList<User>();
        String resp = "";
        try {
            usrM = (UserManager) sCont.getAttribute("userM");
            UserDao uDao = usrM.getUserDao();
            me = usrM.getUserDao().getUserByName(user);
            frM = (FriendManager) sCont.getAttribute("friM");
            friends = frM.getFriendDao().getFriendList(me.getUserId());
            if(friends != null)
                for(int i = 0; i < friends.size(); i++) {
                    User curr = new User();
                    curr = uDao.getUserById(friends.get(i).getFriendId());
                    friend_list.add(curr);
                }

            if(friends.size() == 0) {
                resp += "<h1> You have no friends. </h1>";
            } else {
                resp += "<h1>My Friend List</h1>";
                for (int i = 0; i < friends.size(); i++) {
                    User f =  uDao.getUserById(friends.get(i).getFriendId());
                    String f_name = f.getUserName();
                    resp +="<div class=\"friends\">" + "<a href=\"profile.jsp?profile=" +  f_name + "\">" +
                            f_name + "</a><br>" +
                            "<a href=\"profile.jsp?profile=" + f_name + "\">"+
                            "<img src=\"" + f.getUserpic() + "\" alt=\"" + f_name +"\"" +
                            "style=\"width:70px;height:70px;\"></a></div>";
                }
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