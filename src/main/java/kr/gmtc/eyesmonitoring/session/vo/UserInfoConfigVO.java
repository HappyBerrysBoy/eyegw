package kr.gmtc.eyesmonitoring.session.vo;

import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component("userInfoConfig")
public class UserInfoConfigVO {
  private HashMap<String, UserInfoVO> items;
	
	public UserInfoConfigVO() {
		this.items = new HashMap<String, UserInfoVO>();
	}
	
	public HashMap<String, UserInfoVO> getItems() {
		return items;
	}
	public void setItems(HashMap<String, UserInfoVO> items) {
		this.items = items;
	}
}
