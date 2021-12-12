package com.example.demo.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.model.Student;

public class StudentItemProcessor implements ItemProcessor<Student, Student>{
	@Override
	public Student process(Student student) throws Exception {
		return student;
	}
}
