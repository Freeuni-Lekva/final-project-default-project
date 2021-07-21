package user.bean;

public class Friends {
	
	private int id;
	private int user_id;
	private int friend_id;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setUserId(int id) {
		this.user_id = id;
	}
	
	public void setFriendId(int id) {
		this.friend_id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getUserId() {
		return this.user_id;
	}
	
	public int getFriendId() {
		return this.friend_id;
	}
}
