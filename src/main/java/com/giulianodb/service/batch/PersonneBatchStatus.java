package com.giulianodb.service.batch;

import java.util.Date;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value= ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PersonneBatchStatus {
	
	private Integer quantiteErreurs = 0;
	private Integer quantiteNonInseree = 0;
	private Integer quantiteOk  = 0;
	private Long date  = new Date().getTime();
	private Integer lignesTraitees  = 0;
	
	
	public void ajouterErreurs() {
		this.quantiteErreurs++;
	}
	
	public void ajouterNonInseree() {
		this.quantiteNonInseree++;
	}
	
	
	public void ajouterOK() {
		this.quantiteOk++;
	}
	
	public void ajouterLignesTraitees() {
		this.lignesTraitees++;
	}
	
	public void demarrerStatut() {
		this.lignesTraitees = 0;
		this.quantiteErreurs = 0;
		this.quantiteOk = 0;
		this.quantiteNonInseree = 0;
		this.date = new Date().getTime();
	}

	public Integer getQuantiteErreurs() {
		return quantiteErreurs;
	}

	public void setQuantiteErreurs(Integer quantiteErreurs) {
		this.quantiteErreurs = quantiteErreurs;
	}

	public Integer getQuantiteNonInseree() {
		return quantiteNonInseree;
	}

	public void setQuantiteNonInseree(Integer quantiteNonInseree) {
		this.quantiteNonInseree = quantiteNonInseree;
	}

	public Integer getQuantiteOk() {
		return quantiteOk;
	}

	public void setQuantiteOk(Integer quantiteOk) {
		this.quantiteOk = quantiteOk;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Integer getLignesTraitees() {
		return lignesTraitees;
	}

	public void setLignesTraitees(Integer lignesTraitees) {
		this.lignesTraitees = lignesTraitees;
	}


}
