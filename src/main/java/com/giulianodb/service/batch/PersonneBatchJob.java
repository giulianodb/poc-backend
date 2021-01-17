package com.giulianodb.service.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.giulianodb.domain.Personne;

@Configuration
@EnableBatchProcessing
public class PersonneBatchJob {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job importPersonneJob (final Step myStep) {
	  return jobBuilderFactory.get("importPersonneJob")
	    .incrementer(new RunIdIncrementer())
	    .flow(myStep)
	    .end()
	    .build();
	}
	
	@Bean
	public Step myStep (final ItemReader<Personne> personneReaderBatch,
	                    final ItemWriter<Personne> personneWriterBatch,
	                    final ItemProcessor<Personne, Personne> personneProcessorBatch,
	                    final TaskExecutor myExecutor) {
		
	  return stepBuilderFactory.get("step1")
	    .<Personne, Personne>chunk(200)
	    .reader(personneReaderBatch)
	    .processor(personneProcessorBatch)
	    .writer(personneWriterBatch)
	    .taskExecutor(myExecutor)
	    .build();
	}
	
	@Bean
	public TaskExecutor myExecutor () {
	  final ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	  taskExecutor.setCorePoolSize(20);
	  return taskExecutor;
	}
}
