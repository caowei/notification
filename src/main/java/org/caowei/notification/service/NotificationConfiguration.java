package org.caowei.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("NotificationConfiguration")
public class NotificationConfiguration {

	@Value( "${proxy.name}" )
    private String proxyName;
    
    @Value( "${proxy.port}" )
    private int    proxyPort;
    
    @Value( "${proxy.user.name}" )
    private String proxyUserName;
    
    @Value( "${proxy.user.password}" )
    private String proxyUserPassword;
    
    @Value( "${proxy.on}" )
    private String proxyOn;
	
    @Value("${push.server.url}")
	private String pushServerUrl;
    
    @Value("${push.server.access.token}")
	private String accessToken;
    
    @Value("${nea.psi.endpoint}")
    private String psiEndpoint;

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUserName() {
		return proxyUserName;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public String getProxyUserPassword() {
		return proxyUserPassword;
	}

	public void setProxyUserPassword(String proxyUserPassword) {
		this.proxyUserPassword = proxyUserPassword;
	}

	public String getProxyOn() {
		return proxyOn;
	}

	public void setProxyOn(String proxyOn) {
		this.proxyOn = proxyOn;
	}

	public String getPushServerUrl() {
		return pushServerUrl;
	}

	public void setPushServerUrl(String pushServerUrl) {
		this.pushServerUrl = pushServerUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getPsiEndpoint() {
		return psiEndpoint;
	}

	public void setPsiEndpoint(String psiEndpoint) {
		this.psiEndpoint = psiEndpoint;
	}
	
    
}
