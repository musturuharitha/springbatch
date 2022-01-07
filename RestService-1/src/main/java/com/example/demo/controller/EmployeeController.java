package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;



	@RestController
	public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	//creating a get mapping that retrieves all the emp detail from the database 
	@GetMapping("/employee")
	private List<Employee> getAllEmployee() 
	{
	return employeeService.getAllEmployee();
	}
	//creating a get mapping that retrieves the detail of a specific emp
	@GetMapping("/employee/{employeeid}")
	private Employee getEmployee(@PathVariable("employeeid") int employeeid) 
	{
	return employeeService.getemployeeById(employeeid);
	}
	//creating a delete mapping that deletes a specified
	@DeleteMapping("/employee/{employeeid}")
	private void deleteEmployee(@PathVariable("employeeid") int employeeid) 
	{
	employeeService.delete(employeeid);
	}
	//creating post mapping that post the book detail in the database
	@PostMapping("/employee")
	private int saveEmployee(@RequestBody Employee employee) 
	{
	employeeService.saveOrUpdate(employee);
	return employee.getEmployeeid();
	}
	//creating put mapping that updates the book detail 
	@PutMapping("/employee")
	private Employee update(@RequestBody Employee employee) 
	{
	employeeService.saveOrUpdate(employee);
	return employee;
	}
	}

