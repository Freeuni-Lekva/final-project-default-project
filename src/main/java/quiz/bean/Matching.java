package quiz.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matching extends Question {
	public Matching(String question, String type, int questionid, int quizid, String c_answer, int answer_count) {
		super(question, type, questionid, quizid,  c_answer, answer_count );
	}
	
	public Matching() {
		super();
	}
	
	public ArrayList<String> getFirstRow() {
		ArrayList<String> rw = getRow(0);
		return rw;
	}
	
	public ArrayList<String> getSecondRow() {
		ArrayList<String> rw = getRow(1);
		return rw;
	}
	
	private ArrayList<String> getRow(int row) {
		ArrayList<String> rw = new ArrayList<String>();
		String[] matchs = this.c_answer.split(";");
		for(int i=0; i<matchs.length; i++) {
			String[] st = matchs[i].split("-");
			rw.add(st[row]);
		}
		return rw;
	}
	
	public List<String> getCouples() {
		String[] matchs = this.c_answer.split(";");
		List<String> listed = Arrays.asList(matchs);
		return listed;
	}
	
	
}
