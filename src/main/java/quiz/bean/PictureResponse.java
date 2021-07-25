package quiz.bean;

public class PictureResponse extends Question {

	public PictureResponse(String question, String type, int questionid, int quizid, String url, String c_answer, int answer_count) {
		super(question, type, questionid, quizid,  c_answer, answer_count);
		this.picurl = url;
	}

	public PictureResponse() {
		super();
	}

	public void setPicUrl(String url) {
		this.picUrl = url;
	}
	
	public String getPicUrl(){
		return this.picUrl;
	}
}
