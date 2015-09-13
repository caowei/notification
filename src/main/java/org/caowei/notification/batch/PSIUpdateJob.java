package org.caowei.notification.batch;

import java.util.List;

import org.apache.log4j.Logger;
import org.caowei.notification.model.nea.PSIResult;
import org.caowei.notification.service.NEAClient;
import org.caowei.notification.service.PushBulletClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PSIUpdateJob implements BatchJob {

	final static Logger logger = Logger.getLogger(PSIUpdateJob.class);
	
	private List<PSIResult> lastUpdateResult = null;
	
	@Autowired
	private NEAClient neaClient;
	
	@Autowired
	private PushBulletClient pushClient;
	
	
	public NEAClient getNeaClient() {
		return neaClient;
	}

	public void setNeaClient(NEAClient neaClient) {
		this.neaClient = neaClient;
	}

	public PushBulletClient getPushClient() {
		return pushClient;
	}

	public void setPushClient(PushBulletClient pushClient) {
		this.pushClient = pushClient;
	}

	/**
	 * Get Batch Job Name
	 */
	public String getJobName() {
		return this.getClass().getName();
	}
	
	public void run() {
		
		logger.info("Run batch job " + getJobName());
		
		List<PSIResult> psiresult = getNeaClient().getPSIUpdates();
		
		if (psiresult == null){
			logger.warn("Can't get result.");
			return;
		}
		
		if (isResultUpdated(psiresult,lastUpdateResult)){
			lastUpdateResult = psiresult;
			
			logger.info("This is updated record. Push Message to devices.");
			pushMessages(lastUpdateResult);
		
		} else {
			logger.info("result is not updated. No need to send notification again.");
		}

		logger.info("Batch Job " + getJobName() + " ran successfully.");
		
	}
	
	private void pushMessages(List<PSIResult> results){
		
		if (results == null){
			return;
		}
		
		for (PSIResult psiResult : results){
			if (psiResult.getRegion().equalsIgnoreCase("NRS") || 
					psiResult.getRegion().equalsIgnoreCase("rNO")){
				
				getPushClient().pushMessage("PSI Updates", psiResult.toString());
			}
			
		}
		
	}
	
	/**
	 * Check if result is the same as last time checked.
	 * 
	 * @param current
	 * @param old
	 * @return
	 */
	private boolean isResultUpdated(List<PSIResult> current,List<PSIResult> old){
		
		if (old == null && current != null){
			return true;
		}
		
		if (current != null && old != null){
			return !current.get(0).getRawTimestamp().equals(old.get(0).getRawTimestamp());
		}
		
		return false;
	}

}
