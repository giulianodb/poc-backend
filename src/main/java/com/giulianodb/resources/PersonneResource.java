package com.giulianodb.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.giulianodb.domain.Personne;
import com.giulianodb.service.PersonneService;

@RestController
@RequestMapping(value="/personnes")
public class PersonneResource {
	
	@Autowired
	private PersonneService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Personne>> findAll(){
		
		List<Personne> list = service.findAll();
		
		return ResponseEntity.ok().body(list);
		
	}
	
}
