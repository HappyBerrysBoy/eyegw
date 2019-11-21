package kr.gmtc.eyesmonitoring.controllers;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gmt.common.type.LogLevelType;
import gmt.common.util.GmtUtils;
import gmt.logger.GmtLogManager;
import kr.gmtc.eyesmonitoring.restapi.vo.ApplicationServerVO;
import kr.gmtc.eyesmonitoring.session.SessionManager;
import kr.gmtc.eyesmonitoring.session.vo.UserInfoVO;

@Controller
public class MainController {
  private final String DEPLOYMODE_PRODUCTION = "production";

  @Resource(name = "applicationServer")
  private ApplicationServerVO applicationServer;

  public MainController(@Value("${root}") String path) {
    GmtLogManager.setpath(path);
  }

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

  @RequestMapping(value = "/serverlist", method = RequestMethod.GET)
  public @ResponseBody HashMap<String, ApplicationServerVO> serverlist() {
    HashMap<String, ApplicationServerVO> list = applicationServer.getItems();
    return list;
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

  @RequestMapping(method = RequestMethod.POST, value = "/tgw/statuses")
  @ResponseBody
  public String searchStatus(HttpSession session, @Value("${deployment.mode}") String deploymentMode,
      @RequestBody String request) {
    if (deploymentMode.equals(this.DEPLOYMODE_PRODUCTION)) { // PRODUCTION MODE
      return this.postGwStatus(request);
    } else { // LOCAL MODE - DEBUG
      String msgId = this.getJsonValue(request, "MsgID");

      switch (msgId) {
      case "1":
        return "{\"result\":[{\"Header\":{\"MsgGroup\":0,\"SendDT\":\"2019-10-22 16:42:27\",\"MsgID\":1},\"Data\":{\"Start\":\"2019-10-22 16:37:14\",\"SendBuffer\":0,\"RecvBuffer\":0}}]}";
      case "3":
        return "{\"result\":[{\"Header\":{\"MsgGroup\":0,\"SendDT\":\"2019-10-22 17:08:51\",\"MsgID\":3},\"Data\":[{\"SC\":[{\"ServicePort\":65112,\"ServiceIP\":\"127.0.0.1\",\"ServiceName\":\"Any Client\",\"Connect\":true,\"SendBuffer\":0,\"ID\":9900}],\"SCCount\":1,\"Use\":false,\"ServerName\":\"RLY Service\",\"SendBuffer\":0,\"ID\":9900,\"ServerPort\":9900},{\"SC\":[],\"SCCount\":0,\"Use\":false,\"ServerName\":\"발신#8030\",\"SendBuffer\":0,\"ID\":8030,\"ServerPort\":8030},{\"SC\":[],\"SCCount\":0,\"Use\":false,\"ServerName\":\"발신#8040\",\"SendBuffer\":0,\"ID\":8031,\"ServerPort\":8031}]}]}";
      case "4":
        return "{\"result\":[{\"Header\":{\"MsgGroup\":0,\"SendDT\":\"2019-10-22 17:43:25\",\"MsgID\":4},\"Data\":[{\"ClientName\":\"LGS\",\"Active\":0,\"IP\":\"127.0.0.1\",\"Port\":9904,\"Connect\":false,\"SendBuffer\":0,\"ID\":9904},{\"ClientName\":\"RLYCLIENT\",\"Active\":0,\"IP\":\"192.168.136.132\",\"Port\":9900,\"Connect\":false,\"SendBuffer\":0,\"ID\":9900},{\"ClientName\":\"CLIENT#1\",\"Active\":0,\"IP\":\"127.0.0.1\",\"Port\":9901,\"Connect\":false,\"SendBuffer\":0,\"ID\":9901},{\"ClientName\":\"UGW\",\"Active\":0,\"IP\":\"192.168.136.131\",\"Port\":9903,\"Connect\":false,\"SendBuffer\":0,\"ID\":9903}]}]}";
      }

      return "";
    }
  }

  /**
   * TSS, Msg2DB등등 16개 Application Server에 접속
   * 
   * @param request
   * @return
   */
  private String postGwStatus(String request) {
    try {
      String applicationServerName = this.getJsonValue(request, "ApplicationServer");
      String ip = "127.0.0.1";
      String port = "8080";

      if (applicationServer.getItems().containsKey(applicationServerName)) {
        ApplicationServerVO restServer = applicationServer.getItems().get(applicationServerName);
        ip = restServer.getIp();
        port = restServer.getPort();
      }

      HttpPost httpPost = new HttpPost(String.format("http://%s:%s/tgw/statuses", ip, port));
      ByteArrayEntity entity = new ByteArrayEntity(request.getBytes(), ContentType.APPLICATION_JSON);
      httpPost.setEntity(entity);

      CloseableHttpClient httpClient = HttpClients.createDefault();
      CloseableHttpResponse response = httpClient.execute(httpPost);
      return EntityUtils.toString(response.getEntity());
    } catch (HttpHostConnectException connEx) { // Heartbeat 처럼 사용 가능
      GmtLogManager.getInstance().writeLevelLog(
          "[MainController] postGwStatus - 서버 통신 불가 Error " + GmtUtils.getStatckTrace(connEx), LogLevelType.LOG_ERROR,
          "AllLog");
    } catch (Exception e) {
      GmtLogManager.getInstance().writeLevelLog("[MainController] postGwStatus Error " + GmtUtils.getStatckTrace(e),
          LogLevelType.LOG_ERROR, "AllLog");
    }

    return "";
  }

  /**
   * 나중에는 삭제 예정 테스트 용도로 사용
   * 
   * @param request
   */
  private String getJsonValue(String request, String keyString) {
    String msgId = "";
    try {
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(request);
      JSONObject json = (JSONObject) obj;

      JSONObject header = (JSONObject) json.get("Header");
      msgId = (String) header.get(keyString);
    } catch (Exception e) {
      GmtLogManager.getInstance().writeLevelLog("[MainController] getJsonValue Error " + GmtUtils.getStatckTrace(e),
          LogLevelType.LOG_ERROR, "AllLog");
    }
    return msgId;
  }
}
