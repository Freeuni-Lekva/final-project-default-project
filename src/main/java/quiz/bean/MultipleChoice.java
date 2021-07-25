package quiz.bean;

import java.util.Arrays;
import java.util.List;

public class MultipleChoice extends Question {

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
