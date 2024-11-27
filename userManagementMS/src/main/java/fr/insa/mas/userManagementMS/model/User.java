package fr.insa.mas.userManagementMS.model;

public abstract class User {
	private String username;
	private String password;
	
	public User() {
	}
		
	public abstract String getUsername();
	
	public abstract void setUsername(String username);
	
	public abstract String getPassword();
	
	public abstract void setPassword(String password);

}
