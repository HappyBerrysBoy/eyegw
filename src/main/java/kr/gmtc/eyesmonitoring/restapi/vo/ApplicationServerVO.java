package kr.gmtc.eyesmonitoring.restapi.vo;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component("applicationServer")
public class ApplicationServerVO {
	private String name;
	private String ip;
	private String port;
	private HashMap<String, ApplicationServerVO> items;
	
	public ApplicationServerVO() {
		this.items = new HashMap<String, ApplicationServerVO>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public HashMap<String, ApplicationServerVO> getItems() {
		return items;
	}
	public void setItems(HashMap<String, ApplicationServerVO> items) {
		this.items = items;
	}
}
