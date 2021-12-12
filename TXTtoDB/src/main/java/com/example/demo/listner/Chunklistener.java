package com.example.demo.listner;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecutionListener;

public class Chunklistener implements JobExecutionListener{

		  


		@Override
		public void beforeJob(org.springframework.batch.core.JobExecution jobExecution) {
			// TODO Auto-generated method stub
			jobExecution.setExitStatus(ExitStatus.STOPPED);	
		}


		@Override
		public void afterJob(org.springframework.batch.core.JobExecution jobExecution) {
			// TODO Auto-generated method stub
			
		}
		}

