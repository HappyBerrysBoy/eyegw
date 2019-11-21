package kr.gmtc.eyesmonitoring.session;

import java.io.File;

// import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gmt.common.type.LogLevelType;
import gmt.common.util.GmtUtils;
import gmt.logger.GmtLogManager;
// import gmt.common.type.LogLevelType;
// import gmt.common.util.GmtUtils;
// import gmt.logger.GmtLogManager;
import kr.gmtc.eyesmonitoring.session.vo.UserInfoConfigVO;
import kr.gmtc.eyesmonitoring.session.vo.UserInfoVO;

@Component("userInfoManager")
public class UserInfoManager {
	private UserInfoConfigVO userInfoConfig;
	
	 public UserInfoManager(@Value("${root}") String path, UserInfoConfigVO userInfoConfig) {
		this.userInfoConfig = userInfoConfig;
		this.initializeServerFromXML(path);
	}
	
	private void initializeServerFromXML(String path) {
		if (path.equals("")) {
			path = System.getProperty("user.dir");	
		}
		
		File xmlFile = new File(path + "/cfg/UserInfo.xml");
		
		if (!xmlFile.exists()) {
			return;
		}

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);

			NodeList mailAccounts = doc.getElementsByTagName("UserInfos");
			
			if(mailAccounts.getLength() > 0) {
				Node mailAccount = mailAccounts.item(0);
				
				for (Node child = mailAccount.getFirstChild(); child != null; child = child.getNextSibling()) {
					if (child.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					
					try {
						UserInfoVO userInfo = new UserInfoVO();
						Element chiledElement = (Element) child;
						userInfo.setId(chiledElement.getAttribute("Id"));
						userInfo.setPassword(chiledElement.getAttribute("Password"));
						userInfo.setAdmin(Boolean.parseBoolean(chiledElement.getAttribute("IsAdmin")));
						
						this.userInfoConfig.getItems().put(userInfo.getId(), userInfo);
					} catch (Exception e) {
						 GmtLogManager.getInstance().writeLevelLog(
						 		"[UserInfoManager] initializeServerFromXML Error " + GmtUtils.getStatckTrace(e), LogLevelType.LOG_ERROR, "AllLog");
					}
				}
			}
		} catch (Exception e) {
			 GmtLogManager.getInstance().writeLevelLog("[UserInfoManager] initializeServerFromXML Error " + GmtUtils.getStatckTrace(e), LogLevelType.LOG_ERROR, "AllLog");
		}
	}
}
