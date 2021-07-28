package manager;

import connection.DataBase;
import user.dao.MessagesDao;

import java.sql.Connection;

public class MessageManager {
    protected Connection conn = null;
    private DataBase db;
    protected MessagesDao  msgD = null;

    public MessageManager(DataBase data) throws ClassNotFoundException{
        this.db = data;
        this.conn = db.getConnection();
    }

    public MessagesDao getMessageDao(){
        if(this.msgD == null){
            this.msgD = new MessagesDao(this.conn);
        }
        return this.msgD;
    }
}