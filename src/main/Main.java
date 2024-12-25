package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import models.ConnectionMySQL;
import views.LoginView;

public class Main {
	public static void main(String[] args) {

		LoginView login = new LoginView();
		login.setVisible(true);

//		checkConnection();
	}

	private static void checkConnection() {

		ConnectionMySQL connectionMySQL = new ConnectionMySQL();
		// Este metodo lo uso de prueba para saber si conecto correctamente
		//
		Connection connection = null;
		try {
			// Obtener la conexión
			connection = connectionMySQL.getConnection();

			// Verificar si la conexión no es nula
			if (connection != null) {
				System.out.println("Conexión exitosa!");

				// Ejecutar una consulta simple (por ejemplo, listar las tablas de la base de
				// datos)
				String query = "SHOW TABLES";
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);

				System.out.println("Tablas en la base de datos:");
				while (resultSet.next()) {
					System.out.println(resultSet.getString(1)); // Imprime el nombre de cada tabla
				}
			} else {
				System.out.println("La conexión ha fallado.");
			}
		} catch (Exception e) {
			System.err.println("Error al probar la conexión: " + e.getMessage());
		} finally {
			// Cerrar la conexión
			try {
				if (connection != null) {
					connection.close();
					System.out.println("Conexión cerrada.");
				}
			} catch (Exception e) {
				System.err.println("Error al cerrar la conexión: " + e.getMessage());
			}
		}
	}
}
