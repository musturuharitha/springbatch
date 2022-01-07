package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


	@Entity
	//defining class name as Table name
	@Table
	public class Employee {
	
	//Defining book id as primary key
	@Id
	@Column
	private int employeeid;
	
	public int getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}
	public String getEmployeedept() {
		return employeedept;
	}
	public void setEmployeedept(String employeedept) {
		this.employeedept = employeedept;
	}
	public int getEmployeesalary() {
		return employeesalary;
	}
	public void setEmployeesalary(int employeesalary) {
		this.employeesalary = employeesalary;
	}
	@Column
	private String employeename;
	@Column
	private String employeedept;
	@Column
	private int employeesalary;
	}
	