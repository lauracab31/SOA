package fr.insa.mas.orchestratorService.model;

import javax.xml.bind.annotation.XmlRootElement;

import fr.insa.mas.orchestratorService.model.User;

@XmlRootElement(name = "Benevole")
public class Benevole extends User {
	private int id;
	private String username;
	private String password;


	public Benevole() {
	}
	
	public Benevole(int i, String u, String p) {
		this.id=i;
		this.password=p;
		this.username=u;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id=id;
		
	}	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username=username;
		
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password=password;
		
	}

}
