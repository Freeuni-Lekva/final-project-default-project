package manager;

import quiz.bean.History;
import quiz.dao.QuizDao;
import user.dao.AchievementDao;

import java.sql.SQLException;
import java.util.List;

public class AchievementManager {

	private UserManager manager;
	
	public AchievementManager(UserManager manager) {
		this.manager = manager;
	}
	
	public void addAchievement(int user) {
		QuizDao quizDao = manager.getQuizDao();
		AchievementDao achievementDao = manager.getAchievementDao();
		int quizCount = 0;
		try{
			quizCount = quizDao.getQuizByCreator(user).size();
			if (quizCount >= 1) {
				achievementDao.addUserAchievement(user, 1);
			}
			if (quizCount >= 5) {
				achievementDao.addUserAchievement(user, 2);
			}
			if (quizCount >= 10) {
				achievementDao.addUserAchievement(user, 3);
			}
			int history = quizDao.getUserHistory(user).size();
			if (history >= 10) {
				achievementDao.addUserAchievement(user, 4);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addHighScoreAchievement(int user, int quiz) {
		QuizDao quizDao = manager.getQuizDao();
		AchievementDao achievementDao = manager.getAchievementDao();
		
		try {
			List<History> history = quizDao.getTopScores(quiz);
			if (history.size() > 0 && history.get(0).getUser_id() == user) {
				achievementDao.addUserAchievement(user, 5);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
