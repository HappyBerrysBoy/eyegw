package kr.gmtc.eyesmonitoring.session.vo;

import java.util.Hashtable;

import org.springframework.stereotype.Component;

@Component("userInfoConfig")
public class UserInfoConfigVO {
	private Hashtable<String, UserInfoVO> items;
	
	public UserInfoConfigVO() {
		this.items = new Hashtable<String, UserInfoVO>();
	}
	
	public Hashtable<String, UserInfoVO> getItems() {
		return items;
	}
	public void setItems(Hashtable<String, UserInfoVO> items) {
		this.items = items;
	}
}
