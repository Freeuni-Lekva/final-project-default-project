package quiz.bean;

import java.util.Arrays;
import java.util.List;

/*
 * In this type of questions we must use underscores
 * for blank spaces.
 *
 * Multiple answers must be delimited by the sign ";"
 * */
public class FillInTheBlank extends Question {
	
	public FillInTheBlank() {
		super();
	}
	
	public List<String> getCorrectAnswerList() {
		String[] data = this.c_answer.split(";");
		List<String> listed = Arrays.asList(data);
		return listed;
	}
}
