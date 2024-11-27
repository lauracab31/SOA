package fr.insa.mas.InsctiptionService.controller;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InscriptionRessource {
	private boolean running;
	
	public static Connection con = null; 
	
	@PostMapping("/benevole/{username}/{password}")
	public void addBenevole(@PathVariable String username, @PathVariable String password) {
		connexionBDD();
	//	addUser(username, password);
	//	addBenevole
		
	}
	
	/*
	@PostMapping("/Demandeur")
	public void addDemandeur(@PathVariable String username, @PathVariable String password) {
		connexionBDD();
	//	ajoutDemandeur();
	}
	
	@PostMapping("/Validateur")
	public void addValidateur(@PathVariable String username, @PathVariable String password) {
		connexionBDD();
	//	ajoutValidateur();
	}*/
	
	//en attendant on le met ici :
	public void connexionBDD() {
		
		String DBurl = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_045";
		
	//	String DBurl = "mysql -h srv-bdens.insa-toulouse.fr --port=3306 -u projet_gei_045 -p projet_gei_045";
	
		try {
			con = DriverManager.getConnection(DBurl, "projet_gei_045", "IG0aito0");
			System.out.println("Connexion établie");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Impossible de se connecter à la base de données.");
		}
		
		running = true;
		
		while(running) {
			
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("La base de données n'a pas pu être correctement fermée");
		}			
	}
	/*
	//Fonction qui permet d'insérer un utilisateur dans notre base de données
			public static void addUser(String username, String password) {
				String requete = "INSERT INTO User (username, password) "
						+ "VALUES ('"+ username +"', '"+ password+"');";
				
				try {
					Statement stmt = con.createStatement();
					stmt.executeUpdate(requete);
				} catch (SQLException e) {
					System.out.println(e);
				}
				
			}
			
			public static void ajoutBenevole(int id) {
				
				String requete = "INSERT INTO Benevole (id) "
						+ "VALUES ('"+ id +");";
				
				try {
					Statement stmt = con.createStatement();
					stmt.executeUpdate(requete);
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
	*/

}
