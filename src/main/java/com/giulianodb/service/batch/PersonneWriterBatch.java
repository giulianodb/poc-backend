package com.giulianodb.service.batch;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giulianodb.domain.Personne;
import com.giulianodb.service.PersonneService;

@StepScope
@Component
public class PersonneWriterBatch implements ItemWriter<Personne>{
	
	@Autowired
	private PersonneService personneService;
	
	@Override
	public void write(List<? extends Personne> items) throws Exception {
		
		for (Personne personne : items) {
			personneService.insert(personne);
		}
//		System.out.println("Inseriu todos!!");
	}
	
}
