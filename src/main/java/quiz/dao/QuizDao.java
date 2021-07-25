package quiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import quiz.bean.*;


public class QuizDao {
	
	private final Connection conn;
    
	public QuizDao(Connection conn) {
		this.conn = conn;
	}
	
	/*
	 * Delete quiz by id
	 * */
	public void deleteQuiz(int quiz_id) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Quizes WHERE quiz_id = " + quiz_id + "")) {
			stmt.executeUpdate();
		}
	}
	
	/*
	 * Return quiz by id
	 * */
	public Quiz getQuizById(int id) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Quizes WHERE quiz_id = ?")) {
			stmt.setInt(1, id);
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					Quiz curr_quiz = new Quiz();
					curr_quiz.setQuizId(rslt.getInt("quiz_id"));
					curr_quiz.setQuizName(rslt.getString("quiz_name"));
					curr_quiz.setAuthorId(rslt.getInt("author_id"));
					curr_quiz.setDescription(rslt.getString("description"));
					curr_quiz.setCategory(rslt.getString("category"));
					curr_quiz.setRandomized(rslt.getInt("isRandom"));
					curr_quiz.setCorrection(rslt.getInt("correction"));
					curr_quiz.setPages(rslt.getInt("multiPage"));
					curr_quiz.setPractice(rslt.getInt("practice"));
					return curr_quiz;
				}
				return null;
			}
		}
	}
	
	
	public int isMultiPage(int quizid) throws SQLException {
		int isMP = 0;
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Quizes WHERE quiz_id = ?")) {
			stmt.setInt(1, quizid);
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					isMP = rslt.getInt("multiPage");
				}
			}
		}
		return isMP;
	}
	
	/*
	 * Return quiz id by quiz name
	 * */
	public int getQuizId(String quizName) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Quizes WHERE quiz_name = ?")) {
			stmt.setString(1, quizName);
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					return rslt.getInt("quiz_id");
				}
				return -1;
			}
		}
	}
	
	public String getNameByQuizId(int id) throws SQLException {
		String name = "Quiz Not Found";
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Quizes WHERE quiz_id = "
				+ id + ";")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					name = rslt.getString("quiz_name");
				}
			}
		}
		return name;
	}
	
	/*
	 * Return quiz list by Author 
	 * */
	public ArrayList<Quiz> getQuizByCreator(int authorid) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Quizes WHERE author_id = ? AND finished = 1;")) {
			stmt.setInt(1, authorid);
			try (ResultSet rslt = stmt.executeQuery()) {
				ArrayList<Quiz> users_quiz = new ArrayList<>();
				while (rslt.next()) {
					Quiz curr_quiz = new Quiz();
					curr_quiz.setQuizId(rslt.getInt("quiz_id"));
					curr_quiz.setQuizName(rslt.getString("quiz_name"));
					curr_quiz.setDescription(rslt.getString("description"));
					curr_quiz.setAuthorId(rslt.getInt("author_id"));
					users_quiz.add(curr_quiz);
				}
				return users_quiz;
			}
		}
	}
	
	
	public ArrayList<Quiz> getUnfinishedQuizByCreator(int authorid) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Quizes WHERE author_id = ? AND finished = 0;")) {
			stmt.setInt(1, authorid);
			try (ResultSet rslt = stmt.executeQuery()) {
				ArrayList<Quiz> users_quiz = new ArrayList<>();
				while (rslt.next()) {
					Quiz curr_quiz = new Quiz();
					curr_quiz.setQuizId(rslt.getInt("quiz_id"));
					curr_quiz.setQuizName(rslt.getString("quiz_name"));
					curr_quiz.setDescription(rslt.getString("description"));
					curr_quiz.setAuthorId(rslt.getInt("author_id"));
					users_quiz.add(curr_quiz);
				}
				return users_quiz;
			}
		}
	} 
	
	
	/*
	 * Return quiz list by category 
	 * */
	public ArrayList<Quiz> getQuizByCategory(String category) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Quizes WHERE category = ? and finished = 1;")) {
			stmt.setString(1, category);
			try (ResultSet rslt = stmt.executeQuery()) {
				ArrayList<Quiz> categorys_quiz= new ArrayList<>();
				while (rslt.next()) {
					Quiz curr_quiz = new Quiz();
					curr_quiz.setQuizId(rslt.getInt("quiz_id"));
					curr_quiz.setQuizName(rslt.getString("quiz_name"));
					curr_quiz.setDescription(rslt.getString("description"));
					curr_quiz.setAuthorId(rslt.getInt("author_id"));
					categorys_quiz.add(curr_quiz);
				}
				return categorys_quiz;
			}
		}
	}

	public ArrayList<Quiz> getQuizList() throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			try(ResultSet rslt = stmt.executeQuery("SELECT * FROM Quizes WHERE finished = 1;")) {
				ArrayList<Quiz> quiz_list = new ArrayList<>();
				while (rslt.next()) {
					Quiz curr_quiz = new Quiz();
					curr_quiz.setQuizId(rslt.getInt("quiz_id"));
					curr_quiz.setQuizName(rslt.getString("quiz_name"));
					curr_quiz.setDescription(rslt.getString("description"));
					curr_quiz.setAuthorId(rslt.getInt("author_id"));
					curr_quiz.setRandomized(rslt.getInt("isRandom"));
					curr_quiz.setPages(rslt.getInt("multiPage"));
					curr_quiz.setCorrection(rslt.getInt("correction"));
					quiz_list.add(curr_quiz);
				}
				return quiz_list;
			}
		}
	}
	
	
	
	public ArrayList<Quiz> getNewQuizes() throws SQLException {
		try (Statement stmt = conn.createStatement()) {
			try(ResultSet rslt = stmt.executeQuery("SELECT * FROM Quizes WHERE finished = 1 ORDER BY quiz_id DESC;")) {
				ArrayList<Quiz> quiz_list = new ArrayList<>();
				for (int i=0; i<5; i++) {
					if (rslt.next()){
						Quiz curr_quiz = new Quiz();
						curr_quiz.setQuizId(rslt.getInt("quiz_id"));
						curr_quiz.setQuizName(rslt.getString("quiz_name"));
						quiz_list.add(curr_quiz);
					}
				}
				return quiz_list;
			}
		}
	}
	

	public void setQuizFinished(int quizId) {
		String sql = "UPDATE Quizes SET finished=1 where quiz_id = " + quizId + ";";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int createNewQuiz(String quizName, String description, int authorId, String category, int isRandom, int finished, int multiPage) {
			
		try {
			Statement stmt = (Statement) conn.createStatement();
			String sql = "INSERT INTO Quizes(quiz_name, author_id, description, category, multiPage, isRandom, correction, practice, finished, filled)"
				+ "VALUES('" + quizName +"', "+ authorId + ", '" + description + "', '" + category +"', "+ multiPage + ", "  +isRandom + ", "
						+ "0, 0, " + finished + ", 0)";
			stmt.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int lastid = -1;
		try (Statement stmt2 = conn.createStatement()) {
			try(ResultSet rslt = stmt2.executeQuery("SELECT * FROM Quizes WHERE author_id = " + authorId +" "
					+ "ORDER BY quiz_id DESC;")) {
				if(rslt.next()) {
					lastid = rslt.getInt("quiz_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lastid;
	}
	
	public int createNewQuiz(String quizName, String description, int authorId, String category, int isRandom, int finished, int multiPage, int correction) {
		
		try {
			Statement stmt = (Statement) conn.createStatement();
			String sql = "INSERT INTO Quizes(quiz_name, author_id, description, category, multiPage, isRandom, correction, practice, finished)"
				+ "VALUES('" + quizName +"', "+ authorId + ", '" + description + "', '" + category +"', "+ multiPage + ", "  +isRandom + ", "
						+ correction + ", 0, " + finished + ")";
			stmt.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		int lastid = -1;
		try (Statement stmt2 = conn.createStatement()) {
			try(ResultSet rslt = stmt2.executeQuery("SELECT * FROM Quizes WHERE author_id = " + authorId +" "
					+ "ORDER BY quiz_id DESC;")) {
				if(rslt.next()) {
					lastid = rslt.getInt("quiz_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lastid;
	}
	
	public ArrayList<Quiz> getTopQuizes() {
		ArrayList<Quiz> top= new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Quizes WHERE finished = 1 ORDER BY filled DESC;")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				for(int i=0; i<5; i++) {
					if (rslt.next()) {
						Quiz curr_quiz = new Quiz();
						curr_quiz.setQuizId(rslt.getInt("quiz_id"));
						curr_quiz.setQuizName(rslt.getString("quiz_name"));
						curr_quiz.setAuthorId(rslt.getInt("author_id"));
						top.add(curr_quiz);
					}
				}
				return top;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return top;
	}
	
	public ArrayList<History> getRecentTakers(int quizid) {
		ArrayList<History> recents = new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM History WHERE quiz_id = " + quizid + " ORDER BY id DESC;")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				for(int i=0; i<5; i++) {
					if (rslt.next()) {
						History curr = new History();
						curr.setUser_id(rslt.getInt("user_id"));
						curr.setId(rslt.getInt("quiz_id"));
						curr.setScore(rslt.getInt("score"));
						curr.setTime(rslt.getInt("f_time"));
						recents.add(curr);
					}
				}
				return recents;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recents;
	}
	
	public ArrayList<History> getRecentNeverCreated(int userid) {
		ArrayList<History> recents = new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement("select * from quizes where quiz_id in "
				+ "(Select quiz_id from history where user_id = 1) group by quiz_id;")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				for(int i=0; i<5; i++) {
					if (rslt.next()) {
						History curr = new History();
						curr.setUser_id(rslt.getInt("user_id"));
						curr.setId(rslt.getInt("quiz_id"));
						curr.setScore(rslt.getInt("score"));
						curr.setTime(rslt.getInt("f_time"));
						recents.add(curr);
					}
				}
				return recents;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recents;
	}
	
	
	public ArrayList<History> getFriendsLastActivities(int userid) {
		ArrayList<History> recents = new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM History WHERE user_id IN "
				+ " (SELECT friend_id FROM Friends WHERE user_id =  " + userid + ") ORDER BY id DESC;")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				while(rslt.next()) {
					History h = new History();
					h.setUser_id(rslt.getInt("user_id"));
					h.setQuiz_id(rslt.getInt("quiz_id"));
					h.setScore(rslt.getInt("score"));
					h.setTime(rslt.getInt("f_time"));
					recents.add(h);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recents;
	}
	
	
	public ArrayList<History> getTopScores(int quizid) {
		ArrayList<History> top = new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM History WHERE quiz_id = " + quizid +
				" ORDER BY score DESC, f_time;")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				for(int i=0; i<5; i++) {
					if (rslt.next()) {
						History curr = new History();
						curr.setUser_id(rslt.getInt("user_id"));
						curr.setId(rslt.getInt("quiz_id"));
						curr.setScore(rslt.getInt("score"));
						curr.setTime(rslt.getInt("f_time"));
						top.add(curr);
					}
				}
				return top;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return top;
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> cat= new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Categories;")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				while (rslt.next()) {
					String c = rslt.getString("c_name");
					cat.add(c);
				}
				return cat;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("CAT SIZE    " + cat);
		return cat;
	}
	
	public ArrayList<History> getUserHistory(int userid) {
		ArrayList<History> hist= new ArrayList<>();
		
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM History Where user_id = " 
				+ userid + ";")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				while (rslt.next()) {
						History h = new History();
						h.setId(rslt.getInt("id"));
						h.setQuiz_id(rslt.getInt("quiz_id"));
						h.setUser_id(rslt.getInt("user_id"));
						h.setScore(rslt.getInt("score"));
						hist.add(h);
					}
				}
				return hist;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return hist;
	}
	
	
	public void addUserHostory(History h) {
		Statement stmt;
		try {
			stmt = (Statement) conn.createStatement();
			String sql = "INSERT INTO History(user_id, quiz_id, score, f_time, start_time, end_time)" 
					+ "VALUES("+ h.getUser_id() + ", " + h.getQuiz_id() +", "+ h.getScore() + ", " + h.getTime() + ", '"
					+h.getStarttime() + "', '" + h.getEndtime() + "')";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	public int getBestScore(int userid, int quizid) {
		int score = 0;
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM History WHERE user_id = " 
				+ userid + " AND quiz_id = " + quizid + ";")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				while (rslt.next()) {
						int curr = rslt.getInt("score");
						if(curr > score)
							score = curr;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return score;
	}
	
	public void deleteHistory(int quiz) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM History WHERE quiz_id = ?")) {
			stmt.setInt(1, quiz);
			stmt.executeUpdate();
		}
	}

	public int getHistoryCount() throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT count(*) as history_count FROM achievements")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					return rslt.getInt("history_count");
				}
			}
		}
		return 0;
	}
	
	public void quizFilled(int quizid) {
		int was = -1;
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM quizes WHERE quiz_id=" + quizid)) {
			try (ResultSet rslt = stmt.executeQuery()) {
				if(rslt.next()) {
					was = rslt.getInt("filled");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		was = was + 1;
		try (PreparedStatement stmt2 = conn.prepareStatement(""
				+ "UPDATE Quizes SET filled = " +was + " WHERE quiz_id=" +quizid)) {
			stmt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
