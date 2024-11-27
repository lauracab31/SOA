package fr.insa.mas.userManagementMS.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Benevole")
public class Benevole extends User {
	private String username;
	private String password;


	public Benevole() {
	}
	
	public Benevole(String u, String p) {
		this.password=p;
		this.username=u;
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
