package controllers;

import static dao.EmployeesDao.rol_user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import dao.CategoriesDao;
import models.Categories;
import utils.DynamicCombobox;
import views.base.AbstractSystemView;

public class CategoriesController implements ActionListener, MouseListener, KeyListener {

	private Categories category;
	private CategoriesDao categoryDao;
	private AbstractSystemView views;
	String rol = rol_user;
	DefaultTableModel model = new DefaultTableModel();

	public CategoriesController(Categories category, CategoriesDao categoryDao, AbstractSystemView views) {
		this.category = category;
		this.categoryDao = categoryDao;
		this.views = views;
		// Botón de registrar categoría
		this.views.btn_category_register.addActionListener(this);
		// Botón de modificar categoría
		this.views.btn_category_update.addActionListener(this);
		// Botón de eliminar categoría
		this.views.btn_category_delete.addActionListener(this);
		// Botón de cancelar
		this.views.btn_category_cancel.addActionListener(this);
		this.views.categories_table.addMouseListener(this);
		this.views.txt_category_search.addKeyListener(this);
		this.views.jlabel_categories.addMouseListener(this);
		// Carga al iniciar las categorias en el combobox de productos
		getCategoryName();
		AutoCompleteDecorator.decorate(views.cmb_product_category);
		listAllCategories();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == views.btn_category_register) {
			if (views.txt_category_name.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
			} else {
				category.setName(views.txt_category_name.getText().trim());

				if (categoryDao.registerCategoryQuery(category)) {
					cleanTable();
					cleanFields();
					listAllCategories();
					JOptionPane.showMessageDialog(null, "Categoría registrada con éxito");
				} else {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar la categoría");
				}
			}
		} else if (e.getSource() == views.btn_category_update) {
			if (views.txt_category_id.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
			} else {
				if (views.txt_category_id.getText().equals("") || views.txt_category_name.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
				} else {
					category.setId(Integer.parseInt(views.txt_category_id.getText()));
					category.setName(views.txt_category_name.getText().trim());

					if (categoryDao.updateCategoryQuery(category)) {
						cleanTable();
						cleanFields();
						views.btn_category_register.setEnabled(true);
						listAllCategories();
					}
				}
			}
		} else if (e.getSource() == views.btn_category_delete) {
			int row = views.categories_table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Debe seleccionar una categoría para eliminar");
			} else {
				int id = Integer.parseInt(views.categories_table.getValueAt(row, 0).toString());
				int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar esta categoría?");

				if (question == 0 && categoryDao.deleteCategoryQuery(id) != false) {
					cleanTable();
					cleanFields();
					views.btn_category_register.setEnabled(true);
					listAllCategories();
					JOptionPane.showMessageDialog(null, "Categoría eliminada con éxito");
				}
			}
		} else if (e.getSource() == views.btn_category_cancel) {
			cleanFields();
			views.btn_category_register.setEnabled(true);
		}
	}

	// Listar categorías
	public void listAllCategories() {
		if (rol.equals("Administrador".toUpperCase())) {
			List<Categories> list = categoryDao.listCategoriesQuery(views.txt_category_search.getText());
			model = (DefaultTableModel) views.categories_table.getModel();
			Object[] row = new Object[2];
			for (int i = 0; i < list.size(); i++) {
				row[0] = list.get(i).getId();
				row[1] = list.get(i).getName();
				model.addRow(row);
			}
			views.categories_table.setModel(model);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == views.categories_table) {
			int row = views.categories_table.rowAtPoint(e.getPoint());
			views.txt_category_id.setText(views.categories_table.getValueAt(row, 0).toString());
			views.txt_category_name.setText(views.categories_table.getValueAt(row, 1).toString());
			views.btn_category_register.setEnabled(false);
		} else if (e.getSource() == views.jlabel_categories) {
			if (rol.equals("Administrador".toUpperCase())) {
				views.panel_tab_menu_options.setSelectedIndex(5);
				cleanTable();
				cleanFields();
				listAllCategories();
			} else {
				views.panel_tab_menu_options.setEnabledAt(5, false);
				views.jlabel_categories.setEnabled(false);
				JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador para acceder a esta vista");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == views.txt_category_search) {
			// Limpiar tabla
			cleanTable();
			// Listar categorías
			listAllCategories();

		}
	}

	public void cleanTable() {
		for (int i = 0; i < model.getRowCount(); i++) {
			model.removeRow(i);
			i = i - 1;
		}
	}

	public void cleanFields() {
		views.txt_category_id.setText("");
		views.txt_category_name.setText("");
	}

	// Método para mostrar el nombre de las categorías en la pestaña producto
	public void getCategoryName() {
		List<Categories> list = categoryDao.listCategoriesQuery(views.txt_category_search.getText());
		for (int i = 0; i < list.size(); i++) {
			int id = list.get(i).getId();
			String name = list.get(i).getName();
			views.cmb_product_category.addItem(new DynamicCombobox(id, name));
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
