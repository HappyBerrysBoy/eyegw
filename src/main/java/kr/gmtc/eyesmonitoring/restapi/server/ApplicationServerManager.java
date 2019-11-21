package kr.gmtc.eyesmonitoring.restapi.server;

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
import kr.gmtc.eyesmonitoring.restapi.vo.ApplicationServerVO;

@Component("applicationServerManager")
public class ApplicationServerManager {
	private ApplicationServerVO applicationServer;
	
	public ApplicationServerManager(@Value("${root}") String path, ApplicationServerVO applicationServer) {
		this.applicationServer = applicationServer;
		this.initializeServerFromXML(path);
	}
	
	private void initializeServerFromXML(String path) {
		if (path.equals("")) {
			path = System.getProperty("user.dir");	
		}
		
		File xmlFile = new File(path + "/cfg/ApplicationServer.xml");
		
		if (!xmlFile.exists()) {
			return;
		}

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);

			NodeList mailAccounts = doc.getElementsByTagName("ApplicationServers");
			
			if(mailAccounts.getLength() > 0) {
				Node mailAccount = mailAccounts.item(0);
				
				for (Node child = mailAccount.getFirstChild(); child != null; child = child.getNextSibling()) {
					if (child.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					
					try {
						ApplicationServerVO applicationServer = new ApplicationServerVO();
						Element chiledElement = (Element) child;
						applicationServer.setName(chiledElement.getAttribute("Name"));
						applicationServer.setIp(chiledElement.getAttribute("Ip"));
						applicationServer.setPort(chiledElement.getAttribute("Port"));
						
						this.applicationServer.getItems().put(applicationServer.getName(), applicationServer);
					} catch (Exception e) {
						 GmtLogManager.getInstance().writeLevelLog("[ApplicationServerManager] initializeServerFromXML Error " + GmtUtils.getStatckTrace(e), LogLevelType.LOG_ERROR, "AllLog");
					}
				}
			}
		} catch (Exception e) {
			 GmtLogManager.getInstance().writeLevelLog("[ApplicationServerManager] initializeServerFromXML Error " + GmtUtils.getStatckTrace(e), LogLevelType.LOG_ERROR, "AllLog");
		}
	}
}
