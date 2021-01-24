package com.giulianodb.service.batch;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giulianodb.domain.Personne;
import com.giulianodb.service.PersonneService;
import com.giulianodb.service.execptions.CpfInvalideException;

@StepScope
@Component
public class PersonneWriterBatch implements ItemWriter<Personne>{
	
	@Autowired
	private PersonneService personneService;
	
	@Autowired
	private PersonneBatchStatus status;
	
	@Override
	public void write(List<? extends Personne> items) throws Exception {
		
		for (Personne personne : items) {
			try {
				personneService.insert(personne);
				status.ajouterOK();
				
			} catch (CpfInvalideException e) {
				status.ajouterErreurs();
			}
		}
	}
	
}
