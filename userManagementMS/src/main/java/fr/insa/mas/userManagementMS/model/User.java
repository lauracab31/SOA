package fr.insa.mas.userManagementMS.model;

public abstract class User {
	private int id;
	private String username;
	private String password;
	
	public User() {
	}
	
	public abstract int getId();
	
	public abstract void setId(int id);
		
	public abstract String getUsername();
	
	public abstract void setUsername(String username);
	
	public abstract String getPassword();
	
	public abstract void setPassword(String password);

}
