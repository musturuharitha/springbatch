package com.example.demo.model;

import lombok.Data;

@Data
public class Student {
	private Integer studentId;
	private String studentName;
	private String studentDepartment;
	private Float subject1;
	private Float subject2;
	private Float subject3;
	private String grade;
	public Student() {
		super();
	}
	public Student(Integer studentId, String studentName, String studentDepartment, Float subject1, Float subject2,
			Float subject3, String grade) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentDepartment = studentDepartment;
		this.subject1 = subject1;
		this.subject2 = subject2;
		this.subject3 = subject3;
		this.grade = grade;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentDepartment() {
		return studentDepartment;
	}
	public void setStudentDepartment(String studentDepartment) {
		this.studentDepartment = studentDepartment;
	}
	public Float getSubject1() {
		return subject1;
	}
	public void setSubject1(Float subject1) {
		this.subject1 = subject1;
	}
	public Float getSubject2() {
		return subject2;
	}
	public void setSubject2(Float subject2) {
		this.subject2 = subject2;
	}
	public Float getSubject3() {
		return subject3;
	}
	public void setSubject3(Float subject3) {
		this.subject3 = subject3;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", studentDepartment="
				+ studentDepartment + ", subject1=" + subject1 + ", subject2=" + subject2 + ", subject3=" + subject3
				+ ", grade=" + grade + "]";
	}
	public int getSub1() {
		// TODO Auto-generated method stub
		return 0;
	}
}
