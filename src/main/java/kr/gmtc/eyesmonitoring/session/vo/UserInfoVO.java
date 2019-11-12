package kr.gmtc.eyesmonitoring.session.vo;

public class UserInfoVO  implements Cloneable {
	private String id;
	private String password;
	private boolean isAdmin;
	
	public UserInfoVO() {
		this.isAdmin = false;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
