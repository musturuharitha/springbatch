package com.example.demo.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.example.demo.listner.StudentChunkCounter;
import com.example.demo.model.Student;
import com.example.demo.processor.StudentProcessor;



@Configuration
@EnableBatchProcessing
public class StudentConfiguration {
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

	public class UserRowMapper implements RowMapper<Student> {

		@Override
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
	}
	@Bean
	public JdbcCursorItemReader<Student> reader() {
		
		JdbcCursorItemReader<Student> reader = new JdbcCursorItemReader<Student>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT * from Student");
		reader.setRowMapper(new UserRowMapper());
		return reader;
	}
	
	@Bean
	public StudentProcessor processor() {
		return new StudentProcessor();
	}
    @Bean
    public FlatFileItemWriter<Student> writer(){
  	  FlatFileItemWriter<Student> writer = new FlatFileItemWriter<Student>();
  	writer.setResource(new ClassPathResource("Student.txt"));
	  writer.setLineAggregator(new DelimitedLineAggregator<Student>() {{
	   setDelimiter("|");
	   setFieldExtractor(new BeanWrapperFieldExtractor<Student>() {{
	    setNames(new String[] { "studentId","studentName", "studentDepartment","subject1","subject2","subject3","grade"});
	   }});
	  }});
	  
	  return writer;
	
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory
				.get("step1")
				.<Student, Student>chunk(50)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.listener(listener())
				.build();
	}

	@Bean
	public Job exportStudentJob() {
		return jobBuilderFactory
				.get("exportStudentJob")
				.incrementer(new RunIdIncrementer())
				.flow(step1())
				.end()
				.build();
	}
	
	@Bean
	public StudentChunkCounter listener() {
		return new StudentChunkCounter();
	}
	

}