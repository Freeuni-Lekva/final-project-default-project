package manager;

import java.sql.Connection;

import quiz.dao.QuestionDao;
import quiz.dao.QuizDao;
import user.dao.AchievementDao;
import user.dao.UserDao;
import connection.DataBase;

public class UserManager {
	protected Connection conn;
	private DataBase db;
	protected UserDao UserD = null;
	protected QuizDao QuizD = null;
	protected QuestionDao QuestionD = null;
	protected AchievementDao achievementDao = null;

	public UserManager(DataBase data) throws ClassNotFoundException {
		this.db = data;
		this.conn = db.getConnection();
		this.achievementDao = new AchievementDao(conn);
	}

	public UserDao getUserDao() {
		if (this.UserD == null) {
			this.UserD = new UserDao(conn);
		}
		return this.UserD;
	}

	public QuizDao getQuizDao() {
		if (this.QuizD == null) {
			this.QuizD = new QuizDao(conn);
		}
		return this.QuizD;
	}

	public QuestionDao getQuestionDao() {
		if (this.QuestionD == null) {
			this.QuestionD = new QuestionDao(conn);
		}
		return this.QuestionD;
	}

	public AchievementDao getAchievementDao() {
		if (this.achievementDao == null) {
			this.achievementDao = new AchievementDao(conn);
		}
		return achievementDao;
	}
}