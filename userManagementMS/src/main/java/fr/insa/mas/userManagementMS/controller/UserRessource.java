package fr.insa.mas.userManagementMS.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.mas.userManagementMS.model.Benevole;
import fr.insa.mas.userManagementMS.model.Demandeur;
import fr.insa.mas.userManagementMS.model.Validateur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("/user")
public class UserRessource {
	private boolean running;
	public static Connection con = null; 

	
	@DeleteMapping(value="/benevole/del/{username}")
	public void benevoleDel(@PathVariable String username) {
		System.out.println("On a effacé l'utilisateur "+username);
	}

	
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
	
	@GetMapping("/getBenevole/{id}")
	public Benevole getBenevole(@PathVariable int id) {
		connexionBDD();
		Benevole b = getBenevoleById(id);
		closeConnection();
		return b;
	}
	
	@GetMapping("/getDemandeur/{id}")
	public Demandeur getDemandeur(@PathVariable int id) {
		connexionBDD();
		Demandeur d = getDemandeurById(id);
		closeConnection();
		return d;
	}
	
	@GetMapping("/getValidateur/{id}")
	public Validateur getValidateur(@PathVariable int id) {
		connexionBDD();
		Validateur v = getValidateurById(id);
		closeConnection();
		return v;
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
			
			public Benevole getBenevoleById(int id) {
			    Benevole benevole = null;

			    // Check if the ID exists in the Benevole table
			    String queryBenevole = "SELECT COUNT(*) AS count FROM Benevole WHERE idBenevole = ?";
			    boolean isBenevole = false;

			    try (PreparedStatement pstmt = con.prepareStatement(queryBenevole)) {
			        pstmt.setInt(1, id);
			        try (ResultSet rs = pstmt.executeQuery()) {
			            if (rs.next()) {
			                isBenevole = rs.getInt("count") > 0;
			            }
			        }
			    } catch (SQLException e) {
			        System.out.println("Error checking Benevole ID: " + e.getMessage());
			        return null;
			    }

			    if (!isBenevole) {
			        System.out.println("The given ID does not belong to a Benevole.");
			        return null;
			    }

			    // Retrieve the Benevole's information from the User table
			    String queryUser = "SELECT username, password FROM User WHERE idUser = ?";
			    try (PreparedStatement pstmt = con.prepareStatement(queryUser)) {
			        pstmt.setInt(1, id);
			        try (ResultSet rs = pstmt.executeQuery()) {
			            if (rs.next()) {
			                String username = rs.getString("username");
			                String password = rs.getString("password");

			                // Create the Benevole object with ID, username, and password
			                benevole = new Benevole(id, username, password);
			                System.out.println("Benevole found: " + username);
			            }
			        }
			    } catch (SQLException e) {
			        System.out.println("Error retrieving Benevole information: " + e.getMessage());
			    }

			    return benevole;
			}
			
			public Demandeur getDemandeurById(int id) {
			    Demandeur demandeur = null;

			    // Check if the ID exists in the Demandeur table
			    String queryDemandeur = "SELECT COUNT(*) AS count FROM Demandeur WHERE idDemandeur = ?";
			    boolean isDemandeur = false;

			    try (PreparedStatement pstmt = con.prepareStatement(queryDemandeur)) {
			        pstmt.setInt(1, id);
			        try (ResultSet rs = pstmt.executeQuery()) {
			            if (rs.next()) {
			                isDemandeur = rs.getInt("count") > 0;
			            }
			        }
			    } catch (SQLException e) {
			        System.out.println("Error checking Demandeur ID: " + e.getMessage());
			        return null;
			    }

			    if (!isDemandeur) {
			        System.out.println("The given ID does not belong to a Demandeur.");
			        return null;
			    }

			    // Retrieve the Demandeur's information from the User table
			    String queryUser = "SELECT username, password FROM User WHERE idUser = ?";
			    try (PreparedStatement pstmt = con.prepareStatement(queryUser)) {
			        pstmt.setInt(1, id);
			        try (ResultSet rs = pstmt.executeQuery()) {
			            if (rs.next()) {
			                String username = rs.getString("username");
			                String password = rs.getString("password");

			                // Create the Demandeur object with ID, username, and password
			                demandeur = new Demandeur(id, username, password);
			                System.out.println("Demandeur found: " + username);
			            }
			        }
			    } catch (SQLException e) {
			        System.out.println("Error retrieving Demandeur information: " + e.getMessage());
			    }

			    return demandeur;
			}
			
			@GetMapping("/validateur/{id}")
			public Validateur getValidateurById(int id) {
			    Validateur validateur = null;

			    // Check if the ID exists in the Validateur table
			    String queryValidateur = "SELECT COUNT(*) AS count FROM Validateur WHERE idValidateur = ?";
			    boolean isValidateur = false;

			    try (PreparedStatement pstmt = con.prepareStatement(queryValidateur)) {
			        pstmt.setInt(1, id);
			        try (ResultSet rs = pstmt.executeQuery()) {
			            if (rs.next()) {
			                isValidateur = rs.getInt("count") > 0;
			            }
			        }
			    } catch (SQLException e) {
			        System.out.println("Error checking Validateur ID: " + e.getMessage());
			        return null;
			    }

			    if (!isValidateur) {
			        System.out.println("The given ID does not belong to a Validateur.");
			        return null;
			    }

			    // Retrieve the Validateur's information from the User table
			    String queryUser = "SELECT username, password FROM User WHERE idUser = ?";
			    try (PreparedStatement pstmt = con.prepareStatement(queryUser)) {
			        pstmt.setInt(1, id);
			        try (ResultSet rs = pstmt.executeQuery()) {
			            if (rs.next()) {
			                String username = rs.getString("username");
			                String password = rs.getString("password");

			                // Create the Validateur object with ID, username, and password
			                validateur = new Validateur(id, username, password);
			                System.out.println("Validateur found: " + username);
			            }
			        }
			    } catch (SQLException e) {
			        System.out.println("Error retrieving Validateur information: " + e.getMessage());
			    }

			    return validateur;
			}

	

}
