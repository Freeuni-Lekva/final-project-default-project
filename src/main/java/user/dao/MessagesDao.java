package user.dao;

import user.bean.Messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessagesDao {
	
	private final Connection conn;
	
	public MessagesDao(Connection conn) {
		this.conn = conn;
	}

	public void addMessage(Messages sms) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Messages (u_from, u_to, message, m_type, quiz_id) VALUE("
				+ sms.getSender() +", " + sms.getReceiver() + ", '" + sms.getMessage() + "', '" 
				+ sms.getMType() + "', " + sms.getQuizId() + ")")) {
			stmt.executeUpdate();
		}
	}
	
	public void addFriendRequest(Messages sms) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Messages (u_from, u_to, message, m_type, seen) VALUE("
				+ sms.getSender() +", " + sms.getReceiver() + ", '" + sms.getMessage() + "', '" 
				+ sms.getMType() + "', 0)")) {
			stmt.executeUpdate();
		}
	}
	
	public void addChallenge(Messages sms) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Messages (u_from, u_to, message, m_type, quiz_id) VALUE("
				+ sms.getSender() +", " + sms.getReceiver() + ", '" + sms.getMessage() + "', '" 
				+ sms.getMType() + "', " + sms.getQuizId() + ")")) {
			stmt.executeUpdate();
		}
	}
	
	public void deleteMessageById(int id) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Messages WHERE id = ?")) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}

	public void deleteMessage(Messages msg) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Messages WHERE u_from = ? AND u_to = ? AND message = ? AND m_type = ?")) {
			stmt.setInt(1, msg.getSender());
			stmt.setInt(2, msg.getReceiver());
			stmt.setString(3, msg.getMessage());
			stmt.setString(4, msg.getMType());
			stmt.executeUpdate();
		}
	}

	public void deleteMessageByQuiz(Messages msg) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Messages WHERE u_from = ? AND u_to = ? AND m_type = ? AND quiz_id = ? ")) {
			stmt.setInt(1, msg.getSender());
			stmt.setInt(2, msg.getReceiver());
			stmt.setString(3, msg.getMType());
			stmt.setInt(4, msg.getQuizId());
			stmt.executeUpdate();
		}
	}
	
	public List<Messages> getUserNotes(int usrId) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Messages WHERE u_to = ? AND m_type = \"note\"")) {
			stmt.setInt(1, usrId);
			try (ResultSet rslt = stmt.executeQuery()) {
				List<Messages> user_messages = new ArrayList<>();
				while(rslt.next()) {
					Messages msg = new Messages();
					msg.setId(rslt.getInt("id"));
					msg.setSender(rslt.getInt("u_from"));
					msg.setReceiver(rslt.getInt("u_to"));
					msg.setMessage(rslt.getString("message"));
					msg.setMType(rslt.getString("m_type"));
					msg.setQuizId(rslt.getInt("quiz_id"));
					user_messages.add(msg);
				}
				return user_messages;
			}
		}
	}
	
	public List<Messages> getUserChallenges(int usrId) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Messages WHERE u_to = ? AND m_type = \"challenge\"")) {
			stmt.setInt(1, usrId);
			try (ResultSet rslt = stmt.executeQuery()) {
				List<Messages> user_messages = new ArrayList<>();
				while(rslt.next()) {
					Messages msg = new Messages();
					msg.setId(rslt.getInt("id"));
					msg.setSender(rslt.getInt("u_from"));
					msg.setReceiver(rslt.getInt("u_to"));
					msg.setMessage(rslt.getString("message"));
					msg.setMType(rslt.getString("m_type"));
					msg.setQuizId(rslt.getInt("quiz_id"));
					user_messages.add(msg);
				}
				return user_messages;
			}
		}
	}
	
	public Messages getMessageById(int id) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Messages WHERE id = " + id + ";")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				if(rslt.next()) {
					Messages msg = new Messages();
					msg.setId(rslt.getInt("id"));
					msg.setSender(rslt.getInt("u_from"));
					msg.setReceiver(rslt.getInt("u_to"));
					msg.setMessage(rslt.getString("message"));
					msg.setMType(rslt.getString("m_type"));
					msg.setQuizId(rslt.getInt("quiz_id"));
					return msg;
				}
				return null;
			}
		}
	}
	
	public List<Messages> getFriendRequests(int usrId) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Messages WHERE u_to = ? AND m_type = \"friendrequest\"")) {
			stmt.setInt(1, usrId);
			try (ResultSet rslt = stmt.executeQuery()) {
				List<Messages> user_messages = new ArrayList<>();
				while(rslt.next()) {
					Messages msg = new Messages();
					msg.setId(rslt.getInt("id"));
					msg.setSender(rslt.getInt("u_from"));
					msg.setReceiver(rslt.getInt("u_to"));
					msg.setMessage(rslt.getString("message"));
					msg.setMType(rslt.getString("m_type"));
					user_messages.add(msg);
				}
				return user_messages;
			}
		}
	}
	
	public List<Messages> getUnseen(int usrId, String type) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Messages WHERE u_to = " + usrId 
				+ " AND m_type = '" + type + "' AND seen = 0;")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				List<Messages> unseen = new ArrayList<>();
				while(rslt.next()) {
					Messages msg = new Messages();
					msg.setId(rslt.getInt("id"));
					msg.setSender(rslt.getInt("u_from"));
					msg.setReceiver(rslt.getInt("u_to"));
					msg.setMessage(rslt.getString("message"));
					msg.setMType(rslt.getString("m_type"));
					unseen.add(msg);
				}
				return unseen;
			}
		}
	}
	
	public void setSeen(int usrId, String type) throws SQLException {
		String sql = "UPDATE TABLE Messages SET seen = 1 WHERE"
				+ " u_to = " + usrId + " AND m_type = '" + type + "'";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
