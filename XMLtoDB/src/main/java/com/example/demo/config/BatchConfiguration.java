package com.example.demo.config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.example.demo.counter.StudentItemCounter;
import com.example.demo.model.Student;
import com.example.demo.processor.StudentItemProcessor;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
 
 @Autowired
 public JobBuilderFactory jobBuilderFactory;
 
 @Autowired
 public StepBuilderFactory stepBuilderFactory;
 
 @Autowired
 public DataSource dataSource;
 
 @Bean
 public DataSource dataSource() {
	
			final DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("org.postgresql.Driver");
			dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
			dataSource.setUsername("postgres");
			dataSource.setPassword("MCHItti1228@");
			return dataSource;
		
	
 }
 
 @Bean
 public StaxEventItemReader<Student> reader(){
  StaxEventItemReader<Student> reader = new StaxEventItemReader<Student>();
  reader.setResource(new ClassPathResource("student.xml"));
  reader.setFragmentRootElementName("students");
  
  Map<String, Class> aliases = new HashMap<String, Class>();
  aliases.put("student", Student.class);
  
  XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
  xStreamMarshaller.setAliases(aliases);
  
  reader.setUnmarshaller(xStreamMarshaller);
  
  return reader;
 }
 
 
 @Bean
 
 public FlatFileItemWriter<Student> writer(){
 	  FlatFileItemWriter<Student> writer = new FlatFileItemWriter<Student>();
 	writer.setResource(new ClassPathResource("Student.xml"));
	  writer.setLineAggregator(new DelimitedLineAggregator<Student>() {{
	   setDelimiter("|");
	   setFieldExtractor(new BeanWrapperFieldExtractor<Student>() {{
	    setNames(new String[] { "studentId","studentName", "studentDepartment","subject1","subject2","subject3","grade"});
	   }});
	  }});
	  
	  return writer;
 }
 
 private class StudentItemPreparedStmSetter implements ItemPreparedStatementSetter<Student>{
 
  public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		Student student = new Student();
		student.setStudentId(rs.getInt("studentId"));
		student.setStudentName(rs.getString("studentName"));
		student.setStudentDepartment(rs.getString("studentDepartment"));
		student.setSubject1(rs.getFloat("subject1"));
		student.setSubject2(rs.getFloat("subject2"));
		student.setSubject3(rs.getFloat("subject3"));
		return student;
	}

@Override
public void setValues(Student item, PreparedStatement ps) throws SQLException {
	// TODO Auto-generated method stub
	
}
  
 }
 @Bean
 public StudentItemProcessor processor() {
	 return new StudentItemProcessor();
 }
 @Bean
	public StudentItemCounter listener() {
	   return new StudentItemCounter();
	}
 @Bean
 public Step step1() {
  return stepBuilderFactory.get("step1")
    .<Student, Student> chunk(10)
    .reader(reader())
    .processor(processor())
    .writer(writer())
    .listener(listener())
    .build();
 }
 
 @Bean
 public Job importUserJob() {
  return jobBuilderFactory.get("importStudentJob")
    .incrementer(new RunIdIncrementer())
    .flow(step1())
    .end()
    .build();
    
 }
}