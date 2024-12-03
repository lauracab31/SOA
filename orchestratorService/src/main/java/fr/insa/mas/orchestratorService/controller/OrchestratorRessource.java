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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.mas.orchestratorService.model.Aide;
import fr.insa.mas.orchestratorService.model.Benevole;
import fr.insa.mas.orchestratorService.model.Demandeur;
import fr.insa.mas.orchestratorService.model.Validateur;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorRessource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/getAidesBenevole/{id}")
	public List<Aide> getAideByBenevole(@PathVariable int id) {

	    ResponseEntity<List<Aide>> response = restTemplate.exchange(
	            "http://aideManagement/aide/getByBenevole/" + id,
	            HttpMethod.GET,
	            null,
	            new ParameterizedTypeReference<List<Aide>>() {}
	        );
	        
	        List<Aide> aides = response.getBody();
		return aides;
	}
	
    @PostMapping("/addAide/{message}/{demandeur}")
    public void ajoutAide(@PathVariable String message, @PathVariable int demandeur) {
        restTemplate.postForEntity(
            "http://aideManagement/aide/add/" + message + "/" + demandeur,
            null,
            Void.class
        );
    }
	
    @PostMapping("/validateAide/{aideId}/{validateurId}")
    public void valideAide(@PathVariable int aideId, @PathVariable int validateurId) {
        restTemplate.postForEntity(
            "http://aideManagement/aide/valider/" + aideId + "/" + validateurId,
            null,
            Void.class
        );
    }

    @PostMapping("/chooseAide/{aideId}/{benevoleId}")
    public void choisirAide(@PathVariable int aideId, @PathVariable int benevoleId) {
        restTemplate.postForEntity(
            "http://aideManagement/aide/choisir/" + aideId + "/" + benevoleId,
            null,
            Void.class
        );
    }

    @GetMapping("/getAide/{aideId}")
    public Aide getAideById(@PathVariable int aideId) {
        ResponseEntity<Aide> response = restTemplate.exchange(
            "http://aideManagement/aide/get/" + aideId,
            HttpMethod.GET,
            null,
            Aide.class
        );
        return response.getBody();
    }
    
    @PostMapping("/addBenevole/{username}/{password}")
    public void addBenevole(@PathVariable String username, @PathVariable String password) {
        restTemplate.postForEntity(
            "http://userManagementMS/user/benevole/" + username + "/" + password,
            null,
            Void.class
        );
    }

    @PostMapping("/addDemandeur/{username}/{password}")
    public void addDemandeur(@PathVariable String username, @PathVariable String password) {
        restTemplate.postForEntity(
            "http://userManagementMS/user/demandeur/" + username + "/" + password,
            null,
            Void.class
        );
    }

    @PostMapping("/addValidateur/{username}/{password}")
    public void addValidateur(@PathVariable String username, @PathVariable String password) {
        restTemplate.postForEntity(
            "http://userManagementMS/user/validateur/" + username + "/" + password,
            null,
            Void.class
        );
    }


    @GetMapping("/getUserId/{username}/{password}")
    public int getUserId(@PathVariable String username, @PathVariable String password) {
        ResponseEntity<Integer> response = restTemplate.exchange(
            "http://userManagementMS/user/getId/" + username + "/" + password,
            HttpMethod.GET,
            null,
            Integer.class
        );
        return response.getBody();
    }

    @GetMapping("/getRole/{username}/{password}")
    public int getRole(@PathVariable String username, @PathVariable String password) {
        ResponseEntity<Integer> response = restTemplate.exchange(
            "http://userManagementMS/user/role/" + username + "/" + password,
            HttpMethod.GET,
            null,
            Integer.class
        );
        return response.getBody();
    }

    @GetMapping("/getBenevole/{id}")
    public Benevole getBenevole(@PathVariable int id) {
        ResponseEntity<Benevole> response = restTemplate.exchange(
            "http://userManagementMS/user/getBenevole/" + id,
            HttpMethod.GET,
            null,
            Benevole.class
        );
        return response.getBody();
    }

    @GetMapping("/getDemandeur/{id}")
    public Demandeur getDemandeur(@PathVariable int id) {
        ResponseEntity<Demandeur> response = restTemplate.exchange(
            "http://userManagement/userMS/getDemandeur/" + id,
            HttpMethod.GET,
            null,
            Demandeur.class
        );
        return response.getBody();
    }

    @GetMapping("/getValidateur/{id}")
    public Validateur getValidateur(@PathVariable int id) {
        ResponseEntity<Validateur> response = restTemplate.exchange(
            "http://userManagementMS/user/getValidateur/" + id,
            HttpMethod.GET,
            null,
            Validateur.class
        );
        return response.getBody();
    }
	

}
