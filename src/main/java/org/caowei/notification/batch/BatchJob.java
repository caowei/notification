package org.caowei.notification.batch;

public interface BatchJob {
	
	public void run();
	
	public String getJobName();
}
