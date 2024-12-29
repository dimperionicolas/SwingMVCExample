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

import dao.ProductsDao;
import dao.PurchasesDao;
import models.Products;
import models.Purchases;
import utils.DynamicCombobox;
import views.Print;
import views.base.AbstractSystemView;

public class PurchasesController implements KeyListener, ActionListener, MouseListener {

	private Purchases purchase;
	private PurchasesDao purchaseDao;
	private AbstractSystemView views;
	private int getIdSupplier = 0;
	private int item = 0;
	DefaultTableModel model = new DefaultTableModel();
	DefaultTableModel temp;
	// Instanciar el modelo productos
	Products product = new Products();
	ProductsDao productDao = new ProductsDao();
	String rol = rol_user;

	public PurchasesController(Purchases purchase, PurchasesDao purchaseDao, AbstractSystemView views) {
		this.purchase = purchase;
		this.purchaseDao = purchaseDao;
		this.views = views;
		// Botón de agregar
		this.views.btn_purchase_add_to_buy.addActionListener(this);
		// Botón de comprar
		this.views.btn_purchase_confirm.addActionListener(this);
		// Botón de eliminar compra
		this.views.btn_purchase_remove.addActionListener(this);
		this.views.txt_purchase_unit_code.addKeyListener(this);
		this.views.txt_purchase_unit_price.addKeyListener(this);
		this.views.btn_purchase_new.addActionListener(this);
		this.views.jlabel_purchases.addMouseListener(this);
		this.views.jlabel_reports.addMouseListener(this);
		listAllPurchases();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO si no existe el producto, deberia poder comprar y hacer un insert en la
		// tabla products?

		if (e.getSource() == views.btn_purchase_add_to_buy) {
			DynamicCombobox supplier_cmb = (DynamicCombobox) views.cmb_purchase_supplier.getSelectedItem();
			int supplier_id = supplier_cmb.getId();

			if (getIdSupplier == 0) {
				getIdSupplier = supplier_id;
			} else {
				if (getIdSupplier != supplier_id) {
					JOptionPane.showMessageDialog(null, "No puede realizar una misma compra a varios proveedores");
				} else {
					int amount = Integer.parseInt(views.txt_purchase_amount.getText());
					String product_name = views.txt_purchase_product_name.getText();
					double price = Double.parseDouble(views.txt_purchase_unit_price.getText());
					int purchase_id = Integer.parseInt(views.txt_purchase_id.getText());
					String supplier_name = views.cmb_purchase_supplier.getSelectedItem().toString();

					if (amount > 0) {
						temp = (DefaultTableModel) views.purchase_table.getModel();
						for (int i = 0; i < views.purchase_table.getRowCount(); i++) {
							if (views.purchase_table.getValueAt(i, 1)
									.equals(views.txt_purchase_product_name.getText())) {
								JOptionPane.showMessageDialog(null,
										"El producto ya esta registrado en la tabla de compras");
								return;
							}
						}

						ArrayList list = new ArrayList();
						item += 1;
						list.add(item);
						list.add(purchase_id);
						list.add(product_name);
						list.add(amount);
						list.add(price);
						list.add(amount * price);
						list.add(supplier_name);

						Object[] obj = new Object[6];
						obj[0] = list.get(1);
						obj[1] = list.get(2);
						obj[2] = list.get(3);
						obj[3] = list.get(4);
						obj[4] = list.get(5);
						obj[5] = list.get(6);
						temp.addRow(obj);
						views.purchase_table.setModel(temp);
						cleanFieldsPurchases();
						views.cmb_purchase_supplier.setEditable(false);
						views.txt_purchase_unit_code.requestFocus();
						calculatePurchase();
					}
				}
			}
		} else if (e.getSource() == views.btn_purchase_confirm) {
			insertPurchase();
		} else if (e.getSource() == views.btn_purchase_remove) {
			model = (DefaultTableModel) views.purchase_table.getModel();
			model.removeRow(views.purchase_table.getSelectedRow());
			calculatePurchase();
			views.txt_purchase_unit_code.requestFocus();
		} else if (e.getSource() == views.btn_purchase_new) {
			cleanTableTemp();
			cleanFieldsPurchases();
		}
	}

	private void insertPurchase() {
		double total = Double.parseDouble(views.txt_purchase_total.getText());
		int employee_id = id_user;

		if (purchaseDao.registerPurchaseQuery(getIdSupplier, employee_id, total)) {
			int purchase_id = purchaseDao.purchaseId();
			for (int i = 0; i < views.purchase_table.getRowCount(); i++) {
				int product_id = Integer.parseInt(views.purchase_table.getValueAt(i, 0).toString());
				int purchase_amount = Integer.parseInt(views.purchase_table.getValueAt(i, 2).toString());
				double purchase_price = Double.parseDouble(views.purchase_table.getValueAt(i, 3).toString());
				double purchase_subtotal = purchase_price * purchase_amount;

				// Registrar detalles de la compra
				purchaseDao.registerPurchaseDetailQuery(purchase_id, purchase_price, purchase_amount, purchase_subtotal,
						product_id);

				// Traer la cantidad de productos
				product = productDao.searchId(product_id);
				int amount = product.getProduct_quantity() + purchase_amount;

				productDao.updateStockQuery(amount, product_id);
			}
			cleanTableTemp();
			cleanFieldsPurchases();
			JOptionPane.showMessageDialog(null, "Compra generada con éxito");
			Print print = new Print(purchase_id);
			print.setVisible(true);
		}
	}

	// Método para listar las compras realizadas
	public void listAllPurchases() {
		if (rol.equals("Administrador".toUpperCase()) || rol.equals("Auxiliar".toUpperCase())) {
			List<Purchases> list = purchaseDao.listAllPurchasesQuery();
			model = (DefaultTableModel) views.purchase_report_table.getModel();
			Object[] row = new Object[4];
			// Recorrer con ciclo for
			for (int i = 0; i < list.size(); i++) {
				row[0] = list.get(i).getId();
				row[1] = list.get(i).getSupplier_name_product();
				row[2] = list.get(i).getTotal();
				row[3] = list.get(i).getCreated();
				model.addRow(row);
			}
			views.purchase_report_table.setModel(model);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source == views.jlabel_purchases) {
			if (rol.equals("Administrador".toUpperCase())) {
				views.panel_tab_menu_options.setSelectedIndex(2);
				cleanTable();
			} else {
				views.panel_tab_menu_options.setEnabledAt(2, false);
				views.jlabel_purchases.setEnabled(false);
				JOptionPane.showMessageDialog(null, "No tiene privilegios de administrador para ingresar a esta vista");
			}
		} else if (e.getSource() == views.jlabel_reports) {
			views.panel_tab_menu_options.setSelectedIndex(7);
			cleanTable();
			listAllPurchases();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == views.txt_purchase_unit_code) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (views.txt_purchase_unit_code.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Ingresa el código del producto a comprar");
				} else {
					int id = Integer.parseInt(views.txt_purchase_unit_code.getText());
					product = productDao.searchCode(id);
					views.txt_purchase_product_name.setText(product.getName());
					views.txt_purchase_id.setText("" + product.getId());
					views.txt_purchase_amount.requestFocus();
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == views.txt_purchase_unit_price) {
			int quantity;
			double price = 0.0;

			if (views.txt_purchase_amount.getText().equals("")) {
				quantity = 1;
				views.txt_purchase_unit_price.setText("" + price);
			} else {
				quantity = Integer.parseInt(views.txt_purchase_amount.getText());
				price = Double.parseDouble(views.txt_purchase_unit_price.getText());
				views.txt_purchase_subtotal.setText("" + quantity * price);
			}
		}
	}

	// Limpiar campos
	public void cleanFieldsPurchases() {
		views.txt_purchase_product_name.setText("");
		views.txt_purchase_unit_price.setText("");
		views.txt_purchase_amount.setText("");
		views.txt_purchase_unit_code.setText("");
		views.txt_purchase_subtotal.setText("");
		views.txt_purchase_id.setText("");
		views.txt_purchase_total.setText("");
	}

	// Calcular total a pagar
	public void calculatePurchase() {
		double total = 0.00;
		int numRow = views.purchase_table.getRowCount();
		for (int i = 0; i < numRow; i++) {
			// Pasar el indice de la columna que se sumará
			total = total + Double.parseDouble(String.valueOf(views.purchase_table.getValueAt(i, 4)));
		}
		views.txt_purchase_total.setText("" + total);
	}

	// Limpiar tabla temporal
	public void cleanTableTemp() {
		for (int i = 0; i < temp.getRowCount(); i++) {
			temp.removeRow(i);
			i = i - 1;
		}
	}

	// Limpiar tabla
	public void cleanTable() {
		for (int i = 0; i < model.getRowCount(); i++) {
			model.removeRow(i);
			i = i - 1;
		}
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
}
