package com.giulianodb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giulianodb.domain.Personne;
import com.giulianodb.repository.PersonneRepository;

@Service
public class PersonneService {
	
	@Autowired
	private PersonneRepository repo;
	
	public List<Personne> findAll(){
		return repo.findAll();
		
	}
}
