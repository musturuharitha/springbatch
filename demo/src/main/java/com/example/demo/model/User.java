package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	private Integer id;
	private String name;
	private String dept;
	private Integer salary;
	
	public  String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
