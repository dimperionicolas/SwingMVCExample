package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CustomersDao {

	// Instanciar conexion
	ConnectionMySQL cn = new ConnectionMySQL();
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;

	public boolean registerCustomerQuery(Customers customer) {
		String query = "INSERT INTO customers (id, full_name, address, telephone, email, created, updated)"
				+ "VALUES(?,?,?,?,?,?,?)";
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.setInt(1, customer.getId());
			pst.setString(2, customer.getFull_name());
			pst.setString(3, customer.getAddress());
			pst.setString(4, customer.getTelephone());
			pst.setString(5, customer.getEmail());
			pst.setTimestamp(6, timestamp);
			pst.setTimestamp(7, timestamp);
			pst.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al registtrar el cliente: " + e.toString());
			return false;
		}
	}

	public List<Customers> listCustomersQuery(String value) {
		List<Customers> list_customers = new ArrayList<>();
		String query = "SELECT * FROM customers";
		String query_search_customer = "SELECT * FROM customers WHERE id LIKE '%" + value + "%'";
		try {
			conn = cn.getConnection();
			if (value.equalsIgnoreCase("")) {
				pst = conn.prepareStatement(query);
				rs = pst.executeQuery();
			} else {
				pst = conn.prepareStatement(query_search_customer);
				rs = pst.executeQuery();
			}
			while (rs.next()) {
				Customers customer = new Customers();
				customer.setId(rs.getInt("id"));
				customer.setFull_name(rs.getString("full_name"));
				customer.setAddress(rs.getString("address"));
				customer.setTelephone(rs.getString("telephone"));
				customer.setEmail(rs.getString("email"));
				list_customers.add(customer);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener clientes: " + e.toString());
			return List.of();
		}
		return list_customers;
	}

	public boolean updateCustomerQuery(Customers customer) {
		String query = "UPDATE customers SET full_name = ?, address = ?, telephone = ?, email = ?, updated = ? WHERE id = ?";
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.setString(1, customer.getFull_name());
			pst.setString(2, customer.getAddress());
			pst.setString(3, customer.getTelephone());
			pst.setString(4, customer.getEmail());
			pst.setTimestamp(5, timestamp);
			pst.setInt(6, customer.getId());
			pst.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al modificar los datos del cliente: " + e.toString());
			return false;
		}
	}

	public boolean deleteCustomerQuery(int id) {
		String query = "DELETE FROM customers WHERE id = " + id;
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"No puedes eliminar un cliente que tenga relacion con otra tabla: " + e.toString());
			return false;
		}
	}

}