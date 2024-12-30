package controllers;

import static dao.EmployeesDao.id_user;
import static dao.EmployeesDao.rol_user;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controllers.base.BaseController;
import dao.ProductsDao;
import dao.PurchasesDao;
import exceptions.BusinessException;
import exceptions.ValidationException;
import models.Products;
import models.Purchases;
import services.PurchaseService;
import utils.DynamicCombobox;
import views.base.AbstractSystemView;

public class PurchasesController extends BaseController {

	private final PurchasesDao purchaseDao;
	private final PurchaseService purchaseService;
	DefaultTableModel temp;
	ProductsDao productDao; // TODO eliminar esto

	public PurchasesController(AbstractSystemView views) {
		super(views);
		this.purchaseDao = new PurchasesDao();
		this.productDao = new ProductsDao();

		this.purchaseService = new PurchaseService(purchaseDao);
		listAllPurchasesOnReportTable();
	}

	@Override
	protected void initializeListeners() {
		views.btn_purchase_add_to_buy.addActionListener(this);
		views.btn_purchase_confirm.addActionListener(this);
		views.btn_purchase_remove.addActionListener(this);
		views.txt_purchase_unit_code.addKeyListener(this);
		views.txt_purchase_unit_price.addKeyListener(this);
		views.btn_purchase_new.addActionListener(this);
		views.jlabel_purchases.addMouseListener(this);
		views.jlabel_reports.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == views.btn_purchase_add_to_buy) {
				addProductsToBuy();
			} else if (e.getSource() == views.btn_purchase_confirm) {
				confirmPurchase();
			} else if (e.getSource() == views.btn_purchase_remove) {
				removeElementFromList();
			} else if (e.getSource() == views.btn_purchase_new) {
				startNewList();
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

	private void startNewList() {
		refreshView();
		unlockSupplier();
		purchaseService.clearSupplier();
	}

	private void removeElementFromList() {
		model = (DefaultTableModel) views.purchase_table.getModel();
		model.removeRow(views.purchase_table.getSelectedRow());
		calculatePurchase();
		views.txt_purchase_unit_code.requestFocus();
	}

	private void addProductsToBuy() throws ValidationException {
		DynamicCombobox supplier_cmb = (DynamicCombobox) views.cmb_purchase_supplier.getSelectedItem();
		purchaseService.isValidSupplier(supplier_cmb.getId());
		if (!validatePurchaseFields()) {
			return;
		}
		verifyNoDuplicateProduct();
		int amount = purchaseService.checkAndGetAmount(views.txt_purchase_amount.getText());
		int purchase_product_id = purchaseService.checkAndGetId(views.txt_purchase_product_id.getText());
		double price = purchaseService.checkAndGetPrices(views.txt_purchase_unit_price.getText());
		String product_name = views.txt_purchase_product_name.getText();
		String supplier_name = supplier_cmb.getName();
		addTemporalProductToTable(amount, purchase_product_id, product_name, price, supplier_name);
		cleanFields();
		lockSupplier();
		calculatePurchase();
		views.txt_purchase_unit_code.requestFocus();
	}

	private void verifyNoDuplicateProduct() throws ValidationException {
		for (int i = 0; i < views.purchase_table.getRowCount(); i++) {
			if (views.purchase_table.getValueAt(i, 1).equals(views.txt_purchase_product_name.getText())) {
				throw new ValidationException("El producto ya esta registrado en la tabla de compras");
			}
		}
	}

	private void confirmPurchase() throws BusinessException {
		purchaseService.registerPurchase(id_user, views.txt_purchase_total.getText());
		int purchase_id = purchaseService.purchaseLastId();
		for (int i = 0; i < views.purchase_table.getRowCount(); i++) {
			registerPurchaseDetail(purchase_id, i);
			updateProductStock(i);
		}
		refreshView();
		showSuccess("Compra generada con éxito");
		print(purchase_id);
	}

	private void updateProductStock(int i) {
		int product_id = Integer.parseInt(views.purchase_table.getValueAt(i, 0).toString());
		// TODO obtener el ProductService desde alguna factory tal vez.
		// No instanciar una nueva
		Products product = productDao.searchId(product_id);
		int purchase_amount = Integer.parseInt(views.purchase_table.getValueAt(i, 2).toString());
		int amount = product.getProduct_quantity() + purchase_amount;
		productDao.updateStockQuery(amount, product_id);
	}

	private void registerPurchaseDetail(int purchase_id, int i) throws BusinessException {
		int product_id = Integer.parseInt(views.purchase_table.getValueAt(i, 0).toString());
		int purchase_amount = Integer.parseInt(views.purchase_table.getValueAt(i, 2).toString());
		double purchase_price = Double.parseDouble(views.purchase_table.getValueAt(i, 3).toString());
		double purchase_subtotal = purchase_price * purchase_amount;
		purchaseService.registerPurchaseDetailQuery(purchase_id, purchase_price, purchase_amount, purchase_subtotal,
				product_id);
	}

	// Método para listar reporte de las compras realizadas
	public void listAllPurchasesOnReportTable() {
		try {
			List<Purchases> list = getListForTable();
			model = (DefaultTableModel) views.purchase_report_table.getModel();
			Object[] row = new Object[4];
			for (int i = 0; i < list.size(); i++) {
				row[0] = list.get(i).getId();
				row[1] = list.get(i).getSupplier_name_product();
				row[2] = list.get(i).getTotal();
				row[3] = list.get(i).getCreated();
				model.addRow(row);
			}
			views.purchase_report_table.setModel(model);
		} catch (BusinessException ex) {
			showError("Error", ex.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	private List<Purchases> getListForTable() throws BusinessException {
		List<?> listAll = listAllElements(purchaseService, "");
		if (listAll.isEmpty() || listAll.get(0) instanceof Purchases) {
			return (List<Purchases>) listAll;
		}
		throw new BusinessException("Error al obtener los elementos");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		if (source == views.jlabel_purchases) {
			if (rol_user.equals("Administrador".toUpperCase())) {// TODO validacion de permisos
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
			listAllPurchasesOnReportTable();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == views.txt_purchase_unit_code) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (views.txt_purchase_unit_code.getText().equals("")) {
					showValidationError("Ingresa el código del producto a comprar");
				} else {
					int id = Integer.parseInt(views.txt_purchase_unit_code.getText());
					// TODO obtener productService. Se chequea luego pero no agregar si no existe
					Products product = productDao.searchCode(id);
					views.txt_purchase_product_name.setText(product.getName());
					views.txt_purchase_product_id.setText("" + product.getId());
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

	// Calcular total a pagar
	public void calculatePurchase() { // TODO el total lo podria tener el servicio
		double total = 0.00;
		int numRow = views.purchase_table.getRowCount();
		for (int i = 0; i < numRow; i++) {
			total = total + Double.parseDouble(String.valueOf(views.purchase_table.getValueAt(i, 4)));
		}
		views.txt_purchase_total.setText("" + total);
	}

	private boolean validatePurchaseFields() {
		return validateRequiredFields(views.txt_purchase_amount.getText(), views.txt_purchase_product_name.getText(),
				views.txt_purchase_unit_price.getText(), views.txt_purchase_product_id.getText());
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
	protected void cleanFields() {
		views.txt_purchase_product_name.setText("");
		views.txt_purchase_unit_price.setText("");
		views.txt_purchase_amount.setText("");
		views.txt_purchase_unit_code.setText("");
		views.txt_purchase_subtotal.setText("");
		views.txt_purchase_product_id.setText("");
		views.txt_purchase_total.setText("");
	}

	private void refreshView() {
		cleanTableTemp();
		cleanFields();
	}

	private void lockSupplier() {
		views.cmb_purchase_supplier.setEditable(false);
	}

	private void unlockSupplier() {
		views.cmb_purchase_supplier.setEditable(true);
	}

	private void addTemporalProductToTable(int amount, int purchase_id, String product_name, double price,
			String supplier_name) {
		temp = (DefaultTableModel) views.purchase_table.getModel();
		// TODO temp es necesario? Podria ser model.
		// item += 1; // TODO este item para que lo quiero?
		Object[] obj = new Object[6];
		obj[0] = purchase_id;
		obj[1] = product_name;
		obj[2] = amount;
		obj[3] = price;
		double total = amount * price;
		obj[4] = total;
		obj[5] = supplier_name;
		temp.addRow(obj);
		views.purchase_table.setModel(temp);
	}

	private void print(int purchase_id) {
		// Print print = new Print(purchase_id);
		// print.setVisible(true);
	}

}
