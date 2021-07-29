package connection;

import manager.FriendManager;
import manager.MessageManager;
import manager.UserManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ServletListener
 *
 */
@WebListener
public class ServletListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ServletListener() {
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  {
        ServletContext sc = event.getServletContext();
        DataBase db = new DataBase();

        UserManager userM = null;
        FriendManager friM = null;
        MessageManager mesM = null;
        try {
            userM = new UserManager(db);
            friM = new FriendManager(db);
            mesM = new MessageManager(db);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sc.setAttribute("userM", userM);
        sc.setAttribute("friM", friM);
        sc.setAttribute("mesM", mesM);
    }
}
