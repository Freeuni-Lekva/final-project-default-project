package manager;

import connection.DataBase;
import user.dao.UserDao;

import java.sql.Connection;

public class UserManager {
	protected Connection conn;
	private DataBase db;
	protected UserDao UserD = null;

	public UserManager(DataBase data) throws ClassNotFoundException {
		this.db = data;
		this.conn = db.getConnection();
	}

	public UserDao getUserDao() {
		if (this.UserD == null) {
			this.UserD = new UserDao(conn);
		}
		return this.UserD;
	}
}
