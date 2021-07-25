package quiz.bean;

public class PictureResponse extends Question {

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
