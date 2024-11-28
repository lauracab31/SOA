package fr.insa.mas.InsctiptionService.controller;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	    System.out.println("username : " + username + " password : " + password);
	    connexionBDD();
	    System.out.println("Adding user to database...");
	    addUser(username, password);
	    System.out.println("User added successfully!");
	    int id= get_id(username);
	    System.out.println("id récupéré"+id);
	    ajoutBenevole(id);
	    System.out.println("Bénévole ajouté");
	    closeConnection();
	}
	
	@PostMapping("/demandeur/{username}/{password}")
	public void addDemandeur(@PathVariable String username, @PathVariable String password) {
	    System.out.println("username : " + username + " password : " + password);
	    connexionBDD();
	    System.out.println("Adding user to database...");
	    addUser(username, password);
	    System.out.println("User added successfully!");
	    int id= get_id(username);
	    System.out.println("id récupéré"+id);
	    ajoutDemandeur(id);
	    System.out.println("demandeur ajouté");
	    closeConnection();
	}
	
	@PostMapping("/validateur/{username}/{password}")
	public void addValidateur(@PathVariable String username, @PathVariable String password) {
	    System.out.println("username : " + username + " password : " + password);
	    connexionBDD();
	    System.out.println("Adding user to database...");
	    addUser(username, password);
	    System.out.println("User added successfully!");
	    int id= get_id(username);
	    System.out.println("id récupéré"+id);
	    ajoutValidateur(id);
	    System.out.println("validateur ajouté");
	    closeConnection();
	}	

	
	public void connexionBDD() {
	    String DBurl = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_045?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	    try {
	        con = DriverManager.getConnection(DBurl, "projet_gei_045", "IG0aito0");
	        System.out.println("Connexion établie");
	    } catch (SQLException e) {
	        System.err.println("Impossible de se connecter à la base de données: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void closeConnection() {
	    try {
	        if (con != null && !con.isClosed()) {
	            con.close();
	            System.out.println("Connexion fermée");
	        }
	    } catch (SQLException e) {
	        System.err.println("La base de données n'a pas pu être correctement fermée: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	
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
			
			
			public static int get_id(String username) {
			    int id = 0; // Default value in case the user is not found
			    String query = "SELECT idUser FROM User WHERE username = ?";

			    try (PreparedStatement pstmt = con.prepareStatement(query)) {
			        // Set the parameter in the query
			        pstmt.setString(1, username);

			        // Execute the query
			        try (ResultSet rs = pstmt.executeQuery()) {
			            // Process the result set
			            if (rs.next()) {
			                id = rs.getInt("idUser");
			            }
			        }
			    } catch (SQLException e) {
			        System.out.println("Error while retrieving user ID: " + e.getMessage());
			        e.printStackTrace();
			    }
			    return id;
			}

			
			public static void ajoutBenevole(int id) {
				
				String requete = "INSERT INTO Benevole (idBenevole) "
						+ "VALUES ('"+ id +"');";
				
				try {
					Statement stmt = con.createStatement();
					stmt.executeUpdate(requete);
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			
			public static void ajoutDemandeur(int id) {
				
				String requete = "INSERT INTO Demandeur (idDemandeur) "
						+ "VALUES ('"+ id +"');";
				
				try {
					Statement stmt = con.createStatement();
					stmt.executeUpdate(requete);
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			
			public static void ajoutValidateur(int id) {
				
				String requete = "INSERT INTO Validateur (idValidateur) "
						+ "VALUES ('"+ id +"');";
				
				try {
					Statement stmt = con.createStatement();
					stmt.executeUpdate(requete);
				} catch (SQLException e) {
					System.out.println(e);
				}
			}

}
