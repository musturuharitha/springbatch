package com.example.springbatchassignment7.batch;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.springbatchassignment7.model.Student;

@Component
public class Processor implements ItemProcessor<Student,Student>{
	Map<Integer,Student> map=new HashMap<>();

	@Override
	public Student process(Student item) throws Exception {
		
		try {
			if(map.containsKey(item.getId())) {
				return null;
			}
		}
			catch(NullPointerException e) {
				System.out.println(e);
			}
		
		
			map.put(item.getId(), item);
			if(item.getMarks()<=50)
				return null;
		
		return item;
		
	}

}
