package controllers;

import static dao.EmployeesDao.id_user;
import static dao.EmployeesDao.rol_user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.CustomersDao;
import dao.ProductsDao;
import dao.SalesDao;
import models.Customers;
import models.Products;
import models.Sales;
import views.base.AbstractSystemView;

public class SalesController implements KeyListener, ActionListener, MouseListener {

	private Sales sale;
	private SalesDao saleDao;
	private final AbstractSystemView views;
	Products product = new Products();
	ProductsDao productDao = new ProductsDao();

	private int item = 0;
	String rol = rol_user;

	DefaultTableModel model = new DefaultTableModel();
	DefaultTableModel temp;

	// Instanciar el modelo productos
	public SalesController(Sales sale, SalesDao saleDao, AbstractSystemView views) {
		this.sale = sale;
		this.saleDao = saleDao;
		this.views = views;

		// Botón de agregar
		this.views.btn_sale_new.addActionListener(this);
		// Botón de comprar
		this.views.btn_sale_confirm.addActionListener(this);
		// Botón de eliminar compra
		this.views.btn_sale_remove.addActionListener(this);
//		this.views.txt_purchase_unit_code.addKeyListener(this);
//		this.views.txt_purchase_unit_price.addKeyListener(this);
//		this.views.btn_purchase_new.addActionListener(this);
		this.views.jlabel_purchases.addMouseListener(this);
		this.views.jlabel_reports.addMouseListener(this);
		listAllSales();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == views.btn_sale_confirm) {

			insertSale();

		} else if (e.getSource() == views.btn_sale_new) {

			cleanFieldsSales();

			cleanTableTemp();

		} else if (e.getSource() == views.btn_sale_remove) {

			model = (DefaultTableModel) views.sales_table.getModel();

			model.removeRow(views.sales_table.getSelectedRow());

			calculateSales();

			views.txt_sale_product_code.requestFocus();

		} else if (!"".equals(e.getSource() == views.btn_sale_add_product)) {

			// Agregar productos a la tabla de ventas temporalmente

			int amount = Integer.parseInt(views.txt_sale_quantity.getText());

			String product_name = views.txt_sale_product_name.getText();

			double price = Double.parseDouble(views.txt_sale_price.getText());

			int sale_id = Integer.parseInt(views.txt_sale_product_id.getText());

			double subtotal = amount * price;

			int stock = Integer.parseInt(views.txt_sale_stock.getText());

			String full_name = views.txt_sale_customer_name.getText();

			if (stock >= amount) {

				item = item + 1;

				temp = (DefaultTableModel) views.sales_table.getModel();

				for (int i = 0; i < views.sales_table.getRowCount(); i++) {

					if (views.sales_table.getValueAt(i, 1).equals(views.txt_sale_product_name.getText())) {

						JOptionPane.showMessageDialog(null, "El producto ya esta registrado en la tabla de ventas");

						return;

					}

				}

				ArrayList list = new ArrayList();

				list.add(item);

				list.add(sale_id);

				list.add(product_name);

				list.add(amount);

				list.add(price);

				list.add(subtotal);

				list.add(full_name);

				Object[] obj = new Object[6];

				obj[0] = list.get(1);

				obj[1] = list.get(2);

				obj[2] = list.get(3);

				obj[3] = list.get(4);

				obj[4] = list.get(5);

				obj[5] = list.get(6);

				temp.addRow(obj);

				views.sales_table.setModel(temp);

				calculateSales();

				cleanFieldsSales();

				views.txt_sale_product_code.requestFocus();

			} else {

				JOptionPane.showMessageDialog(null, "Stock no disponible");

			}

		} else {

			JOptionPane.showMessageDialog(null, "Ingrese cantidad");

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == views.jlabel_sales) {
			views.panel_tab_menu_options.setSelectedIndex(2);
		} else if (e.getSource() == views.jlabel_reports) {
			if (rol.equals("Administrador".toUpperCase())) {
				views.panel_tab_menu_options.setSelectedIndex(7);
				listAllSales();
			} else {
				views.panel_tab_menu_options.setEnabledAt(7, false);
				views.jlabel_reports.setEnabled(false);
				JOptionPane.showMessageDialog(null, "No tiene privilegios de administrador para acceder a esta vista");
			}
		}
	}

	private void insertSale() {
		int customer_id = Integer.parseInt(views.txt_sale_customer_id.getText());
		int employee_id = id_user;
		double total = Double.parseDouble(views.txt_sale_total_to_pay.getText());
		if (saleDao.registerSaleQuery(customer_id, employee_id, total)) {
			Products product = new Products();
			ProductsDao productDao = new ProductsDao();
			int sale_id = saleDao.saleId();
			for (int i = 0; i < views.sales_table.getRowCount(); i++) {
				int product_id = Integer.parseInt(views.sales_table.getValueAt(i, 0).toString());
				int sale_quantity = Integer.parseInt(views.sales_table.getValueAt(i, 2).toString());
				double sale_price = Double.parseDouble(views.sales_table.getValueAt(i, 3).toString());
				double sale_subtotal = sale_quantity * sale_price;
				saleDao.registerSaleDetailQuery(product_id, sale_id, sale_quantity, sale_price, sale_subtotal);
				// Traer la cantidad de productos
				product = productDao.searchId(product_id);
				// Obtener cantidad actual y restar la cantidad comprada
				int amount = product.getProduct_quantity() - sale_quantity;
				productDao.updateStockQuery(amount, product_id);
			}
			JOptionPane.showMessageDialog(null, "Venta generada");
			cleanTableTemp();
			cleanFieldsSales();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == views.txt_sale_product_code) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!"".equals(views.txt_sale_product_code.getText())) {
					int code = Integer.parseInt(views.txt_sale_product_code.getText());
					product = productDao.searchCode(code);
					if (product.getName() != null) {
						views.txt_sale_product_name.setText(product.getName());
						views.txt_sale_product_id.setText("" + product.getId());
						views.txt_sale_stock.setText("" + product.getProduct_quantity());
						views.txt_sale_price.setText("" + product.getUnit_price());
						views.txt_sale_quantity.requestFocus();
					} else {
						JOptionPane.showMessageDialog(null, "No existe ningún producto con ese código");
						cleanFieldsSales();
						views.txt_sale_product_code.requestFocus();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Ingrese el código del producto a vender");
				}
			}
		} else if (e.getSource() == views.txt_sale_customer_id) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				Customers customer = new Customers();
				CustomersDao customerDao = new CustomersDao();
				if (!"".equals(views.txt_sale_customer_id.getText())) {
					int customer_id = Integer.parseInt(views.txt_sale_customer_id.getText());
					customer = customerDao.listCustomersQuery("" + customer_id).get(0);
					// TODO esto devuelve una lista de uno
					if (customer.getFull_name() != null) {
						views.txt_sale_customer_name.setText("" + customer.getFull_name());
					} else {
						views.txt_sale_customer_id.setText("");
						JOptionPane.showMessageDialog(null, "El cliente no existe");
					}
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == views.txt_sale_quantity) {
			int quantity;
			double price = Double.parseDouble(views.txt_sale_price.getText());
			if (views.txt_sale_quantity.getText().equals("")) {
				quantity = 1;
				views.txt_sale_price.setText("" + price);
			} else {
				quantity = Integer.parseInt(views.txt_sale_quantity.getText());
				price = Double.parseDouble(views.txt_sale_price.getText());
				views.txt_sale_subtotal.setText("" + quantity * price);
			}
		}
	}

	// Limpiar tabla

	public void cleanTable() {

		for (int i = 0; i < model.getRowCount(); i++) {

			model.removeRow(i);

			i = i - 1;

		}

	}

	// Limpiar tabla temporal

	public void cleanTableTemp() {

		for (int i = 0; i < temp.getRowCount(); i++) {

			temp.removeRow(i);

			i = i - 1;

		}

	}

	// Limpiar algunos campos
	public void cleanFieldsSales() {
		views.txt_sale_product_code.setText("");
		views.txt_sale_product_name.setText("");
		views.txt_sale_quantity.setText("");
		views.txt_sale_product_id.setText("");
		views.txt_sale_price.setText("");
		views.txt_sale_subtotal.setText("");
		views.txt_sale_stock.setText("");
	}

	// Limpiar todos los campos
	public void cleanAllFieldsSales() {
		views.txt_sale_product_code.setText("");
		views.txt_sale_product_name.setText("");
		views.txt_sale_quantity.setText("");
		views.txt_sale_product_id.setText("");
		views.txt_sale_price.setText("");
		views.txt_sale_subtotal.setText("");
		views.txt_sale_customer_id.setText("");
		views.txt_sale_customer_name.setText("");
		views.txt_sale_total_to_pay.setText("");
		views.txt_sale_stock.setText("");
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	// Calcular total a pagar tabla de ventas
	private void calculateSales() {
		double total = 0.00;
		int numRow = views.sales_table.getRowCount();
		for (int i = 0; i < numRow; i++) {
			total = total + Double.parseDouble(String.valueOf(views.sales_table.getValueAt(i, 4)));
		}
		views.txt_sale_total_to_pay.setText("" + total);
	}

	// Listar todas las ventas
	public void listAllSales() {
		if (rol.equals("Administrador".toUpperCase())) {
			List<Sales> list = saleDao.listAllSalesQuery();
			model = (DefaultTableModel) views.sales_table.getModel();
			// Recorrer la lista
			Object[] row = new Object[5];
			for (int i = 0; i < list.size(); i++) {
				row[0] = list.get(i).getId();
				row[1] = list.get(i).getCustomer_name();
				row[2] = list.get(i).getEmployee_name();
				row[3] = list.get(i).getTotal_to_pay();
				row[4] = list.get(i).getSale_date();
				model.addRow(row);
			}
			views.sales_table.setModel(model);
		}
	}
}
