package test.model;

public class UserVO {
	UserInfo user;
	String note;

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public UserVO() {
	}

	public UserVO(UserInfo user, String note) {
		this.user = user;
		this.note = note;
	}

}

