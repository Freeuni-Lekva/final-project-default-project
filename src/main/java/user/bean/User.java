package user.bean;

import action.Hasher;

import java.util.List;

public class User {

	private int user_id;
	private List<Achievement> achievements;
	private String username;
	private String password;
	private String email;
	private String userpic;
	private String hashedpass;
	private int priority;

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

	public void addAchievement(Achievement achievement) {
		this.achievements.add(achievement);
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public void setPassword(String p) {
		this.password = p;
	}

	public void setHashedPassword(String hashedPass) {
		this.hashedpass = hashedPass;
	}

	public void setEMail(String mail) {
		this.email = mail;
	}

	public void setPicURL(String pic_url) {
		this.userpic = pic_url;
	}

	public int getUserId() {
		return this.user_id;
	}

	public String getUserName() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getHashedPassword() {
		return this.hashedpass;
	}

	public String getEMail() {
		return this.email;
	}

	public String getUserpic() {
		return this.userpic;
	}

	public String GenerateHashedPassword(String password) {
		Hasher hashing = new Hasher();
		return this.hashedpass = hashing.getHashedPassword(password);
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}