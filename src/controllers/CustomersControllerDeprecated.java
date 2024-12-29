package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import models.Customers;
import models.CustomersDao;
import views.AbstractSystemView;

public class CustomersControllerDeprecated implements ActionListener, MouseListener, KeyListener {

	private Customers customer;
	private CustomersDao customerDao;
	private AbstractSystemView views;

	DefaultTableModel model = new DefaultTableModel();

	public CustomersControllerDeprecated(Customers customer, CustomersDao customerDao, AbstractSystemView views) {
		this.customer = customer;
		this.customerDao = customerDao;
		this.views = views;
		// Botón de registrar cliente
		this.views.btn_customer_register.addActionListener(this);
		// Botón de modificar cliente
		this.views.btn_customer_update.addActionListener(this);
		// Botón de eliminar cliente
		this.views.btn_customer_delete.addActionListener(this);
		// Botón de cancelar
		this.views.btn_customer_cancel.addActionListener(this);
		// Buscador
		this.views.txt_customer_search.addKeyListener(this);
		this.views.jlabel_customers.addMouseListener(this);
		this.views.customers_table.addMouseListener(this);
		listAllCustomers();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == views.btn_customer_register) {
			// Verificar si los campos estan vacios
			if (views.txt_customer_id.getText().equals("") || views.txt_customer_fullname.getText().equals("")
					|| views.txt_customer_address.getText().equals("")
					|| views.txt_customer_telephone.getText().equals("")
					|| views.txt_customer_email.getText().equals("")) {

				JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
			} else {// TODO integer error
				customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
				customer.setFull_name(views.txt_customer_fullname.getText().trim());
				customer.setAddress(views.txt_customer_address.getText().trim());
				customer.setTelephone(views.txt_customer_telephone.getText().trim());
				customer.setEmail(views.txt_customer_email.getText().trim());

				if (customerDao.registerCustomerQuery(customer)) {
					cleanFields();
					cleanTable();
					listAllCustomers();
					JOptionPane.showMessageDialog(null, "Cliente registrado con éxito");
				} else {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al cliente");
				}
			}
		} else if (e.getSource() == views.btn_customer_update) {
			if (views.txt_customer_id.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
			} else {
				if (views.txt_customer_id.getText().equals("") || views.txt_customer_fullname.getText().equals("")
						|| views.txt_customer_address.getText().equals("")
						|| views.txt_customer_telephone.getText().equals("")
						|| views.txt_customer_email.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
				} else {
					customer.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
					customer.setFull_name(views.txt_customer_fullname.getText().trim());
					customer.setAddress(views.txt_customer_address.getText().trim());
					customer.setTelephone(views.txt_customer_telephone.getText().trim());
					customer.setEmail(views.txt_customer_email.getText().trim());

					if (customerDao.updateCustomerQuery(customer)) {
						cleanTable();
						cleanFields();
						listAllCustomers();
						views.btn_customer_register.setEnabled(true);
						JOptionPane.showMessageDialog(null, "Datos del cliente modificados con éxito");
					} else {
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del cliente");
					}
				}
			}
		} else if (e.getSource() == views.btn_customer_delete) {
			int row = views.customers_table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Debes seleccionar un cliente para eliminar");
			} else {
				int id = Integer.parseInt(views.customers_table.getValueAt(row, 0).toString());
				int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar a este cliente?");

				if (question == 0 && customerDao.deleteCustomerQuery(id) != false) {
					cleanTable();
					cleanFields();
					views.btn_customer_register.setEnabled(true);
					listAllCustomers();
					JOptionPane.showMessageDialog(null, "Cliente eliminado con éxito");
				}
			}
		} else if (e.getSource() == views.btn_customer_cancel) {
			views.btn_customer_register.setEnabled(true);
			cleanFields();
		}
	}

	// Listar clientes
	public void listAllCustomers() {
		List<Customers> list = customerDao.listCustomersQuery(views.txt_customer_search.getText());
		model = (DefaultTableModel) views.customers_table.getModel();

		Object[] row = new Object[5];

		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getId();
			row[1] = list.get(i).getFull_name();
			row[2] = list.get(i).getAddress();
			row[3] = list.get(i).getTelephone();
			row[4] = list.get(i).getEmail();
			model.addRow(row);
		}
		views.customers_table.setModel(model);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == views.customers_table) {
			// TODO validar con NPE
			int row = views.customers_table.rowAtPoint(e.getPoint());
			views.txt_customer_id.setText(views.customers_table.getValueAt(row, 0).toString());
			views.txt_customer_fullname.setText(views.customers_table.getValueAt(row, 1).toString());
			views.txt_customer_address.setText(views.customers_table.getValueAt(row, 2).toString());
			views.txt_customer_telephone.setText(views.customers_table.getValueAt(row, 3).toString());
			views.txt_customer_email.setText(views.customers_table.getValueAt(row, 4).toString());
			// Deshabilitar botones
			views.btn_customer_register.setEnabled(false);
			views.txt_customer_id.setEditable(false);
		} else if (e.getSource() == views.jlabel_customers) {
			views.panel_tab_menu_options.setSelectedIndex(3);
			// Limpiar tabla
			cleanTable();
			// Limpiar campos
			cleanFields();
			// Listar clientes
			listAllCustomers();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == views.txt_customer_search) {
			// Limpiar tabla
			cleanTable();
			// Listar clientes
			listAllCustomers();
		}
	}

	public void cleanFields() {
		views.txt_customer_id.setText("");
		views.txt_customer_id.setEditable(true);
		views.txt_customer_fullname.setText("");
		views.txt_customer_address.setText("");
		views.txt_customer_telephone.setText("");
		views.txt_customer_email.setText("");
	}

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

	@Override
	public void keyPressed(KeyEvent e) {

	}

}
