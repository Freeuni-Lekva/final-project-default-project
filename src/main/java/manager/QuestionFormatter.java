package manager;

import quiz.bean.*;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionFormatter {
	
	public String QuestionForm(Question q, int pos) {
		String html =  "";
		if (q == null) {
			return html;
		}
		QuestionTypeEnum type = q.getType();
		if(type == QuestionTypeEnum.QuestionResponse) {
			html += qrForm((QuestionResponse)q, pos);
		} else if (type == QuestionTypeEnum.FillBlank) {
			html += fbForm((FillInTheBlank) q, pos);
		} else if (type == QuestionTypeEnum.PictureResponse) {
			html += prForm((PictureResponse)q, pos);
		} else if (type == QuestionTypeEnum.MultipleChoice) {
			html += mcForm((MultipleChoice)q, pos);
		} else if (type == QuestionTypeEnum.MultipleChoiceAnswer) {
			html += mcaForm((MultipleChoice)q, pos);
		} else if (type == QuestionTypeEnum.MultiAnswer) {
			html += maForm((MultiAnswer)q, pos);
		} else if (type == QuestionTypeEnum.Matching) {
			html += mForm((Matching)q, pos);
		}
		return html;
	}
	
	private String qrForm(QuestionResponse q, int pos) {
		String html = "<br> <i>" + (pos+1) + ". " + q.getQuestion() + "</i> <br>" +
				"<textarea rows=\"1\" cols=\"15\" name=" + pos + "></textarea><br>"
				+ "";
		return html;
	}
	
	
	private String fbForm(FillInTheBlank q, int pos) {
		java.util.List<String> answers = q.getCorrectAnswerList();
		String html = "<br> <i>" + (pos+1) + ". " + q.getQuestion() + "</i> <br>";
		String name = pos + "x";
		for(int i=0; i<answers.size(); i++) {
			html +="<textarea rows=\"1\" cols=\"15\" name=" + name + i + "></textarea><br>"
				+ "";
		}
		return html;
	}
	
	
	private String prForm(PictureResponse q, int pos) {
		String html = "<br> <i>" + (pos+1) + ". " + q.getQuestion() + "</i>" +
				"<img src=\"" + q.getPicUrl() + "\" style=\"width:150px;height:150px;\"> </img>" +
				"<textarea rows=\"1\" cols=\"15\" name=" + pos + "></textarea><br>"
				+ "";
		return html;
	}
	

	private String mcForm(MultipleChoice q, int pos) {
		java.util.List<String> answers = q.getCorrectAnsweList();
		java.util.List<String> w_answers = q.getWrongAnsweList();
		
		java.util.List<String> all = new ArrayList<String>();
		all.addAll(answers);
		all.addAll(w_answers);
		
		Collections.shuffle(all);
		
		String html = "<br> <i>" + (pos+1) + ". " + q.getQuestion() + "</i> <br>";
		
		for(int i=0; i<all.size(); i++) {
			html +="<input type=\"radio\" name=\"" + pos + "\" value=\"" + all.get(i) + "\">" +  all.get(i) + "<br>"
				+ "";
		}
		return html;
	}
	
	
	private String mcaForm(MultipleChoice q, int pos) {
		java.util.List<String> canswers = q.getCorrectAnsweList();
		java.util.List<String> wanswers = q.getWrongAnsweList();
		
		java.util.List<String> all = new ArrayList<String>();
		all.addAll(canswers);
		all.addAll(wanswers);
		
		Collections.shuffle(all);
		
		String html = "<br> <i>" + (pos+1) + ". " + q.getQuestion() + "</i> <br>"
				+ "";
		String name = pos +"";
		for(int i=0; i<all.size(); i++) {
			html +="<input type=\"checkbox\" name=\"" + name + "\" value=\"" + all.get(i) + "\">" +  all.get(i) + "<br>"
				+ "";
		}
		html += "";
		return html;
	}
	
	
	private String maForm(MultiAnswer q, int pos) {
		java.util.List<String> answers = q.getAnswerList();
		String html = "<br> <i>" + (pos+1) + ". " + q.getQuestion() + "</i> <br>";
		String name = pos + "x";
		
		for(int i=0; i<answers.size(); i++) {
			html +="<textarea rows=\"1\" cols=\"15\" name=" + name + i + "></textarea>"
				+ "";
		}
		return html;
	}
	
	
	private String mForm(Matching q, int pos) {
		String html = "";
		ArrayList<String> fr = q.getFirstRow();
		ArrayList<String> sr = q.getSecondRow();
		int firstrow = fr.size();
		int secondrow = sr.size();
		String name = pos + "x";
		
		html += "<br><i>" + (pos+1) + ". " + q.getQuestion() + "</i>";
		
		for (int i=0; i< firstrow; i++) {
			html += "<p>" + fr.get(i) ;
			html += "<select name=\""+ name + i + "\">";
			for (int j=0; j< secondrow; j++) {
					html += "<option value=\"" + sr.get(j) +  "\">" + sr.get(j) + "</option>";	
			}
			html += "</select></p>";
			
		}
		return html;
	}
}