package com.example.demo.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;


@Component
public  class Writer implements ItemWriter<User>{
	
	    @Autowired
	    private UserRepository userRepository;

	    public void write(List<? extends User> users) throws Exception{
	        System.out.println("Data Saved for Users: " + users);
	        userRepository.saveAll(users);
	    }

		
}





