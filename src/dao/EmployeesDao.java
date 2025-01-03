package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import models.Employees;
import utils.ConnectionMySQL;

public class EmployeesDao {
	// Instanciar conexion
	ConnectionMySQL cn = new ConnectionMySQL();
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;

	// Variables para enviar datos entre interfaces
//	public static int id_user = 0;
//	public static String full_name_user = "";
//	public static String username_user = "";
//	public static String address_user = "";
//	public static String rol_user = "";
//	public static String email_user = "";
//	public static String telephone_user = "";

	// Metodo login
	public Employees loginQuery(String user, String password) {
		String query = "SELECT * FROM employees WHERE username = ? AND password = ?";
		Employees employee = new Employees();
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, user);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				// TODO esto es optimo?
				employee.setId(rs.getInt("id"));
//				id_user = employee.getId();
				employee.setFull_name(rs.getString("full_name"));
//				full_name_user = employee.getFull_name();
				employee.setUsername(rs.getString("username"));
//				username_user = employee.getUsername();
				employee.setAddress(rs.getString("address"));
//				address_user = employee.getAddress();
				employee.setTelephone(rs.getString("telephone"));
//				telephone_user = employee.getTelephone();
				employee.setEmail(rs.getString("email"));
//				email_user = employee.getEmail();
				employee.setRol(rs.getString("rol"));
//				rol_user = employee.getRol();
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener empleado: " + e.toString());
		}
		return employee;
	}

	public boolean registerEmployeeQuery(Employees employee) {
		String query = "INSERT INTO employees (id, full_name, username, address, telephone, email, password, rol, created, updated)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.setInt(1, employee.getId());
			pst.setString(2, employee.getFull_name());
			pst.setString(3, employee.getUsername());
			pst.setString(4, employee.getAddress());
			pst.setString(5, employee.getTelephone());
			pst.setString(6, employee.getEmail());
			pst.setString(7, employee.getPassword());
			pst.setString(8, employee.getRol());
			pst.setTimestamp(9, timestamp);
			pst.setTimestamp(10, timestamp);
			pst.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al registtrar los empleados: " + e.toString());
			return false;
		}
	}

	public List<Employees> listEmployeesQuery(String value) {
		List<Employees> list_employees = new ArrayList<>();
		String query = "SELECT * FROM employees ORDER BY rol ASC";
		String query_search_employees = "SELECT * FROM employees WHERE id LIKE '%" + value + "%'";
		try {
			conn = cn.getConnection();
			if (value.equalsIgnoreCase("")) {
				pst = conn.prepareStatement(query);
				rs = pst.executeQuery();
			} else {
				pst = conn.prepareStatement(query_search_employees);
				rs = pst.executeQuery();
			}
			while (rs.next()) {
				Employees employee = new Employees();
				employee.setId(rs.getInt("id"));
				employee.setFull_name(rs.getString("full_name"));
				employee.setUsername(rs.getString("username"));
				employee.setAddress(rs.getString("address"));
				employee.setTelephone(rs.getString("telephone"));
				employee.setEmail(rs.getString("email"));
				employee.setRol(rs.getString("rol"));
				list_employees.add(employee);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener empleados: " + e.toString());
			return List.of();
		}
		return list_employees;
	}

	public boolean updateEmployeeQuery(Employees employee) {
		String query = "UPDATE employees SET full_name = ?, username = ?, address = ?, telephone = ?, email = ?,  rol = ?,  updated = ? WHERE id = ?";
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, employee.getFull_name());
			pst.setString(2, employee.getUsername());
			pst.setString(3, employee.getAddress());
			pst.setString(4, employee.getTelephone());
			pst.setString(5, employee.getEmail());
			pst.setString(6, employee.getRol());
			pst.setTimestamp(7, timestamp);
			pst.setInt(8, employee.getId());
			pst.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al modificar los datos del empleado: " + e.toString());
			return false;
		}
	}

	public boolean deleteEmployeeQuery(int id) {
		String query = "DELETE FROM employees WHERE id = " + id;
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"No puedes eliminar un empleado que tenga relacion con otra tabla: " + e.toString());
			return false;
		}
	}

	public boolean updateEmployeePasswordQuery(Employees employee) {
		String username = employee.getUsername();
		String query = "UPDATE employees SET password = ? WHERE username = '" + username + "'";
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, employee.getPassword());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al modificar los datos del empleado: " + e.toString());
			return false;
		}
	}

	public boolean exists(int id) {
		// TODO Auto-generated method stub
		return false;
	}
}
