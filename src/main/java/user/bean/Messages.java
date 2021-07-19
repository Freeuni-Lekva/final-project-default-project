package user.bean;

public class Messages {
	private int id;
	private int receiver;
	private int sender;
	private String message;
	private String m_type;
	private int quiz_id;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	} 
	
	public void setSender(int sender) {
		this.sender = sender;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public void setMType(String m_type) {
		this.m_type = m_type.toLowerCase();
	}
	
	public void setQuizId(int q_id) {
		this.quiz_id = q_id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getReceiver() {
		return this.receiver;
	}
	
	public int getSender() {
		return this.sender;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getMType() {
		return this.m_type.toLowerCase();
	}
	
	public int getQuizId() {
		return this.quiz_id;
	}
	
}
