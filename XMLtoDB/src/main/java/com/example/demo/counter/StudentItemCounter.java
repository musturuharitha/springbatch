package com.example.demo.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

public class StudentItemCounter implements ChunkListener{
	private static final Logger log = LoggerFactory.getLogger(StudentItemCounter.class);

	@Override
	public void beforeChunk(ChunkContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChunk(ChunkContext context) {
		// TODO Auto-generated method stub
		int countRead = context.getStepContext().getStepExecution().getReadCount();
		System.out.println(countRead+" records Read from XML file");
        log.info(countRead+" records Read from XML file");

		int countWrite = context.getStepContext().getStepExecution().getWriteCount();
		 System.out.println(countWrite+" records Writting into Database");
	        log.info(countWrite+" records Writting into Database");
		
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		// TODO Auto-generated method stub
		
	}
}

