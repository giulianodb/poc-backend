package com.giulianodb.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<PersonneDTO> findById(@PathVariable String id){
			
		Personne obj = service.findById(id);
		
		return ResponseEntity.ok().body(new PersonneDTO(obj));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody PersonneDTO objDTO){
			
		Personne obj = service.fromDTO(objDTO);
		
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id){
			
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody PersonneDTO objDTO,@PathVariable String id){
			
		Personne obj = service.fromDTO(objDTO);
		
		obj.setId(id);
		
		service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
}
