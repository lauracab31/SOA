package fr.insa.mas.userManagementMS.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Validateur")
public class Validateur extends User {
	private int id;
	private String username;
	private String password;


	public Validateur() {
	}
	
	public Validateur(int i, String u, String p) {
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
