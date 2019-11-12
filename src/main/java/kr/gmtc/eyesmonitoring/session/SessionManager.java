package kr.gmtc.eyesmonitoring.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.gmtc.eyesmonitoring.session.vo.UserInfoVO;

public class SessionManager {
	private static SessionManager handler = new SessionManager();
	
	public static SessionManager getInstance() {
		return handler;
	}
	
	/**
	 * Set userinfo session
	 * @param request
	 * @param userInfo
	 */
	public void setUserInfo(HttpServletRequest request, UserInfoVO userInfo) {
		HttpSession session = request.getSession(true);
		session.setAttribute("loginInfoSession", userInfo);
	}
	
	/**
	 * Get userinfo session
	 * @param session
	 * @return
	 */
	public UserInfoVO getUserInfo(HttpSession session){
		if(session != null) {
			 Object userInfoObj = (UserInfoVO)session.getAttribute("loginInfoSession");
			 
			 if(userInfoObj != null) {
				 UserInfoVO userInfo = (UserInfoVO)userInfoObj;
				 
				 if(userInfo != null) {
					 return userInfo;
				 }
			 }
		}
		
		return null;
	}
}
