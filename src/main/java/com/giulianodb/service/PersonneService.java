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
import com.giulianodb.service.batch.PersonneBatchStatus;
import com.giulianodb.service.execptions.CpfInvalideException;
import com.giulianodb.service.execptions.ObjectNotFoudException;
import com.giulianodb.service.utils.Cpf;

@Service
public class PersonneService {

	@Autowired
	private PersonneRepository repo;

	@Value("${fileName}")
	private String fileName;

	@Autowired
	private PersonneBatchStatus status;

	public List<Personne> findAll() {
		return repo.findAll();
	}

	public Personne findById(String id) {
		Optional<Personne> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoudException("Objet non trouvé"));
	}

	public Personne insert(Personne obj) {

		if (validerPerssone(obj)) {
			return repo.insert(obj);
		} else {
			throw new CpfInvalideException("Votre nom ou votre CPF sont invalides.");
		}

	}

	private boolean validerPerssone(Personne obj) {

		if (obj.getNom() != null && obj.getNom().trim().length() > 4) {
			if (validerCpf(obj.getCpf())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

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

	public Personne update(Personne obj) {
		Personne newObj = findById(obj.getId());

		if (validerPerssone(obj)) {
			updateData(newObj, obj);
			return repo.save(newObj);
		} else {
			throw new CpfInvalideException("Votre nom ou votre CPF sont invalides.");
		}

	}

	private void updateData(Personne newObj, Personne obj) {
		newObj.setCpf(obj.getCpf());
		newObj.setNom(obj.getNom());
	}

	public Personne fromFileLine(String line) {
		String[] lineArray = line.split(",");

		try {
			Personne personne = new Personne(null, lineArray[0], lineArray[1]);

			return personne;
		} catch (Exception e) {
			status.ajouterErreurs();
			return null;
		}

	}

	public void saveFile(MultipartFile file) throws URISyntaxException, IOException {
		File convFile = new File(fileName);
		file.transferTo(convFile);
	}

	public Personne fromDTO(PersonneDTO objDTO) {
		return new Personne(objDTO.getId(), objDTO.getNom(), objDTO.getCpf());
	}

	public Boolean validerCpf(String cpf) {
		return Cpf.isValidCPF(cpf);
	}

}
