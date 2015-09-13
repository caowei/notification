package org.caowei.notification.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.caowei.notification.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * PushBullet Client
 * 
 * @author Cao Wei
 *
 */
@Service
public class PushBulletClient {

	 final static Logger logger = Logger.getLogger(PushBulletClient.class);
	
	@Autowired
    private NotificationConfiguration config;

	public NotificationConfiguration getConfig() {
		return config;
	}
	public void setConfig(NotificationConfiguration config) {
		this.config = config;
	}

	/**
	 * Push Message to all my devices
	 * @param title
	 * @param body
	 */
	public MessageResponse pushMessage(String title,String body){
		
		MessageResponse response = new MessageResponse();
		
		if (title == null){
			throw new IllegalArgumentException("Message Title is null.");
		}
		
		if (body == null){
			throw new IllegalArgumentException("Message Body is null.");
		}
		
		if (getConfig() == null){
			throw new RuntimeException("Server is not config properly. Please check Configuration files.");
		}
		
		response = pushMessage(getConfig().getPushServerUrl(),getConfig().getAccessToken(),title,body);
		
		return response;
	}
	
	/**
	 * 
	 * @param accessToken
	 * @param title
	 * @param body
	 */
	private MessageResponse pushMessage(String serverUrl,String accessToken,String title,String body){
		MessageResponse response = new MessageResponse();
		
		logger.debug("About to push message title " + title + " body " + body 
				+ " to " + serverUrl);
		
		/*
		 * Create configuration if necessary
		 * ClientConfig clientConfig = new ClientConfig();
		 */
		Response res = null;
		try {
			
			Client client = ClientBuilder.newClient();
			WebTarget target = client.target(serverUrl);
			Message myMsg = new Message("note",title,body);

			Entity<Message> payload = Entity.entity(myMsg, MediaType.APPLICATION_JSON_TYPE);
			
			res = target.request(MediaType.APPLICATION_JSON_TYPE).header("Access-Token", accessToken)
			    .post(payload);
			
			String stringResponse = res.readEntity(String.class);
			
			response.setStatus(res.getStatus());
			response.setResponseBody(stringResponse);
		
		} catch (Exception e) {
			logger.error("Error occrus while send the notification Message.",e);
		}
		
		if (response.getStatus() == 200){
			logger.info("Message " + title + " was send successfully to " + serverUrl);
		
		} else {
			
			logger.info("Fail to send message to " + serverUrl + " response code " + response.getStatus());
		}
		
		return response;
	}
	
	
}
