package quiz.bean;

import java.util.Vector;

public class Quiz {
	public Quiz(String quizname,int quizid,int authorid,Vector<Question> questions) {
		this.quizname = quizname;
		this.quizid = quizid;
		this.authorid = authorid;
		this.questions = questions;
	}
	public Quiz(){
		super();
	}
	protected String quizname;
	protected String description;
	protected int quizid;
	protected int authorid;
	protected int isRandom;
	protected int pages;
	protected int correction;
	protected int practice;
	protected String category;
	protected Vector<Question> questions;
	
	public void setQuizId(int id) {
		this.quizid = id;
	}
	
	public int getQuizId(){
		return this.quizid;
	}
	
	public void setAuthorId(int id){
		this.authorid = id;
	}
	
	public int getAuthorId() {
		return this.authorid;
	}
	
	public void setQuizName(String name) {
		this.quizname = name;
	}
	
	public String getQuizName() {
		return this.quizname;
	}
	
	public void setRandomized(int r) {
		this.isRandom = r;
	}
	
	public boolean isRandomized() {
		return this.isRandom == 1;
	}
	
	public void setCorrection(int c) {
		this.correction = c;
	}
	
	public int getCorrection() {
		return this.correction;
	}
	
	public void setPractice(int p) {
		this.practice = p;
	} 
	
	public int getPractice() {
		return this.practice;
	}
	
	public void setPages(int p) {
		this.pages = p;
	} 
	
	public int getPages() {
		return this.pages;
	}
	
	public void setCategory(String cat) {
		this.category = cat;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public Vector<Question> GetQuestions(){
		return this.questions;
	}
	
	public void setDescription(String descr) {
		this.description = descr;
	}
	
	public String getDescription(){
		return this.description;
	}
}
