package com.giulianodb.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.giulianodb.domain.Personne;
import com.giulianodb.dto.PersonneDTO;
import com.giulianodb.repository.PersonneRepository;
import com.giulianodb.service.execptions.ObjectNotFoudException;

@Service
public class PersonneService {
	
	@Autowired
	private PersonneRepository repo;
	
	  @Value("${fileName}")
	  private String fileName;
	
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
	
	public void deleteAll() {
		List<Personne> listPersonne = findAll();
		
		for (Personne personne : listPersonne) {
			delete(personne.getId());
		}
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
	
	public void saveFile(MultipartFile file) throws URISyntaxException, IOException {
		
		File convFile = new File(fileName);
		file.transferTo(convFile);
		
	}

	public Personne fromDTO (PersonneDTO objDTO) {
		return new Personne(objDTO.getId(),objDTO.getNom(), objDTO.getCpf());
	}



}
