package fr.insa.mas.userManagementMS.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.mas.userManagementMS.model.Benevole;
import org.springframework.http.MediaType;

@RestController
public class BenevoleRessource {

	@GetMapping("/benevole")
	public int userNumber() {
		return 200;
	}
	
	@GetMapping(value="/benevole/{username}")
	public Benevole infosUser(@PathVariable String username) {
		Benevole b = new Benevole(username, "mdp123");
		return b;
	}
	
	@GetMapping(value="/{username}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Benevole xmlInfosUser(@PathVariable String username) {
		Benevole b= new Benevole(username, "mdp123");
		return b;
	}
	
	@PostMapping(value="/benevole/add/b/{username}/{password}")
	public void addUser(@PathVariable String username,@PathVariable String password) {
		System.out.println("username : " + username + " password : "+ password);
	}
	
	@DeleteMapping(value="/benevole/del/{username}")
	public void benevoleDel(@PathVariable String username) {
		System.out.println("On a effacé l'utilisateur "+username);
	}
	

}
