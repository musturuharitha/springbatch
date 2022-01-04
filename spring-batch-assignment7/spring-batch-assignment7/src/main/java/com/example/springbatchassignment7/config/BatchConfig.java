package com.example.springbatchassignment7.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.example.springbatchassignment7.model.Student;
import com.example.springbatchassignment7.writer.Writer;




@Configuration
@EnableBatchProcessing
public class BatchConfig {


	 
	    @Value("input/student*.csv")
	    private Resource[] resources;
	    
	    
	

	



	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,
			StepBuilderFactory stepBuilderFactory,
			ItemReader<Student> itemReader,ItemProcessor<Student,Student> itemProcessor,ItemWriter<Student> itemWriter) {
		
		Step step=stepBuilderFactory.get("batch-step").<Student,Student>chunk(5).reader(multiResourceItemReader()).processor(itemProcessor)
				.writer(itemWriter).build();
		
		return jobBuilderFactory.get("batch-job").incrementer(new RunIdIncrementer()).start(step).build();
	}
	
	@Bean
    public MultiResourceItemReader<Student> multiResourceItemReader() 
    {
		
        MultiResourceItemReader<Student> resourceItemReader = new MultiResourceItemReader<Student>();
        resourceItemReader.setResources(resources);
        resourceItemReader.setDelegate(itemReader());
        return resourceItemReader;
    }
	
	@Bean
	public FlatFileItemReader<Student> itemReader(){
		
		   FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader<>();
	        
	        flatFileItemReader.setName("CSV-Reader");
	        flatFileItemReader.setLinesToSkip(1);
	        flatFileItemReader.setLineMapper(lineMapper());
//	        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/student"));
	        
	        return flatFileItemReader;
	}
	
	
	@Bean
	public LineMapper<Student> lineMapper(){
		
		DefaultLineMapper<Student> lineMapper=new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
		
			lineTokenizer.setDelimiter(",");
	        lineTokenizer.setStrict(false);
	        lineTokenizer.setNames("id","name","marks");
	        
	        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	        fieldSetMapper.setTargetType(Student.class);
	        
	        lineMapper.setLineTokenizer(lineTokenizer);
	        lineMapper.setFieldSetMapper(fieldSetMapper);
	        
	        return lineMapper;
	        
	  
		
	}
	
	@Bean
	public Writer itemWriter() {
		
		Writer writer = new Writer();

      
     
     
     BeanWrapperFieldExtractor<Student> fieldExtractor=new BeanWrapperFieldExtractor<>();
     fieldExtractor.setNames(new String[] { "id", "name", "marks" });
     
     DelimitedLineAggregator<Student> lineAggregator=new DelimitedLineAggregator<>();
     lineAggregator.setDelimiter(",");
     lineAggregator.setFieldExtractor(fieldExtractor);
     
     
     writer.setLineAggregator(lineAggregator);
      

     return writer;
		
	}
	
	
}
