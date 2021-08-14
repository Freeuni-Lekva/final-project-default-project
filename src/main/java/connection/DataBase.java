package connection;

import java.sql.*;

/* 
 * A singleton database access.
 */
public class DataBase {
	
	private Connection conn;
	private Statement statement;
	public static DataBase db;
	 
	public DataBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/webdata", "root",
					"Volkswagen123");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		return this.conn;
	}
	public static synchronized DataBase getDbConnection() {
		if(db == null)
			db = new DataBase();
		return db;
	}
 
	public ResultSet runSql(String sql) throws SQLException {
		statement = (Statement) db.conn.createStatement();
		ResultSet res = statement.executeQuery(sql);
		return res;
	}
}
