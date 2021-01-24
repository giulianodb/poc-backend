package com.giulianodb.service.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giulianodb.domain.Personne;

@StepScope
@Component
public class PersonnesItemProcessor implements ItemProcessor<Personne, Personne>{
	
	  
	  @Autowired
	  private PersonneBatchStatus status;
	
	@Override
	public Personne process(Personne item) throws Exception {
		
		try {
			Long cpf = Long.valueOf(item.getCpf().replace("-", "").replace(".", "").trim());
			if (cpf%2 == 0) {
				return item;
			}
			else {
				status.ajouterNonInseree();
				return null;
			}
			
		} catch (Exception e) {
			status.ajouterErreurs();
			return null;
		} 
		
		
	}

	
	
}
