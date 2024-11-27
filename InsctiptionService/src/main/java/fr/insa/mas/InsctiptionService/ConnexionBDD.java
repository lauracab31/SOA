package fr.insa.mas.InsctiptionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class ConnexionBDD {
	public static void main(String[] args) throws SQLException {
	
		boolean running=false;
	
		Connection con = null; 
	
		
		String DBurl = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_045";
		
		con = DriverManager.getConnection(DBurl, "projet_gei_045", "IG0aito0");
		System.out.println("Connexion établie");
	}
				
}