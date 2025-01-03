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

import dao.SuppliersDao;
import models.Suppliers;
import utils.DynamicCombobox;
import views.base.AbstractSystemView;

public class SuppliersController implements ActionListener, MouseListener, KeyListener {

	private Suppliers supplier;
	private SuppliersDao supplierDao;
	private AbstractSystemView views;

	DefaultTableModel model = new DefaultTableModel();

	public SuppliersController(Suppliers supplier, SuppliersDao supplierDao, AbstractSystemView views) {
		this.supplier = supplier;
		this.supplierDao = supplierDao;
		this.views = views;
		// Botón de registrar proveedor
		this.views.btn_supplier_register.addActionListener(this);
		// Botón de modificar proveedor
		this.views.btn_supplier_update.addActionListener(this);
		// Botón de eliminar proveedor
		this.views.btn_supplier_delete.addActionListener(this);
		// Botón de cancelar
		this.views.btn_supplier_cancel.addActionListener(this);
		this.views.suppliers_table.addMouseListener(this);
		this.views.txt_suppliers_search.addKeyListener(this);
		this.views.jlabel_suppliers.addMouseListener(this);
		getSupplierName();
		listAllSuppliers();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == views.btn_supplier_register) {
			if (views.txt_supplier_name.getText().equals("") || views.txt_supplier_description.getText().equals("")
					|| views.txt_supplier_address.getText().equals("")
					|| views.txt_supplier_telephone.getText().equals("")
					|| views.txt_supplier_email.getText().equals("")
					|| views.cmb_supplier_city.getSelectedItem().toString().equals("")) {

				JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
			} else {
				// Realizar inserción
				supplier.setName(views.txt_supplier_name.getText().trim());
				supplier.setDescription(views.txt_supplier_description.getText().trim());
				supplier.setAddress(views.txt_supplier_address.getText().trim());
				supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
				supplier.setEmail(views.txt_supplier_email.getText().trim());
				supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());

				if (supplierDao.registerSupplierQuery(supplier)) {
					cleanTable();
					cleanFields();
					listAllSuppliers();
					JOptionPane.showMessageDialog(null, "Proveedor registrado con éxito");
				} else {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al proveedor");
				}
			}
		} else if (e.getSource() == views.btn_supplier_update) {
			if (views.txt_supplier_id.equals("")) {
				JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
			} else {
				if (views.txt_supplier_name.getText().equals("") || views.txt_supplier_address.getText().equals("")
						|| views.txt_supplier_telephone.getText().equals("")
						|| views.txt_supplier_email.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
				} else {
					supplier.setName(views.txt_supplier_name.getText().trim());
					supplier.setDescription(views.txt_supplier_description.getText().trim());
					supplier.setAddress(views.txt_supplier_address.getText().trim());
					supplier.setTelephone(views.txt_supplier_telephone.getText().trim());
					supplier.setEmail(views.txt_supplier_email.getText().trim());
					supplier.setCity(views.cmb_supplier_city.getSelectedItem().toString());
					supplier.setId(Integer.parseInt(views.txt_supplier_id.getText()));

					if (supplierDao.updateSupplierQuery(supplier)) {
						// Limpiar tabla
						cleanTable();
						// Limpiar campos
						cleanFields();
						// Listar proveedor
						listAllSuppliers();
						views.btn_supplier_register.setEnabled(true);
						JOptionPane.showMessageDialog(null, "Datos del proveedor modificados con éxito");
					} else {
						JOptionPane.showMessageDialog(null,
								"Ha ocurrido un error al modificar los datos del proveedor");
					}
				}
			}
		} else if (e.getSource() == views.btn_supplier_delete) {
			int row = views.suppliers_table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Debes seleccionar un proveedor para eliminar");
			} else {
				int id = Integer.parseInt(views.suppliers_table.getValueAt(row, 0).toString());
				int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar este proveedor?");
				if (question == 0 && supplierDao.deleteSupplierQuery(id) != false) {
					// Limpiar tabla
					cleanTable();
					// Limpiar campos
					cleanFields();
					// Listar proveedores
					listAllSuppliers();
					views.btn_supplier_register.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Proveedor eliminado con éxito");
				}
			}
		} else if (e.getSource() == views.btn_supplier_cancel) {
			cleanFields();
			views.btn_supplier_register.setEnabled(true);
		}
	}

	// Listar proveedores
	public void listAllSuppliers() {
		if (LoginController.getPermission()) {
			List<Suppliers> list = supplierDao.listSuppliersQuery(views.txt_suppliers_search.getText());
			model = (DefaultTableModel) views.suppliers_table.getModel();
			Object[] row = new Object[7];
			for (int i = 0; i < list.size(); i++) {
				row[0] = list.get(i).getId();
				row[1] = list.get(i).getName();
				row[2] = list.get(i).getDescription();
				row[3] = list.get(i).getAddress();
				row[4] = list.get(i).getTelephone();
				row[5] = list.get(i).getEmail();
				row[6] = list.get(i).getCity();
				model.addRow(row);
			}
			views.suppliers_table.setModel(model);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == views.suppliers_table) {
			// TODO validar NPE tostring
			int row = views.suppliers_table.rowAtPoint(e.getPoint());
			views.txt_supplier_id.setText(views.suppliers_table.getValueAt(row, 0).toString());
			views.txt_supplier_name.setText(views.suppliers_table.getValueAt(row, 1).toString());
			views.txt_supplier_description.setText(views.suppliers_table.getValueAt(row, 2).toString());
			views.txt_supplier_address.setText(views.suppliers_table.getValueAt(row, 3).toString());
			views.txt_supplier_telephone.setText(views.suppliers_table.getValueAt(row, 4).toString());
			views.txt_supplier_email.setText(views.suppliers_table.getValueAt(row, 5).toString());
			views.cmb_supplier_city.setSelectedItem(views.suppliers_table.getValueAt(row, 6).toString());
			// Deshabilitar botones
			views.btn_supplier_register.setEnabled(false);
			views.txt_supplier_id.setEditable(false);

		} else if (e.getSource() == views.jlabel_suppliers) {
			if (LoginController.getPermission()) {
				views.panel_tab_menu_options.setSelectedIndex(6);
				// Limpiar tabla
				cleanTable();
				// Limpiar campos
				cleanFields();
				// Listar proveedores
				listAllSuppliers();
			} else {
				views.panel_tab_menu_options.setEnabledAt(6, false);
				views.jlabel_suppliers.setEnabled(false);
				JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador para acceder a esta vista");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == views.txt_suppliers_search) {
			// Limpiar tabla
			cleanTable();
			// Listar proveedor
			listAllSuppliers();
		}
	}

	public void cleanTable() {
		for (int i = 0; i < model.getRowCount(); i++) {
			model.removeRow(i);
			i = i - 1;
		}
	}

	public void cleanFields() {
		views.txt_supplier_id.setText("");
		views.txt_supplier_id.setEditable(true);
		views.txt_supplier_name.setText("");
		views.txt_supplier_description.setText("");
		views.txt_supplier_address.setText("");
		views.txt_supplier_telephone.setText("");
		views.txt_supplier_email.setText("");
		views.cmb_supplier_city.setSelectedIndex(0);
	}

	// Método para mostrar el nombre del proveedor
	public void getSupplierName() {
		List<Suppliers> list = supplierDao.listSuppliersQuery(views.txt_suppliers_search.getText());
		for (int i = 0; i < list.size(); i++) {
			int id = list.get(i).getId();
			String name = list.get(i).getName();
			views.cmb_purchase_supplier.addItem(new DynamicCombobox(id, name));
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
