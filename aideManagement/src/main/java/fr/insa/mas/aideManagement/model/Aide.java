package fr.insa.mas.aideManagement.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Aide")
public class Aide {
	private int id;
	private String description;
	private boolean estValide;
	private int idDemandeur;
	private int idBenevole;
	private int idValidateur;
	
	public Aide() {
	}
	
	public Aide(int i, String d, boolean v, int idd, int idb, int idv) {
		this.id=i;
		this.description=d;
		this.estValide=v;
		this.idDemandeur=idd;
		this.idBenevole=idb;
		this.idValidateur=idv;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id=id;		
	}	
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String d) {
		this.description=d;		
	}
	
	public Boolean getValide() {
		return estValide;
	}
	
	public void setValide(Boolean v) {
		this.estValide=v;		
	}	
	
	public int getIdDemandeur() {
		return idDemandeur;
	}
	
	public void setIdDemandeur(int id) {
		this.idDemandeur=id;		
	}
	
	public int getIdValidateur() {
		return idValidateur;
	}
	
	public void setIdValidateur(int id) {
		this.idValidateur=id;		
	}
	
	public int getIdBenevole() {
		return idBenevole;
	}
	
	public void setIdBenevole(int id) {
		this.idBenevole=id;		
	}

}
