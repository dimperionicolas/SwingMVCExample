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

public class SalesDao {

	// Instanciar conexion
	ConnectionMySQL cn = new ConnectionMySQL();
	Connection conn;
	PreparedStatement pst;
	ResultSet rs;

	public boolean registerSaleQuery(int customer_id, int employee_id, double total) {
		String query = "INSERT INTO sales (customer_id , employee_id, total, sale_date) " + "VALUES(?,?,?,?)";
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.setInt(1, customer_id);
			pst.setInt(2, employee_id);
			pst.setDouble(3, total);
			pst.setTimestamp(4, timestamp);
			pst.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al registtrar la venta: " + e.toString());
			return false;
		}
	}

	public boolean registerSaleDetailQuery(int product_id, int sale_id, int sale_quantity, double sale_price,
			double sale_subtotal) {
		String query = "INSERT INTO sale_detail " + "(product_id,  sale_id, sale_quantity, sale_price, "
				+ "sale_subtotal) VALUES(?,?,?,?,?)";
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			pst.setInt(1, product_id);
			pst.setInt(2, sale_id);
			pst.setInt(3, sale_quantity);
			pst.setDouble(4, sale_price);
			pst.setDouble(5, sale_subtotal);
			pst.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al registtrar los detalles de la venta: " + e.toString());
			return false;
		}
	}

	public int saleId() {
		int id = 0;
		String query = "SELECT MAX(id) AS id FROM sales";
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener max id: " + e.toString());
		}
		return id;
	}

	// Listar todas las ventas realizadas
	public List<Sales> listAllSalesQuery() {
		List<Sales> list_sales = new ArrayList<>();
		String query = "SELECT s.id AS invoice, c.full_name AS customer, e.full_name AS employee, s.total, s.created FROM sales s INNER JOIN customers c ON s.customer_id = c.id INNER JOIN employees e ON s.employe_id = e.id ORDER BY s.id ASC";
		try {
			conn = cn.getConnection();
			pst = conn.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				Sales sales = new Sales();
				sales.setId(rs.getInt("invoice"));
				sales.setCustomer_name(rs.getString("customer"));
				sales.setEmployee_name(rs.getString("employee"));
				sales.setTotal_to_pay(rs.getDouble("total"));
				sales.setSale_date(rs.getString("sale_date"));
				list_sales.add(sales);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener lista de compras: " + e.toString());
		}
		return list_sales;
	}
}
