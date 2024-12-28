package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {

	private String database_name = "pharmacy_database";

	private String user = "root";
	private String password = "MoraN2323!"; // local pass = "root";
	private String url = "jdbc:mysql://localhost:3308/" + database_name; // docker
//	private String url = "jdbc:mysql://localhost:3606/" + database_name; //local 
	Connection conn = null;

	public Connection getConnection() {
		try {
			// Esta línea carga la clase del controlador de MySQL en la JVM (Java Virtual
			// Machine).
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.err.print("Ha ocurrido un ClassNotFoundException " + e.getMessage());
		} catch (SQLException e) {
			System.err.print("Ha ocurrido un SQLException " + e.getMessage());
		}
		return conn;
	}

}
