package com.giulianodb.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.giulianodb.domain.Personne;
import com.giulianodb.dto.PersonneDTO;
import com.giulianodb.service.PersonneService;

@RestController
@RequestMapping(value="/personnes")
public class PersonneResource {
	
	@Autowired
	private PersonneService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PersonneDTO>> findAll(){
		
		List<Personne> list = service.findAll();
		
		List<PersonneDTO> listDTO = list.stream().map(x -> new PersonneDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
		
	}
	
}
