package org.caowei.notification.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.caowei.notification.batch.BatchServer;
import org.caowei.notification.batch.PSIUpdateJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	final static Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private BatchServer batchServer;
	
	public BatchServer getBatchServer() {
		return batchServer;
	}
	public void setBatchServer(BatchServer batchServer) {
		this.batchServer = batchServer;
	}


	@RequestMapping(value="/status",method = RequestMethod.GET)
	public String viewStatus(Map<String, Object> model) {

		logger.info("Accessing Notification Home Page.");

		model.put("isRun", getBatchServer().isRuning());
		
		return "home";
	}
	
	@RequestMapping(value="/startJobServer",method = RequestMethod.GET)
	public String startJobServer(Map<String, Object> model) {

		logger.info("Start Job Server.");

		if (getBatchServer().isRuning()){
			
			model.put("message", "Server is already running. No action is taken.");
		
		} else {
			
			getBatchServer().startServer();
			model.put("message", "Server Started.");
		}
		
		model.put("isRun", getBatchServer().isRuning());
		
		return "home";
	}
	
	@RequestMapping(value="/stopJobServer",method = RequestMethod.GET)
	public String stopJobServer(Map<String, Object> model) {

		logger.info("Stop Job Server.");

		if (getBatchServer().isRuning()){
			
			getBatchServer().stopServer();
			model.put("message", "Stop signal send.");
		
		} else {

			model.put("message", "Server is not running. No action is taken.");
		}
		
		model.put("isRun", getBatchServer().isRuning());
		
		return "home";
	}
}
