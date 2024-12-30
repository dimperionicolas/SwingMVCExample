package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import controllers.base.BaseController;
import dao.CustomersDao;
import exceptions.BusinessException;
import models.Customers;
import services.CustomerService;
import views.base.AbstractSystemView;

public class CustomersController extends BaseController {

	private final CustomersDao customerDao;
	private final CustomerService customerService;

	public CustomersController(AbstractSystemView views) {
		super(views);
		this.customerDao = new CustomersDao();
		customerService = new CustomerService(customerDao);
		listAllCustomers();
	}

	@Override
	protected void initializeListeners() {
		views.btn_customer_register.addActionListener(this);
		views.btn_customer_update.addActionListener(this);
		views.btn_customer_delete.addActionListener(this);
		views.btn_customer_cancel.addActionListener(this);
		views.txt_customer_search.addKeyListener(this);
		views.jlabel_customers.addMouseListener(this);
		views.customers_table.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == views.btn_customer_register) {
				handleRegisterCustomer();
			} else if (e.getSource() == views.btn_customer_update) {
				handleUpdateCustomer();
			} else if (e.getSource() == views.btn_customer_delete) {
				handleDeleteCustomer();
			} else if (e.getSource() == views.btn_customer_cancel) {
				handleCancel();
			}
		} catch (BusinessException ex) {
			switch (ex.getErrorCode()) {
			case DUPLICATE_ENTITY:
				showError("Cliente duplicado", ex.getMessage());
				break;
			case DATABASE_ERROR:
				showError("Error de base de datos", ex.getMessage());
				logError(ex);
				break;
			default:
				showError("Error", ex.getMessage());
			}
		}
	}

	private void handleCancel() {
		views.btn_customer_register.setEnabled(true);
		refreshView();
	}

	private void handleRegisterCustomer() throws BusinessException {
		if (!validateCustomerFields()) {
			return;
		}
		Customers customerToRegister = buildCustomerFromFields();
		customerService.registerCustomer(customerToRegister);
		refreshView();
		showSuccess("Cliente registrado con éxito");
	}

	private void handleUpdateCustomer() throws BusinessException {
		if (!validateSelectedCustomerById() || !validateCustomerFields()) {
			return;
		}
		Customers customerToUpdate = buildCustomerFromFields();
		customerToUpdate.setId(Integer.parseInt(views.txt_customer_id.getText().trim()));
		customerService.updateCustomer(customerToUpdate);
		refreshView();
		views.btn_customer_register.setEnabled(true);
		showSuccess("Datos del cliente modificados con éxito");
	}

	private void handleDeleteCustomer() throws BusinessException {
		int row = views.customers_table.getSelectedRow();
		if (row == -1) {
			throw new BusinessException("Debes seleccionar un cliente para eliminar");
		}
		int id = Integer.parseInt(views.customers_table.getValueAt(row, 0).toString());
		if (confirmAction("¿En realidad quieres eliminar a este cliente?")) {
			customerService.deleteCustomer(id);
			refreshView();
			showSuccess("Cliente eliminado con éxito");
		}
	}

	private boolean validateSelectedCustomerById() {
		if (views.txt_customer_id.getText().equals("")) {
			showValidationError("Seleccione un cliente de la tabla para actualizar");
			return false;
		}
		return true;
	}

	private Customers buildCustomerFromFields() {
		Customers customer = new Customers();
		customer.setFull_name(views.txt_customer_fullname.getText().trim());
		customer.setAddress(views.txt_customer_address.getText().trim());
		customer.setTelephone(views.txt_customer_telephone.getText().trim());
		customer.setEmail(views.txt_customer_email.getText().trim());
		return customer;
	}

	private boolean validateCustomerFields() {
		return validateRequiredFields(views.txt_customer_fullname.getText(), views.txt_customer_address.getText(),
				views.txt_customer_telephone.getText(), views.txt_customer_email.getText());
	}

	private boolean validateCustomerTableRowCells(int row) {
		return validateRequiredRowCells(views.customers_table.getValueAt(row, 0).toString(),
				views.customers_table.getValueAt(row, 1).toString(),
				views.customers_table.getValueAt(row, 2).toString(),
				views.customers_table.getValueAt(row, 3).toString(),
				views.customers_table.getValueAt(row, 4).toString());

	}

	private void refreshView() {
		cleanTable();
		cleanFields();
		listAllCustomers();
	}

	private void listAllCustomers() {
		try {
			List<Customers> list = getListForTable();
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
		} catch (BusinessException ex) {
			showError("Error", ex.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	private List<Customers> getListForTable() throws BusinessException {
		List<?> listAll = listAllElements(customerService, views.txt_customer_search.getText());
		if (listAll.isEmpty() || listAll.get(0) instanceof Customers) {
			return (List<Customers>) listAll;
		}
		throw new BusinessException("Error al obtener los elementos");
	}

	@Override
	protected void cleanFields() {
		views.txt_customer_id.setText("");
		views.txt_customer_id.setEditable(true);
		views.txt_customer_fullname.setText("");
		views.txt_customer_address.setText("");
		views.txt_customer_telephone.setText("");
		views.txt_customer_email.setText("");
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == views.customers_table) {
			int row = views.customers_table.rowAtPoint(e.getPoint());
			if (!validateCustomerTableRowCells(row)) {
				return;
			}
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
			refreshView();
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getSource() == views.txt_customer_search) {
			cleanTable();
			listAllCustomers();
		}
	}

}
