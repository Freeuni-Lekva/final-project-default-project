package quiz.dao;

import quiz.bean.*;

import java.sql.*;
import java.util.ArrayList;

public class QuestionDao {
	private final Connection conn;

	public QuestionDao(Connection conn) {
		this.conn = conn;
	}

	public void addQuestion(Question q) throws SQLException {
		Statement stmt = (Statement) conn.createStatement();
		QuestionTypeEnum type = q.getType();

		if (type  == QuestionTypeEnum.QuestionResponse || type  == QuestionTypeEnum.FillBlank) {
			stmt.executeUpdate(buildFbQrQuery(q));
		}
		else if (type == QuestionTypeEnum.MultipleChoice || type == QuestionTypeEnum.MultipleChoiceAnswer) {
			stmt.executeUpdate(buildMcMcaQuery(q));
		}
		else if (type  == QuestionTypeEnum.MultiAnswer) {
			stmt.executeUpdate(buildMaQuery(q));
		}
		else if (type  == QuestionTypeEnum.PictureResponse) {
			stmt.executeUpdate(buildPrQuery(q));
		}
		else if (type == QuestionTypeEnum.Matching) {
			stmt.executeUpdate(buildMQuery(q));
		}
	}
	
	public void updateQuestion(Question q) {
		String sql = "";
		if (q.getType() == QuestionTypeEnum.QuestionResponse || q.getType() == QuestionTypeEnum.FillBlank) {
			sql = updateFbQrQuery(q);
		} else if(q.getType() == QuestionTypeEnum.PictureResponse) {
			sql = updatePrQuery(q);
		} else if(q.getType() == QuestionTypeEnum.MultipleChoice || q.getType() == QuestionTypeEnum.MultipleChoiceAnswer) {
			sql = updateMcMcaQuery(q);
		} else if(q.getType() == QuestionTypeEnum.MultiAnswer) {
			sql = updateMaQuery(q);
		}
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteQuestionById(int id) throws SQLException {
		try (PreparedStatement stmt = conn
				.prepareStatement("DELETE FROM Questions WHERE id = ?")) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}
	
	public Question getQuestionById(int id) {
		Question q = null;
		try (Statement stmt = conn.createStatement()) {
			try(ResultSet rslt = stmt.executeQuery("SELECT * FROM Questions WHERE id = " + id + ";")) {
				if(rslt.next()) {
					QuestionTypeEnum type = QuestionTypeEnum.values()[rslt.getInt("type")];
					if(type == QuestionTypeEnum.QuestionResponse) {
						q = new QuestionResponse();
					} else if(type == QuestionTypeEnum.FillBlank) {
						q = new FillInTheBlank();
					} else if(type == QuestionTypeEnum.PictureResponse) {
						q = new PictureResponse();
						((PictureResponse) q).setPicUrl(rslt.getString("pic_url"));
					} else if(type == QuestionTypeEnum.MultipleChoice || type == QuestionTypeEnum.MultipleChoiceAnswer) {
						q = new MultipleChoice();
						((MultipleChoice) q).setWAnswers(rslt.getString("w_answer"));
						((MultipleChoice) q).setOrdered(rslt.getInt("ordered"));
					} else if(type == QuestionTypeEnum.MultiAnswer) {
						q = new MultiAnswer();
						((MultiAnswer) q).setIsOrdered(rslt.getInt("ordered"));
					} else if(type == QuestionTypeEnum.Matching) {
						q = new Matching();
					}
					q.setQuestionId(id);
					q.setQuestion(rslt.getString("question"));
					q.setCAnswer(rslt.getString("c_answer"));
					q.setAnswerCount(rslt.getInt("answer_count"));
					q.setType(type);
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return q;

	}
	
	public ArrayList<Question> getQuestionsByQuizId(int id) throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Questions WHERE quiz_id = ?")) {
			stmt.setInt(1, id);
			try (ResultSet rslt = stmt.executeQuery()) {
				ArrayList<Question> question_list = new ArrayList<Question>();
				
				while (rslt.next()) {
					Question curr_quest = null;
					QuestionTypeEnum type = QuestionTypeEnum.values()[rslt.getInt("type")];
					
					if (type == QuestionTypeEnum.PictureResponse)
					{
						curr_quest = new PictureResponse();
						((PictureResponse) curr_quest).setPicUrl(rslt.getString("pic_url"));
					} 
					else if (type == QuestionTypeEnum.MultiAnswer)
					{
						curr_quest = new MultiAnswer();
						((MultiAnswer) curr_quest).setIsOrdered(rslt.getInt("ordered"));
					} 
					else if (type == QuestionTypeEnum.MultipleChoice || type== QuestionTypeEnum.MultipleChoiceAnswer)
					{
						curr_quest = new MultipleChoice();
						((MultipleChoice) curr_quest).setWAnswers(rslt.getString("w_answer"));
					} 
					else if (type == QuestionTypeEnum.QuestionResponse)
					{
						curr_quest = new QuestionResponse();
					}
					else if (type == QuestionTypeEnum.FillBlank) {
						curr_quest = new FillInTheBlank();
					}					
					else if (type == QuestionTypeEnum.Matching) {
						curr_quest = new Matching();
					}
					curr_quest.setQuestionId(rslt.getInt("id"));
					curr_quest.setQuestion(rslt.getString("question"));
					curr_quest.setCAnswer(rslt.getString("c_answer"));
					curr_quest.setAnswerCount(rslt.getInt("answer_count"));
					curr_quest.setQuizId(rslt.getInt("quiz_id"));
					curr_quest.setType(QuestionTypeEnum.values()[rslt.getInt("type")]);
					
					question_list.add(curr_quest);

				}
				return question_list;
			}
		}
	}

	public int getQuestionCount() throws SQLException {
		try (PreparedStatement stmt = conn.prepareStatement("SELECT count(*) as questions FROM Questions")) {
			try (ResultSet rslt = stmt.executeQuery()) {
				if (rslt.next()) {
					return rslt.getInt("questions");
				}
			}
		}
		return 0;
	}

	private String updateFbQrQuery(Question q)
	{
		return "UPDATE Questions SET question='" + q.getQuestion() + "', "
				+ "c_answer='" + q.getCAnswer() +"' WHERE id=" + q.getQuestionId() + ";";
	}

	private String updateMcMcaQuery(Question q)
	{
		return "UPDATE Questions SET question=" + q.getQuestion() + " "
				+ "c_answer=" + q.getCAnswer() + " w_answer= " + ((MultipleChoice) q).getWAnswers()
				+ " answer_count=" + ((MultipleChoice) q).getAnswerCount()
				+" WHERE id=" + q.getQuestionId() + ";";
	}

	private String updateMaQuery(Question q)
	{
		return "UPDATE Questions SET question=" + q.getQuestion() + " "
				+ "c_answer=" + q.getCAnswer() + " ordered=" +  ((MultiAnswer) q).getIsOrderd()
				+ " answer_count=" + ((MultiAnswer) q).getAnswerCount()
				+" WHERE id=" + q.getQuestionId() + ";";
	}

	private String updatePrQuery(Question q)
	{
		return "UPDATE Questions SET question='" + q.getQuestion() + "', "
				+ "c_answer='" + q.getCAnswer() + "', pic_url='" + ((PictureResponse) q).getPicUrl()
				+"' WHERE id=" + q.getQuestionId() + ";";
	}

	private String buildFbQrQuery(Question q)
	{
		return "INSERT INTO Questions (question, type, c_answer, answer_count, quiz_id) "
				+ "VALUES('"
				+ q.getQuestion()
				+ "', '"
				+ q.getType().ordinal()
				+ "', '"
				+ q.getCAnswer()
				+ "', '"
				+ q.getAnswerCount()
				+ "', '"
				+ q.getQuizId() + "')";
	}

	private String buildMcMcaQuery(Question q)
	{
		return "INSERT INTO Questions (question, type, c_answer, w_answer, answer_count, quiz_id) "
				+ "VALUES('"
				+ q.getQuestion()
				+ "', '"
				+ q.getType().ordinal()
				+ "', '"
				+ q.getCAnswer()
				+ "', '"
				+ ((MultipleChoice) q).getWAnswers()
				+ "', '"
				+ q.getAnswerCount() + "', '" + q.getQuizId() + "')";
	}

	private String buildMaQuery(Question q)
	{
		return "INSERT INTO Questions (question, type, c_answer, ordered, answer_count, quiz_id) "
				+ "VALUES('"
				+ q.getQuestion()
				+ "', '"
				+ q.getType().ordinal()
				+ "', '"
				+ q.getCAnswer()
				+ "', '"
				+ ((MultiAnswer) q).getIsOrderd()
				+ "', '"
				+ q.getAnswerCount() + "', '" + q.getQuizId() + "')";
	}

	private String buildPrQuery(Question q)
	{
		return "INSERT INTO Questions (question, type, c_answer, answer_count, pic_url, quiz_id) "
				+ "VALUES('"
				+ q.getQuestion()
				+ "', '"
				+ q.getType().ordinal()
				+ "', '"
				+ q.getCAnswer()
				+ "', '"
				+ q.getAnswerCount()
				+ "', '"
				+ ((PictureResponse) q).getPicUrl()
				+ "', '"
				+ q.getQuizId() + "')";
	}

	public String buildMQuery(Question q)
	{
		return "INSERT INTO Questions (question, type, c_answer, answer_count, quiz_id) "
				+ "VALUES('"
				+ q.getQuestion()
				+ "', '"
				+ q.getType().ordinal()
				+ "', '"
				+ q.getCAnswer()
				+ "', '"
				+ q.getAnswerCount()
				+ "', '"
				+ q.getQuizId() + "')";
	}
}
