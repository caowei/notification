package org.caowei.notification.batch;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchServer {

	final static Logger logger = Logger.getLogger(BatchServer.class);
	
	@Autowired
	private PSIUpdateJob psiJob; 
	
	private List<BatchJob> serverJobs = null; 
	
	BatchServerThread batchThread = null;

	public PSIUpdateJob getPsiJob() {
		return psiJob;
	}

	public void setPsiJob(PSIUpdateJob psiJob) {
		this.psiJob = psiJob;
	}

	/**
	 * Start Batch Job Server
	 */
	public synchronized void startServer() {
		
		if (serverJobs == null){
			serverJobs = new ArrayList<BatchJob>();
			serverJobs.add(getPsiJob());
		}
		
		if (batchThread == null ||
				(!batchThread.isRunFlag() && !batchThread.isAlive())){
			
			logger.info("Server is not running. Try to start server.");
			
			batchThread = new BatchServerThread(serverJobs);
			batchThread.setRunFlag(true);
			batchThread.start();
			
		} else if (batchThread.isAlive()){
			
			logger.info("Server currently is running. No action to take.");
		
		} 
	}
	
	public synchronized boolean isRuning(){
		if (batchThread != null && batchThread.isRunFlag() && batchThread.isAlive()){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Stop Batch Job Server
	 * @param job
	 */
	public synchronized void registerJob(BatchJob job){
		serverJobs.add(job);
	}
	
	public synchronized void stopServer() {
		
		batchThread.setRunFlag(false);
		
		try {
		
			if (batchThread.isAlive()){
				batchThread.interrupt();
			}
			
			//Sleep a while
			Thread.sleep(2000);
		} catch (Exception e){
			logger.error("Error occurs while attempt to stop the batch Server",e);
		}
	
		logger.info("Server Stopped.");
		
	}
	
	/**
	 * Register a Job
	 * @param job
	 */
	public synchronized void regsiterJob(BatchJob job) {
		
		if (job == null)
			throw new IllegalArgumentException(" Job is null.");
				
		logger.info("Stop signal send to the server thread.");
		
	}
	
	public synchronized void setFrequencyInMinutes(int frequency) {
		
		batchThread.setRunFrequenceInMinutes(frequency);
		
		logger.info("Set batch run frequency to " + frequency + " Minutes");
		
	}

	private static class BatchServerThread extends Thread {
		
		private volatile boolean runFlag = false;

		private volatile int runFrequenceInMinutes = 1;
		
		private volatile List<BatchJob> jobs = new ArrayList<BatchJob>();
		
		public BatchServerThread(List<BatchJob> jobs){
			super();
			this.jobs = jobs;
		}
		
		public int getRunFrequenceInMinutes() {
			return runFrequenceInMinutes;
		}

		public void setRunFrequenceInMinutes(int runFrequenceInMinutes) {
			this.runFrequenceInMinutes = runFrequenceInMinutes;
		}

		public boolean isRunFlag() {
			return runFlag;
		}

		public void setRunFlag(boolean runFlag) {
			this.runFlag = runFlag;
		}


		@Override
		public void run() {
			
			try {
				
				while (isRunFlag()){
					logger.info("Batch Server Thread is runing. Frequency " + getRunFrequenceInMinutes() + " Minutes.");
					
					for (BatchJob job : jobs){
						
						try {
							
							logger.info("Batch Job Server start to run job " + job.getJobName());
							
							job.run();	
						
						} catch (Exception e){
							logger.error("Error occurs while run batch job " + job.getJobName(),e);
						}
						
					}
					
					Thread.sleep(runFrequenceInMinutes * 1000L * 60);
					
				}
			} catch (Exception e) {
				
				logger.error(e);
			}
			
			logger.info("Batch Server Thread stop!");
		}
		
	}
	
	
	public static void main(String[] args){
		BatchServer server = new BatchServer();
		server.startServer();
		//server.stop
	}
}
