package quiz.bean;

import java.util.Arrays;
import java.util.List;

public class MultipleChoice extends Question {


	public MultipleChoice(String question, String type, int questionid,
			int quizid, String c_answer, int answer_count,  String wronganswers, int ordered) {
		super(question, type, questionid, quizid, c_answer, answer_count);
		this.w_answers = wronganswers;
		this.ordered = ordered;
	}

	public MultipleChoice(String question,String type, int questionid,
			int quizid, String c_answer, int answer_count) {
		super(question, type, questionid, quizid, c_answer, answer_count);
	}

	public MultipleChoice() {
		super();
	}

	public void setWAnswers(String wronganswers) {
		this.w_answers = wronganswers;
	}

	public String getWAnswers() {
		return this.w_answers;
	}
	
	public void setOrdered(int ord) {
		this.ordered = ord;
	}
	
	public int getOrdered() {
		return this.ordered;
	}
	
	public int countWrongAnswers() {
		String[] data = this.w_answers.split(";");
		List<String> listed = Arrays.asList(data);
		return listed.size();
	}
	
	public int countCorrectAnswers() {
		String[] data = this.c_answer.split(";");
		List<String> listed = Arrays.asList(data);
		return listed.size();
	}
	
	public List<String> getWrongAnsweList() {
		String[] data = this.w_answers.split(";");
		List<String> listed = Arrays.asList(data);
		return listed;
	}
	
	public List<String> getCorrectAnsweList() {
		String[] data = this.c_answer.split(";");
		List<String> listed = Arrays.asList(data);
		return listed;
	}
}
