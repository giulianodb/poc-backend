package com.giulianodb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.giulianodb.domain.Personne;
import com.giulianodb.dto.PersonneDTO;
import com.giulianodb.repository.PersonneRepository;
import com.giulianodb.service.execptions.ObjectNotFoudException;

@Service
public class PersonneService {
	
	@Autowired
	private PersonneRepository repo;
	
	public List<Personne> findAll(){
		return repo.findAll();
		
	}
	
	public Personne findById(String id) {
		Optional<Personne> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoudException("Objet non trouvé"));
	}
	
	public Personne insert(Personne obj) {
		return repo.insert(obj);
	}
	
	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}
	
	public Personne update (Personne obj) {
		Personne newObj = findById(obj.getId());
		
		updateData(newObj,obj);
		
		return repo.save(newObj);
		
	}
	
	private void updateData(Personne newObj, Personne obj) {
		// TODO Auto-generated method stub
		newObj.setCpf(obj.getCpf());
		newObj.setNom(obj.getNom());
	}
	
	public Personne fromFileLine(String line) {
		String[] lineArray = line.split(",");
		
		try {
			Personne personne = new Personne(null, lineArray[0], lineArray[1]);
			
			return personne;
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			return null;
		}
		
	}

	public Personne fromDTO (PersonneDTO objDTO) {
		return new Personne(objDTO.getId(),objDTO.getNom(), objDTO.getCpf());
	}
}
