package com.giulianodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.giulianodb.domain.Personne;

@Repository
public interface PersonneRepository extends MongoRepository<Personne, String>{
	
}
