package kr.gmtc.eyesmonitoring.controllers;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.gmtc.eyesmonitoring.session.SessionManager;
import kr.gmtc.eyesmonitoring.session.vo.UserInfoConfigVO;
import kr.gmtc.eyesmonitoring.session.vo.UserInfoVO;
import kr.gmtc.eyesmonitoring.session.vo.UserParmVO;

@Controller
public class UserPolicyController {
	@Resource(name = "userInfoConfig")
	private UserInfoConfigVO userInfoConfig;
	
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    // @ResponseBody
    public String loginToken(HttpServletRequest request, HttpServletResponse response, UserParmVO parm) throws Exception {
    	if(parm != null) {
    		if(userInfoConfig.getItems().containsKey(parm.getId())) {
    			UserInfoVO serverUserInfo = userInfoConfig.getItems().get(parm.getId());
    			
    			if(serverUserInfo.getPassword().equals(parm.getPassword())) {
    				UserInfoVO copyUserInfo = (UserInfoVO)serverUserInfo.clone();
    				copyUserInfo.setPassword(null); // Password null
    				SessionManager.getInstance().setUserInfo(request, copyUserInfo);
              // return copyUserInfo;
              return "html/layout/layout.html";
    			}
    		}
    	}
    	
   		return null;
    }
    
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    @ResponseBody
    public void loginToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession loginSession = request.getSession(false);
    	
		if(loginSession != null){
			loginSession.invalidate();
		}
		
		Cookie[] cookies = request.getCookies();
	    if (cookies != null){
	        for (Cookie cookie : cookies) {
	            cookie.setValue("");
	            cookie.setPath("/");
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
	        }
		}
    }
}
