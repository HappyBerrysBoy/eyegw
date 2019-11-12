package kr.gmtc.eyesmonitoring.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.gmtc.eyesmonitoring.session.SessionManager;
import kr.gmtc.eyesmonitoring.session.vo.UserInfoVO;


@Controller
public class MainController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpSession session) {
		return "index.html";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String home2(Model model, HttpSession session) {
		UserInfoVO userInfo = SessionManager.getInstance().getUserInfo(session);
		
		if(userInfo != null) {
			return "html/layout/layout.html";
		} else {
			return "html/login_error.html";
		}
	}
	
	@RequestMapping(value = "/rest", method = RequestMethod.GET)
	public @ResponseBody String home3(Model model, HttpSession session) {
		return "rest test";
	}

    @RequestMapping(value = "/error", method = RequestMethod.GET)
	public @ResponseBody String error(Model model, HttpSession session) {
		return "rest test";
	}
}
