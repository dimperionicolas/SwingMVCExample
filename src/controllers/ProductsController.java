package controllers;

import static models.EmployeesDao.rol_user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import models.DynamicCombobox;
import models.Products;
import models.ProductsDao;
import views.AbstractSystemView;

public class ProductsController implements ActionListener, MouseListener, KeyListener, ChangeListener {

	private Products product;
	private ProductsDao productDao;
	private AbstractSystemView views;
	String rol = rol_user;
	DefaultTableModel model = new DefaultTableModel();

	public ProductsController(Products product, ProductsDao productDao, AbstractSystemView views) {
		this.product = product;
		this.productDao = productDao;
		this.views = views;
		// Botón de registrar producto
		this.views.btn_product_register.addActionListener(this);
		// Botón de modificar producto
		this.views.btn_product_update.addActionListener(this);
		// Botón de eliminar producto
		this.views.btn_product_delete.addActionListener(this);
		// Botón de cancelar
		this.views.btn_product_cancel.addActionListener(this);

		this.views.product_table.addMouseListener(this);
		// Tabla para ser actualizada cuando se hace foco en la pestaña
		this.views.panel_tab_menu_options.addChangeListener(this);
		// TextBox buscar
		this.views.txt_product_search.addKeyListener(this);
		// Etiqueta menu lateral
		this.views.jlabel_products.addMouseListener(this);
		listAllProducts();
		disablesButtonsForNonAdmin();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == views.btn_product_register) {
			if (views.txt_product_code.getText().equals("") || views.txt_product_name.getText().equals("")
					|| views.txt_product_description.getText().equals("")
					|| views.txt_product_unit_price.getText().equals("")
					|| views.cmb_product_category.getSelectedItem().toString().equals("")) {
				JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
			} else {
				product.setCode(Integer.parseInt(views.txt_product_code.getText()));
				product.setName(views.txt_product_name.getText().trim());
				product.setDescription(views.txt_product_description.getText().trim());
				product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
				DynamicCombobox category_id = (DynamicCombobox) views.cmb_product_category.getSelectedItem();
				product.setCategory_id(category_id.getId());

				if (productDao.registerProductQuery(product)) {
					cleanFields();
					refreshTable();
					JOptionPane.showMessageDialog(null, "Producto registrado con éxito");
				} else {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al producto");
				}
			}
		} else if (e.getSource() == views.btn_product_update) {
			if (views.txt_product_code.getText().equals("") || views.txt_product_name.getText().equals("")
					|| views.txt_product_description.getText().equals("")
					|| views.txt_product_unit_price.getText().equals("")
					|| views.cmb_product_category.getSelectedItem().toString().equals("")) {
				JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
			} else {
				product.setCode(Integer.parseInt(views.txt_product_code.getText()));
				product.setName(views.txt_product_name.getText().trim());
				product.setDescription(views.txt_product_description.getText().trim());
				product.setUnit_price(Double.parseDouble(views.txt_product_unit_price.getText()));
				// Obtener el id de la categoría
				DynamicCombobox category_id = (DynamicCombobox) views.cmb_product_category.getSelectedItem();
				product.setCategory_id(category_id.getId());
				// Pasar id al método
				product.setId(Integer.parseInt(views.txt_product_id.getText()));

				if (productDao.updateProductQuery(product)) {
					cleanFields();
					refreshTable();
					views.btn_product_register.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Datos del producto modificados con éxito");
				} else {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar los datos del producto");
				}
			}
		} else if (e.getSource() == views.btn_product_delete) {
			int row = views.product_table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Debes seleccionar un producto para eliminar");
			} else {
				int id = Integer.parseInt(views.product_table.getValueAt(row, 0).toString());
				int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar a este producto?");

				if (question == 0 && productDao.deleteProductQuery(id) != false) {
					cleanFields();
					refreshTable();
					views.btn_product_register.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Producto eliminado con éxito");
				}
			}
		} else if (e.getSource() == views.btn_product_cancel) {
			cleanFields();
			views.btn_product_register.setEnabled(true);
		}
	}

	private void refreshTable() {
		cleanTable();
		listAllProducts();
	}

	// Listar productos
	public void listAllProducts() {
		// if (rol.equals("Administrador".toUpperCase()) ||
		// rol.equals("Auxiliar".toUpperCase())) {
		List<Products> list = productDao.listProductsQuery(views.txt_product_search.getText());
		model = (DefaultTableModel) views.product_table.getModel();
		Object[] row = new Object[7];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getId();
			row[1] = list.get(i).getCode();
			row[2] = list.get(i).getName();
			row[3] = list.get(i).getDescription();
			row[4] = list.get(i).getUnit_price();
			row[5] = list.get(i).getProduct_quantity();
			row[6] = list.get(i).getCategory_name();
			model.addRow(row);
		}
		views.product_table.setModel(model);
		// } //TODO eliminar si no es necesario
	}

	private void disablesButtonsForNonAdmin() {
		if (rol.equals("Auxiliar".toUpperCase())) { // TODO darle otra visual
			views.btn_product_register.setEnabled(false);
			views.btn_product_register.setForeground(Color.GREEN);
			views.btn_product_update.setEnabled(false);
			views.btn_product_update.setForeground(Color.GREEN);
			views.btn_product_delete.setEnabled(false);
			views.btn_product_delete.setForeground(Color.GREEN);
			views.btn_product_cancel.setEnabled(false);
			views.btn_product_cancel.setForeground(Color.GREEN);
			views.txt_product_code.setEnabled(false);
			views.txt_product_code.setForeground(Color.GREEN);
			views.txt_product_description.setEnabled(false);
			views.txt_product_description.setForeground(Color.GREEN);
			views.txt_product_id.setEditable(false);
			views.txt_product_id.setForeground(Color.GREEN);
			views.txt_product_name.setEditable(false);
			views.txt_product_name.setForeground(Color.GREEN);
			views.txt_product_unit_price.setEditable(false);
			views.txt_product_unit_price.setForeground(Color.GREEN);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == views.product_table) {
			if (!isPossibleToLInkTableAndTextbox(e)) {
				JOptionPane.showMessageDialog(null, "Error al obtener id de la fila. ");
				// TODO mensajes de que no se pudo encontrar el producto.
				return;
			}
			// Deshabilita el boton de registar
			views.btn_product_register.setEnabled(false);
		} else if (e.getSource() == views.jlabel_products) {
			views.panel_tab_menu_options.setSelectedIndex(0);
			cleanFields();
			refreshTable();
		}
	}

	private boolean isPossibleToLInkTableAndTextbox(MouseEvent e) {
		int row = views.product_table.rowAtPoint(e.getPoint());
		Object valueAt = views.product_table.getValueAt(row, 0);
		if (valueAt == null) {
			return false;
		}
		String text = valueAt.toString();
		views.txt_product_id.setText(text);
		product = productDao.searchProduct(Integer.parseInt(text));
		if (product == null) {
			return false;
		}
		views.txt_product_code.setText("" + product.getCode());
		views.txt_product_name.setText(product.getName());
		views.txt_product_description.setText(product.getDescription());
		views.txt_product_unit_price.setText("" + product.getUnit_price());
		views.cmb_product_category
				.setSelectedItem(new DynamicCombobox(product.getCategory_id(), product.getCategory_name()));
		return true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == views.txt_product_search) {
			refreshTable();
		}
	}

	public void cleanTable() {
		for (int i = 0; i < model.getRowCount(); i++) {
			model.removeRow(i);
			i = i - 1;
		}
	}

	public void cleanFields() {
		views.txt_product_id.setText("");
		views.txt_product_code.setText("");
		views.txt_product_name.setText("");
		views.txt_product_description.setText("");
		views.txt_product_unit_price.setText("");
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == views.panel_tab_menu_options && views.panel_tab_menu_options.getSelectedIndex() == 0) {
			refreshTable();
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
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
