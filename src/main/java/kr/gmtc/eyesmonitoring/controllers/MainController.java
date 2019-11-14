package kr.gmtc.eyesmonitoring.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.gmtc.eyesmonitoring.session.SessionManager;
import kr.gmtc.eyesmonitoring.session.vo.UserInfoVO;

@Controller
public class MainController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String home(HttpSession session) {
    UserInfoVO userInfo = SessionManager.getInstance().getUserInfo(session);

    if (userInfo != null) {
      return "redirect:view";
    } else {
      return "redirect:login";
    }
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(HttpSession session) {
    return "html/login.html";
  }

  @RequestMapping(value = "/view", method = RequestMethod.GET)
  public String view(HttpSession session) {
    UserInfoVO userInfo = SessionManager.getInstance().getUserInfo(session);

    if (userInfo != null) {
      return "html/layout/layout.html";
    } else {
      return "redirect:login";
    }
  }

  @RequestMapping(value = "/rest", method = RequestMethod.GET)
  public @ResponseBody String resttest(HttpSession session) {
    return "rest test";
  }

  @RequestMapping(value = "/error", method = RequestMethod.GET)
  public @ResponseBody String error(HttpSession session) {
    return "rest test";
  }
}
