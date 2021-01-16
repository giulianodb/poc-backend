package com.giulianodb.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.giulianodb.domain.Personne;

@RestController
@RequestMapping(value="/personnes")
public class PersonneResource {
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Personne>> findAll(){
		Personne maria = new Personne(1, "Maria", "05265626921");
		Personne pedro = new Personne(2, "Pedro", "0598665264");
		
		List<Personne> list = new ArrayList<Personne>();
		list.addAll(Arrays.asList(maria,pedro));
		
		return ResponseEntity.ok().body(list);
		
	}
	
}
