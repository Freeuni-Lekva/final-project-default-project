package manager;

import connection.DataBase;
import quiz.dao.QuestionDao;
import quiz.dao.QuizDao;
import user.dao.UserDao;

import java.sql.Connection;

public class UserManager {
	protected Connection conn;
	private DataBase db;
	protected UserDao UserD = null;
	protected QuizDao QuizD = null;
	protected QuestionDao QuestionD = null;

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
}
