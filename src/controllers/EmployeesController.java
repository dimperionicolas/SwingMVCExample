package controllers;

import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user; //Ver si esto en realidad no lo debo cambiar

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import models.Employees;
import models.EmployeesDao;
import views.AbstractSystemView;

public class EmployeesController implements ActionListener, MouseListener, KeyListener {
	private Employees employee;
	private EmployeesDao employeeDao;
	private AbstractSystemView views;

	DefaultTableModel model = new DefaultTableModel();

	public EmployeesController(Employees employee, EmployeesDao employeeDao, AbstractSystemView views) {
		this.employee = employee;
		this.employeeDao = employeeDao;
		this.views = views;
		// Botón de registrar empleado
		this.views.btn_employee_register.addActionListener(this);
		// Botón de modificar empleado
		this.views.btn_employee_update.addActionListener(this);
		// Botón de eliminar empleado
		this.views.btn_employee_delete.addActionListener(this);
		// Botón de cancelar
		this.views.btn_employee_cancel.addActionListener(this);
		// Botón de cambiar contraseña en settings
		this.views.btn_profile_modify_data.addActionListener(this);
		// Colocar label panel lateral en escucha (opcion del menu)
		this.views.jlabel_employees.addMouseListener(this);
		//
		this.views.employees_table.addMouseListener(this);
		this.views.txt_employee_search.addKeyListener(this);
		listAllEmployees();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO agregar metodos para separar cada evento.
		if (e.getSource() == views.btn_employee_register) {
			// Verificar si los campos estan vacios
			if (views.txt_employee_id.getText().equals("") || views.txt_employee_fullname.getText().equals("")
					|| views.txt_employee_username.getText().equals("")
					|| views.txt_employee_address.getText().equals("")
					|| views.txt_employee_telephone.getText().equals("")
					|| views.txt_employee_email.getText().equals("")
					|| views.cmb_employee_rol.getSelectedItem().toString().equals("")
					|| String.valueOf(views.txt_employee_password.getPassword()).equals("")) {

				JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
			} else {
				// Realizar la inserción
				setEmployeeFromTable();
				if (employeeDao.registerEmployeeQuery(employee)) {
					cleanTable();
					cleanFields();
					listAllEmployees();
					JOptionPane.showMessageDialog(null, "Empleado registrado con éxito");
				} else {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error al registrar al empleado");
				}
			}
		} else if (e.getSource() == views.btn_employee_update) {
			if (views.txt_employee_id.equals("")) {
				JOptionPane.showMessageDialog(null, "Selecciona una fila para continuar");
			} else {
				// Verificar si los campos estan vacios
				if (views.txt_employee_id.getText().equals("") || views.txt_employee_fullname.getText().equals("")
						|| views.cmb_employee_rol.getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
				} else {
					setEmployeeFromTable();
					if (employeeDao.updateEmployeeQuery(employee)) {
						cleanTable();
						cleanFields();
						listAllEmployees();
						views.btn_employee_register.setEnabled(true);
						JOptionPane.showMessageDialog(null, "Datos del empleado modificados con éxito");
					} else {
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar al empleado");
					}

				}
			}
		} else if (e.getSource() == views.btn_employee_delete) {
			int row = views.employees_table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Debes seleccionar un empleado para eliminar");
			} else if (views.employees_table.getValueAt(row, 0).equals(id_user)) {
				JOptionPane.showMessageDialog(null, "No puede eliminar al usuario autenticado");
			} else {
				int id = Integer.parseInt(views.employees_table.getValueAt(row, 0).toString());
				int question = JOptionPane.showConfirmDialog(null, "¿En realidad quieres eliminar a este empleado?");

				if (question == 0 && employeeDao.deleteEmployeeQuery(id) != false) {
					cleanTable();
					cleanFields();
					views.btn_employee_register.setEnabled(true);
					views.txt_employee_password.setEnabled(true);
					listAllEmployees();
					JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito");
				}
			}
		} else if (e.getSource() == views.btn_employee_cancel) {
			cleanFields();
			views.btn_employee_register.setEnabled(true);
			views.txt_employee_password.setEnabled(true);
			views.txt_employee_id.setEnabled(true);
		} else if (e.getSource() == views.btn_profile_modify_data) {
			// Recolectar información de las cajas password
			String password = String.valueOf(views.txt_profile_password_modify.getPassword());

			String confirm_password = String.valueOf(views.txt_profile_modify_confirm.getPassword());
			// Verificar si las cajas de texto estan vacias
			if (!password.equals("") && !confirm_password.equals("")) {
				// Verificar que las contraseñas sean iguales
				if (password.equals(confirm_password)) {
					employee.setPassword(String.valueOf(views.txt_profile_password_modify.getPassword()));
					if (employeeDao.updateEmployeePasswordQuery(employee) != false) {
						JOptionPane.showMessageDialog(null, "Contraseña modificada con éxito");
					} else {
						JOptionPane.showMessageDialog(null, "Ha ocurrido un error al modificar la contraseña");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
			}
		}
	}

	private void setEmployeeFromTable() {
		// TODO no se encuentra validado el tipo de dato
		employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
		employee.setFull_name(views.txt_employee_fullname.getText().trim());
		employee.setUsername(views.txt_employee_username.getText().trim());
		employee.setAddress(views.txt_employee_address.getText().trim());
		employee.setTelephone(views.txt_employee_telephone.getText().trim());
		employee.setEmail(views.txt_employee_email.getText().trim());
		employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
		employee.setRol(views.cmb_employee_rol.getSelectedItem().toString());
	}

	// Listar todos los empleados
	public void listAllEmployees() {
		// TODO cambiar esto
		if (rol_user.equals("Administrador".toUpperCase())) {
			List<Employees> list = employeeDao.listEmployeesQuery(views.txt_employee_search.getText());
			model = (DefaultTableModel) views.employees_table.getModel();
			Object[] row = new Object[7];
			for (int i = 0; i < list.size(); i++) {
				row[0] = list.get(i).getId();
				row[1] = list.get(i).getFull_name();
				row[2] = list.get(i).getUsername();
				row[3] = list.get(i).getAddress();
				row[4] = list.get(i).getTelephone();
				row[5] = list.get(i).getEmail();
				row[6] = list.get(i).getRol();
				model.addRow(row);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Evento al hacer click sobre un elemento de la tabla
		if (e.getSource() == views.employees_table) {
			// TODO validar todo por NPE, row = 0 deberia existir? no son los ittulos?
			// TODO validar estoy y dar un mensajes de que se deberia cargar cierto valor si
			// no se encuentra
			int row = views.employees_table.rowAtPoint(e.getPoint());
			// TODO npe .tostring. Si esta vacio deberia ser que lo arregle o lo elimine
			views.txt_employee_id.setText(views.employees_table.getValueAt(row, 0).toString());
			views.txt_employee_fullname.setText(views.employees_table.getValueAt(row, 1).toString());
			views.txt_employee_username.setText(views.employees_table.getValueAt(row, 2).toString());
			views.cmb_employee_rol.setSelectedItem(views.employees_table.getValueAt(row, 6).toString());
			//
			String address = !(views.employees_table.getValueAt(row, 3) == null)
					? views.employees_table.getValueAt(row, 3).toString()
					: "";
			views.txt_employee_address.setText(address);
			String telephone = !(views.employees_table.getValueAt(row, 4) == null)
					? views.employees_table.getValueAt(row, 4).toString()
					: "";
			views.txt_employee_telephone.setText(telephone);
			String email = !(views.employees_table.getValueAt(row, 5) == null)
					? views.employees_table.getValueAt(row, 5).toString()
					: "";
			views.txt_employee_email.setText(email);

			// Deshabilitar
			views.txt_employee_id.setEditable(false);
			views.txt_employee_password.setEnabled(false);
			views.btn_employee_register.setEnabled(false);

		} else if (e.getSource() == views.jlabel_employees) {
			if (rol_user.equals("Administrador".toUpperCase())) {
				views.panel_tab_menu_options.setSelectedIndex(4);
				// Limpiar tabla
				cleanTable();
				// Limpiar campos
				cleanFields();
				// Listar empleados
				listAllEmployees();
			} else {
				views.panel_tab_menu_options.setEnabledAt(4, false);
				views.jlabel_employees.setEnabled(false);
				// TODO no me deja por el panel del menu pero si por medio del tab
				JOptionPane.showMessageDialog(null, "No tienes privilegios de administrador para acceder a esta vista");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getSource() == views.txt_employee_search) {
			// TODO buscar por otros valores de columnas
			cleanTable();
			cleanFields(); // TODO deberia limpiar los fields?
			listAllEmployees();
		}
	}

	// Limpiar campos
	public void cleanFields() {
		views.txt_employee_id.setText("");
		views.txt_employee_id.setEditable(true);
		views.txt_employee_fullname.setText("");
		views.txt_employee_username.setText("");
		views.txt_employee_address.setText("");
		views.txt_employee_telephone.setText("");
		views.txt_employee_email.setText("");
		views.txt_employee_password.setText("");
		views.cmb_employee_rol.setSelectedIndex(0);
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
