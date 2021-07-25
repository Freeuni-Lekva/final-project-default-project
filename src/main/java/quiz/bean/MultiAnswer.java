package quiz.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiAnswer extends Question {
	
	public MultiAnswer(String question, String type, int questionid, int quizid, String c_answer, int answer_count, 
			int ordered) {
		super(question, type, questionid, quizid, c_answer, answer_count);	
		this.ordered = ordered;
		AnswerList();
	}
	
	public MultiAnswer() {
		super();
	}
	
	public void setIsOrdered(int ordered) {
		this.ordered = ordered;
	}
	
	public int getIsOrderd() {
		return this.ordered;
	}
	
	@Override
	public void setCAnswer(String ans) {
		this.c_answer = ans;
		AnswerList();
	}
	
	private List<String> listed_ans = new ArrayList<String>();
	
	public List<String> getAnswerList() {
		return this.listed_ans;
	}
	
	private void AnswerList() {
		String[] data = this.c_answer.split(";");
		List<String> listed = Arrays.asList(data);
		this.listed_ans = listed;
	}
	
	public boolean checkWord(String w) {
		if(listed_ans.contains(w)) {
			return true;
		}
		return false;
	}
}
