package com.giulianodb.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.giulianodb.domain.Personne;
import com.giulianodb.dto.PersonneDTO;
import com.giulianodb.resources.execptions.StandardError;
import com.giulianodb.service.PersonneService;
import com.giulianodb.service.batch.PersonneBatchStatus;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/personnes")
public class PersonneResource {
	
	@Autowired
	private PersonneService service;
	
	@Autowired
	private JobLauncher jobLauncher;
	  
	@Autowired
	private Job importPersonneJob;
		  
	@Autowired
	private PersonneBatchStatus status;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value="Recherche tous")
	@CrossOrigin
	public ResponseEntity<List<PersonneDTO>> findAll(){
		
		List<Personne> list = service.findAll();
		
		List<PersonneDTO> listDTO = list.stream().map(x -> new PersonneDTO(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDTO);
		
	}
	@ApiOperation(value="Recherche par l’ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<PersonneDTO> findById(@PathVariable String id){
			
		Personne obj = service.findById(id);
		
		return ResponseEntity.ok().body(new PersonneDTO(obj));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<Void> insert(@RequestBody PersonneDTO objDTO){
			
		Personne obj = service.fromDTO(objDTO);
		
		obj = service.insert(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@CrossOrigin
	@ApiOperation(value="Supprimer par ID")
	public ResponseEntity<Void> delete(@PathVariable String id){
			
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ApiOperation(value="Supprimer tous")
	@CrossOrigin
	public ResponseEntity<Void> deleteAll(){
			
		service.deleteAll();
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value="Changer de personne")
	@CrossOrigin
	public ResponseEntity<Void> update(@RequestBody PersonneDTO objDTO,@PathVariable String id){
			
		Personne obj = service.fromDTO(objDTO);
		
		obj.setId(id);
		
		service.update(obj);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/batch", method = RequestMethod.GET)
	@ApiOperation(value="Le résulta du dernier batch exécuté")
	@CrossOrigin
	public ResponseEntity<PersonneBatchStatus> statusBatch(){
		return ResponseEntity.ok().body(status);
	}
	
	@RequestMapping(value="/batch",method = RequestMethod.POST)
	@ApiOperation(value="Commencez la procédure Batch")
	@CrossOrigin
	public ResponseEntity<Void> startBatch(){
		status.demarrerStatut();
		 final JobParameters jobParameter = new JobParametersBuilder()
				    .addLong("time", System.currentTimeMillis())
				    .addString("fileName", "/tmp/personnes.txt")
				    .toJobParameters();
				  
				  try {
					jobLauncher.run(importPersonneJob, jobParameter);
				} catch (JobExecutionAlreadyRunningException e) {
					e.printStackTrace();
				} catch (JobRestartException e) {
					e.printStackTrace();
				} catch (JobInstanceAlreadyCompleteException e) {
					e.printStackTrace();
				} catch (JobParametersInvalidException e) {
					e.printStackTrace();
				}
		
		return ResponseEntity.ok().build();
		
	}
	
	   @PostMapping("/uploadFile")
	   @ApiOperation(value="Envoi de fichiers de personnes")
	   @CrossOrigin
	   public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

	        try {
	        	if (file.getSize() > 0) {
	        		service.saveFile(file);
	        		return ResponseEntity.ok().build();
	        	}
	        	else {
	        	
	        		return ResponseEntity.status(422).body("{\"message\":\"Fichier non envoyé\"}");
	        	}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        return ResponseEntity.status(500).build();
	        
	   }
	
}
