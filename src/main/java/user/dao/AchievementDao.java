package user.dao;

import user.bean.Achievement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AchievementDao {

	private final Connection conn;

	public AchievementDao(Connection conn) {
		this.conn = conn;
		createAchievements();
	}
	
	public void createAchievements() {
		try {
			addNewAchievement(new Achievement(0, "Amateur Author", "Created 1 quiz"));
			addNewAchievement(new Achievement(0, "Prolific Author", "Created 5 quizzes"));
			addNewAchievement(new Achievement(0, "Prodigious Author", "Created 10 quizzes"));
			addNewAchievement(new Achievement(0, "Quiz Machine", "Completed 10 quizzes"));
			addNewAchievement(new Achievement(0, "I am the Greatest", "Scored highest on at least 1 quiz"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Achievement getAchievementByID(int achievement) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM achievements WHERE id = ?")) {
			stmt.setInt(1, achievement);
			try (ResultSet rslt = stmt.executeQuery()) {
				while (rslt.next()) {
					Integer id = rslt.getInt("id");
					String name = rslt.getString("name");
					String text = rslt.getString("description");
					Achievement answer = new Achievement(id, name, text);
					return answer;
				}
			}
		}
		return null;
	}
	
	public List<Achievement> getUserAchievements(int user) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_achievements WHERE user_id = ?")) {
			stmt.setInt(1, user);
			try (ResultSet rslt = stmt.executeQuery()) {
				List<Achievement> answer = new ArrayList<>();
				while (rslt.next()) {
					Integer id = rslt.getInt("achieve_id");
					Achievement newOne = getAchievementByID(id);
					answer.add(newOne);
				}
				return answer;
			}
		}
	}
	
	public boolean hasAchievement(int user, int achievement) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user_achievements WHERE user_id = ? AND achieve_id = ?")) {
			stmt.setInt(1, user);
			stmt.setInt(2, achievement);
			try (ResultSet rslt = stmt.executeQuery()) {
				while (rslt.next()) {
					return true;
				}
				return false;
			}
		}
	}
	
	public void addUserAchievement(int user, int achievement) throws SQLException {
		if (hasAchievement(user, achievement)) {
			return;
		}
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_achievements VALUES(?, ?)")) {
			stmt.setInt(1, user);
			stmt.setInt(2, achievement);
			stmt.executeUpdate();
		}
	}
	
	public boolean achievementExists(Achievement achievement) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM achievements WHERE name = ?")) {
			stmt.setString(1, achievement.getName());
			try (ResultSet rslt = stmt.executeQuery()) {
				while (rslt.next()) {
					return true;
				}
				return false;
			}
		}
	}
	
	public int addNewAchievement(Achievement achievement) throws SQLException {
		if (achievementExists(achievement)) return 0;
		try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO achievements(name, description) VALUES(?, ?)")) {
			stmt.setString(1, achievement.getName());
			stmt.setString(2, achievement.getDescription());
			return stmt.executeUpdate(); 
		}
	}

	public int getAchievementCount() throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT count(*) as achieve_count FROM achievements")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					return rslt.getInt("achieve_count");
				}
			}
		}
		return 0;
	}
	
}
