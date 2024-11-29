package fr.insa.mas.orchestratorService.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.mas.orchestratorService.model.Aide;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorRessource {
	
	@Autowired
	private RestTemplate restTemplate;
	private boolean running;
	public static Connection con = null; 
	
	@GetMapping("/getAidesBenevole/{id}")
	public List<Aide> getAideByBenevole(@PathVariable int id) {

		
		connexionBDD();
	    ResponseEntity<List<Aide>> response = restTemplate.exchange(
	            "http://aideManagement/aide/getByBenevole/" + id,
	            HttpMethod.GET,
	            null,
	            new ParameterizedTypeReference<List<Aide>>() {}
	        );
	        
	        List<Aide> aides = response.getBody();
		closeConnection();
		return aides;
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

}
