package fr.insa.mas.aideManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.mas.aideManagement.model.Aide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/aide")
public class AideRessource {
	private boolean running;
	public static Connection con = null; 
	
	@PostMapping("/add/{message}/{demandeur}")
	public void ajoutAide(@PathVariable String message, @PathVariable int demandeur) {

		connexionBDD();
		addAide(message,demandeur);
		closeConnection();
	}
	
	@PostMapping("/valider/{aideId}/{validateurId}")
	public void valideAide(@PathVariable int aideId, @PathVariable int validateurId) {
		connexionBDD();
		validerAide(aideId, validateurId);
		closeConnection();
	}
	
	@PostMapping("/choisir/{aideId}/{benevoleId}")
	public void choisirAide(@PathVariable int aideId, @PathVariable int benevoleId) {
		connexionBDD();
		prendreAide(aideId, benevoleId);
		closeConnection();
	}
	
	@GetMapping("/get/{aideId}")
	public Aide getAide(@PathVariable int aideId) {
		connexionBDD();
		Aide a= getAideById(aideId);
		closeConnection();
		return a;
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


	public void addAide(String description, int idDemandeur) {
	    String query = "INSERT INTO Aide (description, estValide, idDemandeur, idBenevole, idValidateur) " +
	                   "VALUES (?, false, ?, NULL, NULL)";

	    try (PreparedStatement pstmt = con.prepareStatement(query)) {
	        pstmt.setString(1, description);
	        pstmt.setInt(2, idDemandeur);

	        pstmt.executeUpdate();
	        System.out.println("Aide ajoutée avec succès.");
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de l'ajout de l'aide : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void validerAide(int aideId, int validateurId) {
	    String query = "UPDATE Aide SET estValide = true, idValidateur = ? WHERE idAide = ?";

	    try (
	         PreparedStatement pstmt = con.prepareStatement(query)) {
	        pstmt.setInt(1, validateurId);
	        pstmt.setInt(2, aideId);      

	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("L'aide a été validée avec succès.");
	        } else {
	            System.out.println("Aucune aide trouvée avec l'ID : " + aideId);
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de la validation de l'aide : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public void prendreAide(int aideId, int benevoleId) {
	    String query = "UPDATE Aide SET idBenevole = ? WHERE idAide = ?";

	    try (PreparedStatement pstmt = con.prepareStatement(query)) {
	        pstmt.setInt(1, benevoleId);  
	        pstmt.setInt(2, aideId);     

	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("L'aide a été attribuée au bénévole avec succès.");
	        } else {
	            System.out.println("Aucune aide trouvée avec l'ID : " + aideId);
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de l'attribution de l'aide au bénévole : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public Aide getAideById(int idAide) {
	    Aide aide = null; 

	    String query = "SELECT * FROM Aide WHERE idAide = ?";

	    try (PreparedStatement pstmt = con.prepareStatement(query)) {

	        pstmt.setInt(1, idAide);

	        ResultSet rs = pstmt.executeQuery();


	        if (rs.next()) {
	            int id = rs.getInt("idAide");
	            String description = rs.getString("description");
	            boolean estValide = rs.getBoolean("estValide");
	            int idDemandeur = rs.getInt("idDemandeur");

	            Integer idBenevole = rs.getObject("idBenevole", Integer.class);  
	            Integer idValidateur = rs.getObject("idValidateur", Integer.class);  


	            if (idBenevole == null) {
	                idBenevole = -1;  
	            }
	            if (idValidateur == null) {
	                idValidateur = -1; 
	            }

	            aide = new Aide(id, description, estValide, idDemandeur, idBenevole, idValidateur);
	        }
	    } catch (SQLException e) {
	        System.err.println("Erreur lors de la récupération de l'aide : " + e.getMessage());
	        e.printStackTrace();
	    }

	    return aide; 
	}
	


}
