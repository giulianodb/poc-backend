package com.giulianodb.dto;

import java.io.Serializable;

import com.giulianodb.domain.Personne;

public class PersonneDTO implements Serializable{

	private static final long serialVersionUID = 6102332309407937437L;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	private String id;
	private String nom;
	private String cpf;
	
	public PersonneDTO() {
		
	}
	
	public PersonneDTO(Personne obj) {
		
		id = obj.getId();
		nom = obj.getNom();
		cpf = obj.getCpf();
	}
		
}
