package quiz.bean;

public class Question {
	public Question(String question, QuestionTypeEnum type, int questionid,
					int quizid, String c_answer, int answer_count) {
		this.question = question;
		this.questionId = questionid;
		this.type = type;
		this.quizId = quizid;
		this.answer_count= answer_count;
		this.c_answer = c_answer;
	}
	
	public Question() {
		super();
	}

	protected int questionId;
	protected String question;
	protected String c_answer;

	protected QuestionTypeEnum type;
	protected int quizId;
	protected int answer_count;
	protected String picUrl;
	protected String w_answers;
	protected int ordered;
	
	public void setQuestionId(int id) {
		this.questionId = id;
	} 
	
	public int getQuestionId() {
		return this.questionId;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String qst) {
		this.question = qst;
	}

	public String getCAnswer() {
		return this.c_answer;
	}

	public void setCAnswer(String ans) {
		this.c_answer = ans;
	}

	public void setType(QuestionTypeEnum tp) {
		this.type = tp;
	}
	
	public QuestionTypeEnum getType() {
		return this.type;
	}

	public void setQuizId(int id) {
		this.quizId = id;
	}
	
	public int getQuizId() {
		return this.quizId;
	}
	
	public void setAnswerCount(int c) {
		this.answer_count = c;
	}
	
	public int getAnswerCount() {
		return this.answer_count;
	}
}
