package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import utils.ConnectionMySQL;
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

/*
 * TODO tipo de contenido en diferentes textbox TODO limpiar textbox si se
 * ingresa incorrecto TODO enter para ingresar TODO correccion de tabulaciones
 * TODO enter para cada boton segun corresponda TODO sort en las columas tablas
 * donde corresponda TODO para que le paso el modelo y el DAO por constructor si
 * lo puedo crear en el controlador? TODO si ya envio errores desde el DAO,
 * deberia enviar los errores personalizados?
 */
