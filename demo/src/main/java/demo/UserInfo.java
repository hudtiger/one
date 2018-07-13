package demo;

public class UserInfo {
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserInfo(int id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", getClass()=" + getClass() + "]";
	}
	
}
