package org.caowei.notification.service;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.caowei.notification.model.nea.PSIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RestFul Client to retrieve Singapore NEA public data 
 * 
 * @author Cao Wei
 *
 */
@Service
public class NEAClient {

	final static Logger logger = Logger.getLogger(NEAClient.class);
	
	@Autowired
    private NotificationConfiguration config;

	public NotificationConfiguration getConfig() {
		return config;
	}

	public void setConfig(NotificationConfiguration config) {
		this.config = config;
	}

	/**
	 * To Retrieve the NEA public data
	 * @return
	 */
	public List<PSIResult> getPSIUpdates(){
		
		String psiServerEndpint = getConfig().getPsiEndpoint();
		
		logger.info("Connect to " + psiServerEndpint);
		
		String xmlResult = retrieveDataFromServer(psiServerEndpint);
		
		return PSIResult.resolveFromXml(xmlResult);
		
	}
	
	/**
	 * 
	 * @param serverUrl
	 * @param dataSet
	 * @param keyRef
	 * @return
	 */
	private String retrieveDataFromServer(String requestUrl){
		
		if (requestUrl == null || "".equals(requestUrl.trim()))
			throw new IllegalArgumentException("Request URL is null.");
				
		String result = null;
		
		Response response = null;
		try {
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(requestUrl);
			
			response = target.request(MediaType.APPLICATION_XML).get();
			    
			result = response.readEntity(String.class);
		
		} catch (Exception e) {
			logger.error("Error occrus while send the notification Message.",e);
		}
		
		logger.debug("retrieve data via " + requestUrl + " get response " + result);
		
		return result;
	}
	
	public static void main(String[] args){
		NEAClient client = new NEAClient();
		client.retrieveDataFromServer("http://www.nea.gov.sg/api/WebAPI/?dataset=psi_update&keyref=781CF461BB6606ADE5BD65643F1781749CC0E25F35148B10");
	}
}
