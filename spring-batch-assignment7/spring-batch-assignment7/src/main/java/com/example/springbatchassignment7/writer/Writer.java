package com.example.springbatchassignment7.writer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.example.springbatchassignment7.model.Student;

public class Writer implements ItemStream,ItemWriter<Student>{

	private Map<Integer, FlatFileItemWriter<Student>> writers = new HashMap<>();

    private LineAggregator<Student> lineAggregator;

    private ExecutionContext executionContext;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.executionContext = executionContext;
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
    }

    @Override
    public void close() throws ItemStreamException {
        for(FlatFileItemWriter<Student> f:writers.values()){
            f.close();
        }
    }

    @Override
    public void write(List<? extends Student> items) throws Exception {
        for (Student item : items) {
            FlatFileItemWriter<Student> ffiw = getFlatFileItemWriter(item);
            ffiw.write(Arrays.asList(item));
        }
    }

    public LineAggregator<Student> getLineAggregator() {
        return lineAggregator;
    }

    public void setLineAggregator(LineAggregator<Student> lineAggregator) {
        this.lineAggregator = lineAggregator;
    }


    public FlatFileItemWriter<Student> getFlatFileItemWriter(Student item) {
    	
    	Resource resource=null;
        Integer marks = item.getMarks();
        int key=0;
        if(marks<50) {
        	key=1;
        }
        else {
        	key=2;
        }
        FlatFileItemWriter<Student> rr = writers.get(key);
        if(rr == null){
            rr = new FlatFileItemWriter<>();
            rr.setLineAggregator(lineAggregator);
            if(marks<50) {
				resource=new FileSystemResource("src/main/resources/output/reject.csv");
			}
			else {
				resource=new FileSystemResource("src/main/resources/output/success.csv");
			}
			rr.setResource(resource);
			rr.open(executionContext);
            writers.put(key, rr);
         
        }
        return rr;
    }
}
