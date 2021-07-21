package user.dao;

import user.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

	private final Connection conn;

	public UserDao(Connection conn) {
		this.conn = conn;
	}
	
	public void addUser(User usr) throws SQLException {
		Statement stmt = (Statement) conn.createStatement();
		String sql = "INSERT INTO Users (username, pass, e_mail, pic_url, deleted) " + "VALUES('" + usr.getUserName()
		+ "', '" + usr.getHashedPassword()+ "', '" + usr.getEMail() + "', '" + usr.getUserpic() +"', 0)";
		stmt.executeUpdate(sql);
		addUserPriority(getUserByName(usr.getUserName()).getUserId(), 0);
	}
	
	public boolean checkUser(User usr) {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
			stmt.setString(1, usr.getUserName());
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public User getUserByName(String username) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE deleted = 0 AND username = ?")) {
			stmt.setString(1, username);
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					User usr = getUser(rslt);
					return usr;
				}
			}
		}
		return null;
	}
	
	public List<User> allUsers() throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE deleted = 0;")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				List<User> lst = new ArrayList<User>();
				while (rslt.next()) {
					User usr = getUser(rslt);
					lst.add(usr);
				}
				return lst;
			}
		}
	}

	public ArrayList<User> allUserExcept(int id) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users Where deleted = 0 AND user_id != " + id)) {
			try (ResultSet rslt = stmt.executeQuery()) {
				ArrayList<User> lst = new ArrayList<User>();
				while (rslt.next()) {
					User usr = getUser(rslt);
					lst.add(usr);
				}
				return lst;
			}
		}
	}

	public User getUserById(int id) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE user_id = ?")) {
			stmt.setInt(1, id);
			try (ResultSet rslt = stmt.executeQuery()) {
				if(rslt.next()) {
					User usr = getUser(rslt);
					return usr;
				}
				return null;
			}
		}
	}

	public String getHashedPassword(String user) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE deleted = 0 AND username = ?")) {
			stmt.setString(1, user);
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()){
					return rslt.getString("pass");
				}
				return null;
			}
		}
	}
	
	public void updateUser(int id, User user) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET username = ?, e_mail = ? WHERE user_id = ?")) {
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getEMail());
			stmt.setInt(3, id);
			stmt.executeUpdate();
			updatePriority(id, user.getPriority());
		}
	}
	
	public void removeAccount(int user) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET deleted = 1 WHERE user_id = ?")) {
			stmt.setInt(1, user);
			stmt.executeUpdate();
		}
	}
	
	public void addUserPriority(int user, int priority) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO administrators(user_id, priority) VALUES(?, ?)")) {
			stmt.setInt(1, user);
			stmt.setInt(2, priority);
			stmt.executeUpdate();
		}
	}
	
	public void updatePriority(int user, int priority) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("UPDATE administrators SET priority = ? WHERE user_id = ?")) {
			System.out.println(user + " " + priority);
			stmt.setInt(1, priority);
			stmt.setInt(2, user);
			stmt.executeUpdate();
		}
	}
	
	public int getUserPriority(int user) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM administrators WHERE user_id = ?")) {
			stmt.setInt(1, user);
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					int priority = rslt.getInt("priority");
					return priority;
				}
				return 0;
			}
		}
	}

	private User getUser(ResultSet rslt) throws SQLException {
		User user = new User();
		user.setUserId(rslt.getInt("user_id"));
		user.setUserName(rslt.getString("username"));
		user.setHashedPassword(rslt.getString("pass"));
		user.setEMail(rslt.getString("e_mail"));
		user.setPicURL(rslt.getString("pic_url"));
		user.setPriority(getUserPriority(user.getUserId()));
		return user;
	}
}