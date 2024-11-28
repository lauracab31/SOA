package fr.insa.mas.ConnexionService.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ConnexionRessource {
	private boolean running;
	
	public static Connection con = null; 
	
	@GetMapping("/getId/{username}/{password}")
	public int getUserId(@PathVariable String username, @PathVariable String password) {
		int id=0;
		connexionBDD();
		id = getId(username, password);
		closeConnection();
		return id;
	}
	
	@GetMapping("/role/{username}/{password}")
	public int getRole(@PathVariable String username, @PathVariable String password) {
		int role=0;
		connexionBDD();
		int id = getId(username, password);
		if (id != 0 && isBenevole(id)) { 
            role = 1; 
        }
		if (id != 0 && isDemandeur(id)) { 
            role = 2; 
        }
		if (id != 0 && isValidateur(id)) { 
            role = 3; 
        }
		closeConnection();
		return role;
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
	
	public static int getId(String username, String password) {
	    int id = 0; 
	    String requete = "SELECT idUser FROM User WHERE username = ? AND password = ?";

	    try (PreparedStatement pstmt = con.prepareStatement(requete)) {
	        pstmt.setString(1, username);
	        pstmt.setString(2, password);

	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) { 
	                id = rs.getInt("idUser"); 
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Erreur lors de l'accès à la base : " + e.getMessage());
	    }

	    return id;
	}
	
	public static Boolean isBenevole(int id) {
	    String query = "SELECT COUNT(*) AS count FROM Benevole WHERE idBenevole = ?";
	    boolean result = false;

	    try (PreparedStatement pstmt = con.prepareStatement(query)) {
	        // Set the id parameter in the query
	        pstmt.setInt(1, id);

	        // Execute the query
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt("count"); // Retrieve the count of matching rows
	                result = (count > 0); // If count > 0, the ID exists in the table
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error while checking Benevole table: " + e.getMessage());
	    }

	    return result;
	}
	
	public static Boolean isDemandeur(int id) {
	    String query = "SELECT COUNT(*) AS count FROM Demandeur WHERE idDemandeur = ?";
	    boolean result = false;

	    try (PreparedStatement pstmt = con.prepareStatement(query)) {
	        // Set the id parameter in the query
	        pstmt.setInt(1, id);

	        // Execute the query
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt("count"); // Retrieve the count of matching rows
	                result = (count > 0); // If count > 0, the ID exists in the table
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error while checking Demandeur table: " + e.getMessage());
	    }

	    return result;
	}
	
	public static Boolean isValidateur(int id) {
	    String query = "SELECT COUNT(*) AS count FROM Validateur WHERE idValidateur = ?";
	    boolean result = false;

	    try (PreparedStatement pstmt = con.prepareStatement(query)) {
	        // Set the id parameter in the query
	        pstmt.setInt(1, id);

	        // Execute the query
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt("count"); // Retrieve the count of matching rows
	                result = (count > 0); // If count > 0, the ID exists in the table
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error while checking Validateur table: " + e.getMessage());
	    }

	    return result;
	}




}
