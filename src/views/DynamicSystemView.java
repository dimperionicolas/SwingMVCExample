package views;

import static dao.EmployeesDao.full_name_user;
import static dao.EmployeesDao.rol_user;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import controllers.CategoriesController;
import controllers.CustomersController;
import controllers.EmployeesController;
import controllers.ProductsController;
import controllers.PurchasesController;
import controllers.SalesController;
import controllers.SettingsController;
import controllers.SuppliersController;
import dao.CategoriesDao;
import dao.CustomersDao;
import dao.EmployeesDao;
import dao.ProductsDao;
import dao.PurchasesDao;
import dao.SalesDao;
import dao.SuppliersDao;
import models.Categories;
import models.Customers;
import models.Employees;
import models.Products;
import models.Purchases;
import models.Sales;
import models.Suppliers;
import utils.UIComponentFactory;
import utils.UIComponentFactory.ButtonPosition;
import utils.UIComponentFactory.ComponentPosition;
import views.base.AbstractSystemView;

public class DynamicSystemView extends AbstractSystemView {

	private static final Color tabPanelBackgourndColor = new Color(69, 193, 241);
//	private static final Color UIComponentFactory.getMenuPanelBackground()=new Color(18,45,61);
	private static final Color regularColor = new Color(255, 255, 255); // white

	private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 25);
	private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 18);
	private static final Font buttonFont = new Font("Times New Roman", Font.BOLD, 18);

	private static final int RIGHT = SwingConstants.RIGHT;
	private static final int CENTER = SwingConstants.CENTER;
	private static final int LEFT = SwingConstants.LEFT;

	public enum compPos {
		// Cada enum esta formado por dos componentes. textfieldBounds y labelBounds
		uno(new ComponentBounds(160, 27, 180, 20), new ComponentBounds(10, 20, 140, 35)),
		dos(new ComponentBounds(160, 77, 180, 20), new ComponentBounds(10, 70, 140, 35)),
		tres(new ComponentBounds(160, 127, 180, 20), new ComponentBounds(10, 120, 140, 35)),
		cuatro(new ComponentBounds(160, 177, 180, 20), new ComponentBounds(10, 170, 140, 35)),
		cinco(new ComponentBounds(160, 227, 180, 20), new ComponentBounds(10, 220, 140, 35)),
		seis(new ComponentBounds(530, 27, 180, 20), new ComponentBounds(380, 20, 140, 35)),
		siete(new ComponentBounds(530, 77, 180, 20), new ComponentBounds(380, 70, 140, 35)),
		ocho(new ComponentBounds(530, 127, 180, 20), new ComponentBounds(380, 120, 140, 35)),
		nueve(new ComponentBounds(530, 177, 180, 20), new ComponentBounds(380, 170, 140, 35)),
		diez(new ComponentBounds(530, 227, 180, 20), new ComponentBounds(380, 220, 140, 35));

		private final ComponentBounds textFieldBounds;
		private final ComponentBounds labelBounds;

		// Clase auxiliar para almacenar las dimensiones
		private static class ComponentBounds {
			final int x, y, width, height;

			ComponentBounds(int x, int y, int width, int height) {
				this.x = x;
				this.y = y;
				this.width = width;
				this.height = height;
			}
		}

		compPos(ComponentBounds textFieldBounds, ComponentBounds labelBounds) {
			this.textFieldBounds = textFieldBounds;
			this.labelBounds = labelBounds;
		}

		public void applyTextFieldBounds(JTextField textField) {
			textField.setBounds(textFieldBounds.x, textFieldBounds.y, textFieldBounds.width, textFieldBounds.height);
		}

		public void applyLabelBounds(JLabel label) {
			label.setBounds(labelBounds.x, labelBounds.y, labelBounds.width, labelBounds.height);
		}
	}

	public enum bPos {
		FIRSTPOSITION, SECONDPOSITION, THIRDPOSITION, FOURTHPOSITION;
	}

//	private JFrame frame;
//	public JPanel jpanel_products;
//	public JLabel jlabel_products;
//	public JPanel jpanel_purchases;
//	public JLabel jlabel_purchases;
//	public JPanel jpanel_sales;
//	public JLabel jlabel_sales;
//	public JPanel jpanel_customers;
//	public JLabel jlabel_customers;
//	public JPanel jpanel_employees;
//	public JLabel jlabel_employees;
//	public JPanel jpanel_suppliers;
//	public JLabel jlabel_suppliers;
//	public JPanel jpanel_categories;
//	public JLabel jlabel_categories;
//	public JPanel jpanel_reports;
//	public JLabel jlabel_reports;
//	public JPanel jpanel_settings;
//	public JLabel jlabel_settings;
//
//	private JLabel lbl_title_name_employee;
//	private JLabel lbl_title_rol_employee;
//
//	private JButton btn_photo;
//	private JButton btn_logout;
//
//	// Product
//	private JTextField txt_product_code;
//	private JTextField txt_product_name;
//	private JTextField txt_product_unit_price;
//	private JTextField txt_product_description;
//	private JTextField txt_product_id;
//	private JButton btn_product_register;
//	private JButton btn_product_update;
//	private JButton btn_product_delete;
//	private JButton btn_product_cancel;
//	private JTextField txt_product_search; //sin usar?
//	private JTable product_table;//sin usar?
//
//	// Purchase
//	private JTextField txt_purchase_unit_code;
//	private JTextField txt_purchase_product_name;
//	private JTextField txt_purchase_amount;
//	private JTextField txt_purchase_unit_price;
//	private JTextField txt_purchase_supplier;
//	private JTextField txt_purchase_subtotal;
//	private JTextField txt_purchase_total;
//	private JTextField txt_purchase_id;
//	private JButton btn_purchase_add_to_buy;
//	private JButton btn_purchase_confirm;
//	private JButton btn_purchase_remove;
//	private JButton btn_purchase_new;
//	private JTable purchase_table;//sin usar?
//
//	// Customer
//	private JTextField txt_customer_id;
//	private JTextField txt_customer_address;
//	private JTextField txt_customer_telephone;
//	private JTextField txt_customer_email;
//	private JTextField txt_customer_fullname;
//	private JButton btn_customer_cancel;
//	private JButton btn_customer_delete;
//	private JButton btn_customer_update;
//	private JButton btn_customer_register;
//	private JTextField txt_customer_search;//sin usar?
//	private JTable customers_table;//sin usar?
//
//	// Employee
//	private JTextField txt_employee_id;
//	private JTextField txt_employee_username;
//	private JTextField txt_employee_address;
//	private JTextField txt_employee_telephone;
//	private JTextField txt_employee_fullname;
//	private JTextField txt_employee_email;
//	private JPasswordField txt_employee_password;
//	private JButton btn_employee_cancel;
//	private JButton btn_employee_delete;
//	private JButton btn_employee_update;
//	private JButton btn_employee_register;
//	private JTextField txt_employee_search;//sin usar?
//	private JTable employees_table;//sin usar?
//
//	// Suppliers
//	private JTextField txt_supplier_name;
//	private JTextField txt_supplier_address;
//	private JTextField txt_supplier_telephone;
//	private JTextField txt_supplier_description;
//	private JTextField txt_supplier_email;
//	private JTextField txt_supplier_id;
//	private JButton btn_supplier_register;
//	private JButton btn_supplier_update;
//	private JButton btn_supplier_delete;
//	private JButton btn_supplier_cancel;
//	private JTextField txt_suppliers_search;//sin usar?
//	private JTable suppliers_table;//sin usar?
//
//	// Category
//	private JTextField txt_category_id;
//	private JTextField txt_category_name;
//	private JButton btn_category_register;
//	private JButton btn_category_update;
//	private JButton btn_category_delete;
//	private JTextField txt_category_search;//sin usar?
//	private JTable categories_table;
//
//	// Reports
//	private JTable purchase_report_table;
//	private JTable sales_report_table;
//
//	// Settings
//	private JTextField txt_profile_id;
//	private JTextField txt_profile_address;
//	private JTextField txt_profile_telephone;
//	private JTextField txt_profile_email;
//	private JTextField txt_profile_fullname;
//	private JPasswordField txt_profile_password_modify;
//	private JPasswordField txt_profile_modify_confirm;
//	private JButton btn_profile_modify_data;
//
//	// Sales
//	private JTextField txt_sale_product_code;
//	private JTextField txt_sale_product_name;
//	private JTextField txt_sale_quantity;
//	private JTextField txt_sale_customer_id;
//	private JTextField txt_sale_price;
//	private JTextField txt_sale_product_id;
//	private JTextField txt_sale_subtotal;
//	private JTextField txt_sale_customer_name;
//	private JTextField txt_sale_stock;
//	private JTextField txt_sale_total_to_pay;
//	private JButton btn_sale_add_product;
//	private JButton btn_sale_new;
//	private JButton btn_sale_remove;
//	private JButton btn_sale_confirm;
//	private JTable sales_table;//sin usar?
//	private JComboBox<String> cmb_product_category;
//	private JComboBox<String> cmb_employee_rol;
//	public JTabbedPane panel_tab_menu_options;
//	public JComboBox<String> cmb_supplier_city;

	Employees employee = new Employees();
	EmployeesDao employeesDao = new EmployeesDao();
	//
	Customers customer = new Customers();
	CustomersDao customerDao = new CustomersDao();
	//
	Suppliers supplier = new Suppliers();
	SuppliersDao supplierDao = new SuppliersDao();
	//
	Categories category = new Categories();
	CategoriesDao categoryDao = new CategoriesDao();
	//
	Products product = new Products();
	ProductsDao productDao = new ProductsDao();
	//
	Purchases purchase = new Purchases();
	PurchasesDao purchaseDao = new PurchasesDao();
	//
	Sales sale = new Sales();
	SalesDao saleDao = new SalesDao();

	/**
	 * Create the application. Se lanza la aplicacion desde main..
	 */
	public DynamicSystemView() {
		initialize();
		frame.setSize(1208, 680);
		frame.setResizable(false);
		frame.setTitle("Panel de administración");
		frame.setLocationRelativeTo(null);
		frame.repaint();
		tittleInterface();
		@SuppressWarnings("unused")
		SettingsController setting = new SettingsController(this);

		EmployeesController employees_account = new EmployeesController(employee, employeesDao, this);

		CustomersController customer_account = new CustomersController(this);
		SuppliersController suppliers_account = new SuppliersController(supplier, supplierDao, this);

		CategoriesController categories_account = new CategoriesController(category, categoryDao, this);

		ProductsController product_section = new ProductsController(product, productDao, this);
//		product_section.listAllProducts();

		PurchasesController purchase_section = new PurchasesController(purchase, purchaseDao, this);

		SalesController sale_section = new SalesController(sale, saleDao, this);
	}

	public String tittleInterface() {
		frame.setTitle("Panel - " + rol_user);
		lbl_title_name_employee.setText(full_name_user);
		lbl_title_rol_employee.setText(rol_user);
		return rol_user.trim();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1208, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		loadLatMenuPanel();
		loadTopIconPanel();
		loadTopTittlePanel();
		loadInnerContentPanels();
	}

	private void loadInnerContentPanels() {
		// Creo el panel tab para agregar su contenido.
		panel_tab_menu_options = new JTabbedPane(JTabbedPane.TOP);
		panel_tab_menu_options.setBounds(200, 100, 990, 540);

		frame.getContentPane().add(panel_tab_menu_options);

		fillProductsTab(panel_tab_menu_options);
		fillPurchasesTab(panel_tab_menu_options);
		fillSalesTab(panel_tab_menu_options);
		fillCustomersTab(panel_tab_menu_options);
		fillEmployeesTab(panel_tab_menu_options);
		fillSuppliersTab(panel_tab_menu_options);
		fillCategoriesTab(panel_tab_menu_options);
		fillReportsTab(panel_tab_menu_options);
		fillSettingsTab(panel_tab_menu_options);
	}

	private void loadTopTittlePanel() {
		JPanel panel_title = UIComponentFactory.createGeneralPanelTab(frame, 200, 0, 990, 100,
				UIComponentFactory.getMenuPanelBackground());
		btn_photo = getNewCustomButton(630, 20, 80, 65, "btn_photo", buttonFont);
		panel_title.add(btn_photo);
		btn_photo.setIcon(new ImageIcon(SystemView.class.getResource("/images/man.png")));
		btn_logout = getNewCustomButton(890, 35, 80, 30, "SALIR", buttonFont);
		btn_logout.addActionListener(new ActionListener() {
			// TODO esto lo podria poner en un LogoutController
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn_logout) {
					dispose();
					LoginView login = new LoginView();
					login.setVisible(true);
				}
			}
		});

		panel_title.add(btn_logout);
//		panel_title.add(getNewCustomLabel(60, 0, 340, 100, "FARMACIA VIDA NATURAL", titleFont, CENTER, Color.WHITE));
		panel_title.add(UIComponentFactory.createCustomLabel(60, 0, 340, 100, "", UIComponentFactory.getTitleFont(),
				SwingConstants.CENTER, UIComponentFactory.getRegularColor()));

//		lbl_title_name_employee = getNewCustomLabel(720, 20, 139, 33, "", buttonFont, CENTER, Color.WHITE);
		lbl_title_name_employee = UIComponentFactory.createCustomLabel(720, 20, 139, 33, "",
				UIComponentFactory.getButtonFont(), SwingConstants.CENTER, UIComponentFactory.getRegularColor());
		panel_title.add(lbl_title_name_employee);

//		lbl_title_rol_employee = getNewCustomLabel(720, 67, 139, 33, "", buttonFont, CENTER, Color.WHITE);
		lbl_title_rol_employee = UIComponentFactory.createCustomLabel(720, 67, 139, 33, "",
				UIComponentFactory.getButtonFont(), SwingConstants.CENTER, UIComponentFactory.getRegularColor());
		panel_title.add(lbl_title_rol_employee);
	}

	private void loadTopIconPanel() {
		JPanel panel_icono = UIComponentFactory.createGeneralPanelTab(frame, 0, 0, 200, 100, regularColor);
		// TODO agregar icono farmacia
		// JLabel newCustomLabel = getNewCustomLabel(0, 0, 200, 100, "", normalFont,
		// CENTER);
		JLabel customLabel = UIComponentFactory.createCustomLabel(0, 0, 200, 100, "",
				UIComponentFactory.getNormalFont(), SwingConstants.CENTER, null);
		panel_icono.add(customLabel);
		customLabel.setIcon(new ImageIcon(SystemView.class.getResource("/images/farmacia.png")));

	}

	private void loadLatMenuPanel() {
		JPanel panel_menu = UIComponentFactory.createGeneralPanelTab(frame, 0, 100, 200, 580,
				UIComponentFactory.getMenuPanelBackground());

		// TODO ICONOS
		jpanel_products = UIComponentFactory.createGeneralPanelTab(frame, 0, 10, 200, 35,
				UIComponentFactory.getMenuPanelBackground());
		jlabel_products = getNewCustomLabel(0, 0, 200, 35, "      Productos", buttonFont, LEFT, regularColor);
		jpanel_products.add(jlabel_products);

		jpanel_purchases = UIComponentFactory.createGeneralPanelTab(frame, 0, 70, 200, 35,
				UIComponentFactory.getMenuPanelBackground());
		jlabel_purchases = getNewCustomLabel(0, 0, 200, 35, "      Compras", buttonFont, LEFT, regularColor);
		jpanel_purchases.add(jlabel_purchases);

		jpanel_sales = UIComponentFactory.createGeneralPanelTab(frame, 0, 130, 200, 35,
				UIComponentFactory.getMenuPanelBackground());
		jlabel_sales = getNewCustomLabel(0, 0, 200, 35, "      Ventas", buttonFont, LEFT, regularColor);
		jpanel_sales.add(jlabel_sales);

		jpanel_customers = UIComponentFactory.createGeneralPanelTab(frame, 0, 190, 200, 35,
				UIComponentFactory.getMenuPanelBackground());
		jlabel_customers = getNewCustomLabel(0, 0, 200, 35, "      Clientes", buttonFont, LEFT, regularColor);
		jpanel_customers.add(jlabel_customers);

		jpanel_employees = UIComponentFactory.createGeneralPanelTab(frame, 0, 250, 200, 35,
				UIComponentFactory.getMenuPanelBackground());
		jlabel_employees = getNewCustomLabel(0, 0, 200, 35, "      Empleados", buttonFont, LEFT, regularColor);
		jpanel_employees.add(jlabel_employees);

		jpanel_suppliers = UIComponentFactory.createGeneralPanelTab(frame, 0, 310, 200, 35,
				UIComponentFactory.getMenuPanelBackground());
		jlabel_suppliers = getNewCustomLabel(0, 0, 200, 35, "      Proveedores", buttonFont, LEFT, regularColor);
		jpanel_suppliers.add(jlabel_suppliers);

		jpanel_categories = UIComponentFactory.createGeneralPanelTab(frame, 0, 370, 200, 35,
				UIComponentFactory.getMenuPanelBackground());
		jlabel_categories = getNewCustomLabel(0, 0, 200, 35, "      Categorías", buttonFont, LEFT, regularColor);
		jpanel_categories.add(jlabel_categories);

		jpanel_reports = UIComponentFactory.createGeneralPanelTab(frame, 0, 430, 200, 35,
				UIComponentFactory.getMenuPanelBackground());
		jlabel_reports = getNewCustomLabel(0, 0, 200, 35, "      Reportes", buttonFont, LEFT, regularColor);
		jpanel_reports.add(jlabel_reports);

		jpanel_settings = UIComponentFactory.createGeneralPanelTab(frame, 0, 490, 200, 35,
				UIComponentFactory.getMenuPanelBackground());
		jlabel_settings = getNewCustomLabel(0, 0, 200, 35, "      Configuración", buttonFont, LEFT, regularColor);
		jpanel_settings.add(jlabel_settings);

		panel_menu.add(jpanel_products);
		panel_menu.add(jpanel_purchases);
		panel_menu.add(jpanel_sales);
		panel_menu.add(jpanel_customers);
		panel_menu.add(jpanel_employees);
		panel_menu.add(jpanel_suppliers);
		panel_menu.add(jpanel_categories);
		panel_menu.add(jpanel_reports);
		panel_menu.add(jpanel_settings);
	}

	private void fillProductsTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_products = createAndAddPanelTabContainer(panel_tab_menu_options, "Productos");

		JPanel Productos = getNewInternalPanel("Productos");
		tab_products.add(Productos);
		Productos.add(UIComponentFactory.createLabel(ComponentPosition.ONE, "Código:"));
		Productos.add(UIComponentFactory.createLabel(ComponentPosition.TWO, "Nombre:"));
		Productos.add(UIComponentFactory.createLabel(ComponentPosition.THREE, "Precio de venta:"));
		Productos.add(UIComponentFactory.createLabel(ComponentPosition.SIX, "Descripción:"));
		Productos.add(UIComponentFactory.createLabel(ComponentPosition.SEVEN, "Categoría:"));
		Productos.add(UIComponentFactory.createLabel(ComponentPosition.EIGHT, "Id:"));

		txt_product_code = UIComponentFactory.createTextField(ComponentPosition.ONE);
		Productos.add(txt_product_code);
		txt_product_name = UIComponentFactory.createTextField(ComponentPosition.TWO);
		Productos.add(txt_product_name);
		txt_product_unit_price = UIComponentFactory.createTextField(ComponentPosition.THREE);
		Productos.add(txt_product_unit_price);
		txt_product_description = UIComponentFactory.createTextField(ComponentPosition.SIX);
		Productos.add(txt_product_description);
		txt_product_id = UIComponentFactory.createTextField(ComponentPosition.EIGHT);
		txt_product_id.setEnabled(false);
		txt_product_id.setEditable(false);
		Productos.add(txt_product_id);

		cmb_product_category = getComboWithValues(List.of(), 530, 77, 180, 20);
		Productos.add(cmb_product_category);

		btn_product_register = UIComponentFactory.createButton(ButtonPosition.FIRST, "Registrar");
		Productos.add(btn_product_register);
		btn_product_update = UIComponentFactory.createButton(ButtonPosition.SECOND, "Actualizar");
		Productos.add(btn_product_update);
		btn_product_delete = UIComponentFactory.createButton(ButtonPosition.THIRD, "Eliminar");
		Productos.add(btn_product_delete);
		btn_product_cancel = UIComponentFactory.createButton(ButtonPosition.FOURTH, "Cancelar");
		Productos.add(btn_product_cancel);

		txt_product_search = UIComponentFactory.createFixedSearchButton(tab_products);
		//
		String[] columnNames = { "Id", "Código", "Nombre", "Descripción", "Precio de venta", "Cantidad", "Categoría" };
		boolean[] columnEditables = { false, false, false, false, true, true, false };
		product_table = UIComponentFactory.createTable(columnNames, columnEditables, tab_products);
	}

	private void fillPurchasesTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_purchases = createAndAddPanelTabContainer(panel_tab_menu_options, "Compras");
		JPanel NuevaCompra = getNewInternalPanel("Nueva compra");
		tab_purchases.add(NuevaCompra);

		NuevaCompra.add(UIComponentFactory.createLabel(ComponentPosition.ONE, "Código del producto:"));
		NuevaCompra.add(UIComponentFactory.createLabel(ComponentPosition.TWO, "Nombre del producto:"));
		NuevaCompra.add(UIComponentFactory.createLabel(ComponentPosition.THREE, "Cantidad:"));
		NuevaCompra.add(UIComponentFactory.createLabel(ComponentPosition.FOUR, "Proveedor:"));
		NuevaCompra.add(UIComponentFactory.createLabel(ComponentPosition.SIX, "Precio de compra:"));
		NuevaCompra.add(UIComponentFactory.createLabel(ComponentPosition.SEVEN, "Subtotal:"));
		NuevaCompra.add(UIComponentFactory.createLabel(ComponentPosition.EIGHT, "Id:"));
		NuevaCompra.add(UIComponentFactory.createLabel(ComponentPosition.NINE, "Total a pagar:"));

		txt_purchase_unit_code = UIComponentFactory.createTextField(ComponentPosition.ONE);
		NuevaCompra.add(txt_purchase_unit_code);
		txt_purchase_product_name = UIComponentFactory.createTextField(ComponentPosition.TWO);
		NuevaCompra.add(txt_purchase_product_name);
		txt_purchase_amount = UIComponentFactory.createTextField(ComponentPosition.THREE);
		NuevaCompra.add(txt_purchase_amount);

		cmb_purchase_supplier = UIComponentFactory.createComboBox(List.of(), 160, 177, 180, 20);
		NuevaCompra.add(cmb_purchase_supplier);

		txt_purchase_unit_price = UIComponentFactory.createTextField(ComponentPosition.SIX);
		NuevaCompra.add(txt_purchase_unit_price);
		txt_purchase_subtotal = UIComponentFactory.createTextField(ComponentPosition.SEVEN);
		txt_purchase_subtotal.setEditable(false);
		NuevaCompra.add(txt_purchase_subtotal);
		txt_purchase_id = UIComponentFactory.createTextField(ComponentPosition.EIGHT);
		txt_purchase_id.setEditable(false);
		txt_purchase_id.setEnabled(false);
		NuevaCompra.add(txt_purchase_id);
		txt_purchase_total = UIComponentFactory.createTextField(ComponentPosition.NINE);
		txt_purchase_total.setEditable(false);
		NuevaCompra.add(txt_purchase_total);

		btn_purchase_add_to_buy = UIComponentFactory.createButton(ButtonPosition.FIRST, "Agregar");
		NuevaCompra.add(btn_purchase_add_to_buy);
		btn_purchase_confirm = UIComponentFactory.createButton(ButtonPosition.SECOND, "Comprar");
		NuevaCompra.add(btn_purchase_confirm);
		btn_purchase_remove = UIComponentFactory.createButton(ButtonPosition.THIRD, "Eliminar");
		NuevaCompra.add(btn_purchase_remove);
		btn_purchase_new = UIComponentFactory.createButton(ButtonPosition.FOURTH, "Nuevo");
		NuevaCompra.add(btn_purchase_new);
		//
		String[] columnNames = { "Id", "Nombre del producto", "Cantidad", "Precio", "Subtotal", "Proveedor" };
		boolean[] columnEditables = { false, false, false, false, false, false, false };
		purchase_table = UIComponentFactory.createTable(columnNames, columnEditables, tab_purchases);
	}

	private void fillSalesTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_sales = createAndAddPanelTabContainer(panel_tab_menu_options, "Ventas");
		JPanel Ventas = getNewInternalPanel("Nueva venta");
		tab_sales.add(Ventas);

		JLabel productCode = UIComponentFactory.createLabel(ComponentPosition.ONE, "Código de producto:");
		productCode.setBounds(10, 20, 140, 35);// Necesario modificar luego de crear
		Ventas.add(productCode);
		JLabel productId = UIComponentFactory.createLabel(ComponentPosition.ONE, "Id:");
		productId.setBounds(105, 20, 140, 35);// Necesario modificar luego de crear
		Ventas.add(productId);
		Ventas.add(UIComponentFactory.createLabel(ComponentPosition.TWO, "Nombre del producto:"));
		Ventas.add(UIComponentFactory.createLabel(ComponentPosition.THREE, "Cantidad:"));
		Ventas.add(UIComponentFactory.createLabel(ComponentPosition.FOUR, "Cédula cliente:"));
		Ventas.add(UIComponentFactory.createLabel(ComponentPosition.FIVE, "Total a pagar:"));
		Ventas.add(UIComponentFactory.createLabel(ComponentPosition.SIX, "Precio:"));
		Ventas.add(UIComponentFactory.createLabel(ComponentPosition.SEVEN, "Subtotal:"));
		Ventas.add(UIComponentFactory.createLabel(ComponentPosition.EIGHT, "Nombre del cliente:"));
		Ventas.add(UIComponentFactory.createLabel(ComponentPosition.NINE, "Stock:"));

		txt_sale_product_code = UIComponentFactory.createTextField(ComponentPosition.ONE);
		txt_sale_product_code.setBounds(160, 27, 60, 20); // Necesario modificar luego de crear
		Ventas.add(txt_sale_product_code);
		txt_sale_product_id = UIComponentFactory.createTextField(ComponentPosition.ONE);
		txt_sale_product_id.setBounds(255, 27, 85, 20);// Necesario modificar luego de crear
		Ventas.add(txt_sale_product_id);

		txt_sale_product_name = UIComponentFactory.createTextField(ComponentPosition.TWO);
		Ventas.add(txt_sale_product_name);
		txt_sale_quantity = UIComponentFactory.createTextField(ComponentPosition.THREE);
		Ventas.add(txt_sale_quantity);
		txt_sale_customer_id = UIComponentFactory.createTextField(ComponentPosition.FOUR);
		Ventas.add(txt_sale_customer_id);
		txt_sale_total_to_pay = UIComponentFactory.createTextField(ComponentPosition.FIVE);
		Ventas.add(txt_sale_total_to_pay);
		txt_sale_price = UIComponentFactory.createTextField(ComponentPosition.SIX);
		Ventas.add(txt_sale_price);
		txt_sale_subtotal = UIComponentFactory.createTextField(ComponentPosition.SEVEN);
		Ventas.add(txt_sale_subtotal);
		txt_sale_customer_name = UIComponentFactory.createTextField(ComponentPosition.EIGHT);
		Ventas.add(txt_sale_customer_name);
		txt_sale_stock = UIComponentFactory.createTextField(ComponentPosition.NINE);
		Ventas.add(txt_sale_stock);

		btn_sale_add_product = UIComponentFactory.createButton(ButtonPosition.FIRST, "Agregar");
		Ventas.add(btn_sale_add_product);
		btn_sale_confirm = UIComponentFactory.createButton(ButtonPosition.SECOND, "Vender");
		Ventas.add(btn_sale_confirm);
		btn_sale_remove = UIComponentFactory.createButton(ButtonPosition.THIRD, "Eliminar");
		Ventas.add(btn_sale_remove);
		btn_sale_new = UIComponentFactory.createButton(ButtonPosition.FOURTH, "Nuevo");
		Ventas.add(btn_sale_new);
		//
		String[] columnNames = { "Id producto", "Nombre", "Cantidad", "Precio de venta", "Subtotal",
				"Nombre del cliente" };
		boolean[] columnEditables = { false, false, false, false, false, false };
		sales_table = UIComponentFactory.createTable(columnNames, columnEditables, tab_sales);

	}

	private void fillCustomersTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_customers = createAndAddPanelTabContainer(panel_tab_menu_options, "Clientes");
		JPanel Cliente = getNewInternalPanel("Clientes");
		tab_customers.add(Cliente);

		Cliente.add(UIComponentFactory.createLabel(ComponentPosition.ONE, "Identificación:"));
		Cliente.add(UIComponentFactory.createLabel(ComponentPosition.TWO, "Nombre completo:"));
		Cliente.add(UIComponentFactory.createLabel(ComponentPosition.THREE, "Dirección:"));
		Cliente.add(UIComponentFactory.createLabel(ComponentPosition.SIX, "Teléfono:"));
		Cliente.add(UIComponentFactory.createLabel(ComponentPosition.SEVEN, "Correo:"));

		txt_customer_id = UIComponentFactory.createTextField(ComponentPosition.ONE);
		Cliente.add(txt_customer_id);
		txt_customer_fullname = UIComponentFactory.createTextField(ComponentPosition.TWO);
		Cliente.add(txt_customer_fullname);
		txt_customer_address = UIComponentFactory.createTextField(ComponentPosition.THREE);
		Cliente.add(txt_customer_address);
		txt_customer_telephone = UIComponentFactory.createTextField(ComponentPosition.SIX);
		Cliente.add(txt_customer_telephone);
		txt_customer_email = UIComponentFactory.createTextField(ComponentPosition.SEVEN);
		Cliente.add(txt_customer_email);

		btn_customer_register = UIComponentFactory.createButton(ButtonPosition.FIRST, "Registrar");
		Cliente.add(btn_customer_register);
		btn_customer_update = UIComponentFactory.createButton(ButtonPosition.SECOND, "Modificar");
		Cliente.add(btn_customer_update);
		btn_customer_delete = UIComponentFactory.createButton(ButtonPosition.THIRD, "Eliminar");
		Cliente.add(btn_customer_delete);
		btn_customer_cancel = UIComponentFactory.createButton(ButtonPosition.FOURTH, "Cancelar");
		Cliente.add(btn_customer_cancel);

		txt_customer_search = UIComponentFactory.createFixedSearchButton(tab_customers);

		String[] columnNames = { "Identificación", "Nombre", "Teléfono", "Dirección", "Correo" };
		boolean[] columnEditables = { true, true, true, true, false };
		customers_table = UIComponentFactory.createTable(columnNames, columnEditables, tab_customers);

	}

	private void fillEmployeesTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_employees = createAndAddPanelTabContainer(panel_tab_menu_options, "Empleados");
		JPanel Empleados = getNewInternalPanel("Empleados");
		tab_employees.add(Empleados);

		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.ONE, "Identificación:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.TWO, "Nombre completo:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.THREE, "Nombre de usuario:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.FOUR, "Rol:"));

		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.SIX, "Dirección:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.SEVEN, "Teléfono:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.EIGHT, "Correo:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.NINE, "Contraseña:"));

		txt_employee_id = UIComponentFactory.createTextField(ComponentPosition.ONE);
		Empleados.add(txt_employee_id);
		txt_employee_fullname = UIComponentFactory.createTextField(ComponentPosition.TWO);
		Empleados.add(txt_employee_fullname);
		txt_employee_username = UIComponentFactory.createTextField(ComponentPosition.THREE);
		Empleados.add(txt_employee_username);
		txt_employee_address = UIComponentFactory.createTextField(ComponentPosition.SIX);
		Empleados.add(txt_employee_address);
		txt_employee_telephone = UIComponentFactory.createTextField(ComponentPosition.SEVEN);
		Empleados.add(txt_employee_telephone);
		txt_employee_email = UIComponentFactory.createTextField(ComponentPosition.EIGHT);
		Empleados.add(txt_employee_email);

		btn_employee_register = UIComponentFactory.createButton(ButtonPosition.FIRST, "Registrar");
		Empleados.add(btn_employee_register);
		btn_employee_update = UIComponentFactory.createButton(ButtonPosition.SECOND, "Modificar");
		Empleados.add(btn_employee_update);
		btn_employee_delete = UIComponentFactory.createButton(ButtonPosition.THIRD, "Eliminar");
		Empleados.add(btn_employee_delete);
		btn_employee_cancel = UIComponentFactory.createButton(ButtonPosition.FOURTH, "Cancelar");
		Empleados.add(btn_employee_cancel);

		txt_employee_password = new JPasswordField();
		txt_employee_password.setFont(buttonFont);
		txt_employee_password.setBounds(530, 177, 180, 20);
		Empleados.add(txt_employee_password);

		List<String> list = new ArrayList<>();
		list.add("ADMINISTRADOR");
		list.add("AUXILIAR");
		cmb_employee_rol = getComboWithValues(list, 160, 177, 180, 20);
		Empleados.add(cmb_employee_rol);

		txt_employee_search = UIComponentFactory.createFixedSearchButton(tab_employees);

		String[] columnNames = { "Identificación", "Nombre", "Nombre de usuario", "Dirección", "Teléfono", "Correo",
				"Rol" };
		boolean[] columnEditables = { false, false, false, false, false, false, false };

		employees_table = UIComponentFactory.createTable(columnNames, columnEditables, tab_employees);

	}

	private void fillSuppliersTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_suppliers = createAndAddPanelTabContainer(panel_tab_menu_options, "Proveedores");
		JPanel Proveedores = getNewInternalPanel("Proveedores");
		tab_suppliers.add(Proveedores);

		Proveedores.add(UIComponentFactory.createLabel(ComponentPosition.ONE, "Nombre:"));
		Proveedores.add(UIComponentFactory.createLabel(ComponentPosition.TWO, "Dirección:"));
		Proveedores.add(UIComponentFactory.createLabel(ComponentPosition.THREE, "Teléfono:"));
		Proveedores.add(UIComponentFactory.createLabel(ComponentPosition.FOUR, "Correo:"));
		Proveedores.add(UIComponentFactory.createLabel(ComponentPosition.SIX, "Descripción:"));
		Proveedores.add(UIComponentFactory.createLabel(ComponentPosition.SEVEN, "Ciudad:"));
		Proveedores.add(UIComponentFactory.createLabel(ComponentPosition.EIGHT, "Id:"));

		txt_supplier_name = UIComponentFactory.createTextField(ComponentPosition.ONE);
		Proveedores.add(txt_supplier_name);
		txt_supplier_address = UIComponentFactory.createTextField(ComponentPosition.TWO);
		Proveedores.add(txt_supplier_address);
		txt_supplier_telephone = UIComponentFactory.createTextField(ComponentPosition.THREE);
		Proveedores.add(txt_supplier_telephone);
		txt_supplier_email = UIComponentFactory.createTextField(ComponentPosition.FOUR);
		Proveedores.add(txt_supplier_email);

		txt_supplier_description = UIComponentFactory.createTextField(ComponentPosition.SIX);
		Proveedores.add(txt_supplier_description);

		txt_supplier_id = UIComponentFactory.createTextField(ComponentPosition.EIGHT);
		txt_supplier_id.setEnabled(false);
		Proveedores.add(txt_supplier_id);

		List<String> list = new ArrayList<>();
		list.add("Buenos Aires");
		list.add("Barcelona");
		cmb_supplier_city = getComboWithValues(list, 530, 77, 180, 20);
		Proveedores.add(cmb_supplier_city);

		btn_supplier_register = UIComponentFactory.createButton(ButtonPosition.FIRST, "Registrar");
		Proveedores.add(btn_supplier_register);
		btn_supplier_update = UIComponentFactory.createButton(ButtonPosition.SECOND, "Modificar");
		Proveedores.add(btn_supplier_update);
		btn_supplier_delete = UIComponentFactory.createButton(ButtonPosition.THIRD, "Eliminar");
		Proveedores.add(btn_supplier_delete);
		btn_supplier_cancel = UIComponentFactory.createButton(ButtonPosition.FOURTH, "Cancelar");
		Proveedores.add(btn_supplier_cancel);

		txt_suppliers_search = UIComponentFactory.createFixedSearchButton(tab_suppliers);

		String[] columnNames = { "Id", "Nombre", "Descripción", "Dirección", "Teléfono", "Correo", "Ciudad" };
		boolean[] columnEditables = { false, false, false, false, false, false, false };

		suppliers_table = UIComponentFactory.createTable(columnNames, columnEditables, tab_suppliers);

	}

	private JComboBox getComboWithValues(List<String> list, int x, int y, int width, int height) {
		String[] array = new String[0];
		if (list != null) {
			array = list.toArray(new String[0]);
		}
		DefaultComboBoxModel<String> aModel = new DefaultComboBoxModel<String>(array);
		//
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(aModel);
		comboBox.setFont(buttonFont);
		comboBox.setBounds(x, y, width, height);
		return comboBox;
	}

	private void fillCategoriesTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_category = createAndAddPanelTabContainer(panel_tab_menu_options, "Categorías");
		JPanel Categorias = getNewInternalPanel(35, 10, 450, 280, "Categorías");

		tab_category.add(Categorias);

		Categorias.add(UIComponentFactory.createLabel(ComponentPosition.ONE, "Id:"));
		Categorias.add(UIComponentFactory.createLabel(ComponentPosition.TWO, "Nombre:"));

		txt_category_id = UIComponentFactory.createTextField(ComponentPosition.ONE);
		txt_category_id.setEditable(false);
		txt_category_id.setEnabled(false);
		Categorias.add(txt_category_id);
		txt_category_name = UIComponentFactory.createTextField(ComponentPosition.TWO);
		Categorias.add(txt_category_name);

		btn_category_register = UIComponentFactory.createCustomButton(120, 120, 210, 35, "Registrar");
		Categorias.add(btn_category_register);
		btn_category_update = UIComponentFactory.createCustomButton(120, 160, 210, 35, "Modificar");
		Categorias.add(btn_category_update);
		btn_category_delete = UIComponentFactory.createCustomButton(120, 200, 210, 35, "Eliminar");
		Categorias.add(btn_category_delete);
		btn_category_cancel = UIComponentFactory.createCustomButton(120, 240, 210, 35, "Cancelar");
		Categorias.add(btn_category_cancel);

		txt_category_search = UIComponentFactory.createFixedSearchButton(tab_category);

		String[] columnNames = { "Id", "Nombre" };
		boolean[] columnEditables = { false, false };

		categories_table = UIComponentFactory.createTable(columnNames, columnEditables, tab_category);
		// Posicion particular para esta tabla
		categories_table.getParent().getParent().setBounds(510, 10, 460, 490);

	}

	private void fillReportsTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_reports = createAndAddPanelTabContainer(panel_tab_menu_options, "Reportes");

//		tab_reports.add(getNewCustomLabel(125, 10, 670, 35, "VENTAS REALIZADAS:", normalFont, CENTER));
		tab_reports.add(UIComponentFactory.createCustomLabel(125, 10, 670, 35, "COMPRAS REALIZADAS:",
				UIComponentFactory.getNormalFont(), SwingConstants.CENTER, null));

		String[] columnNames = { "Factura de compra ", "Proveedor", "Total", "Fecha de venta" };
		boolean[] columnEditables = { false, false, false, false };

		purchase_report_table = UIComponentFactory.createTable(columnNames, columnEditables, tab_reports);
		purchase_report_table.getParent().getParent().setBounds(125, 40, 670, 200);
		// Pos particular para esta tabla
//		tab_reports.add(getNewCustomLabel(125, 255, 670, 35, "COMPRAS REALIZADAS:", normalFont, CENTER));
		tab_reports.add(UIComponentFactory.createCustomLabel(125, 255, 670, 35, "VENTAS REALIZADAS:",
				UIComponentFactory.getNormalFont(), SwingConstants.CENTER, null));

		String[] columnNames1 = { "Factura", "Cliente", "Empleado", "Total", "Fecha de venta" };
		boolean[] columnEditables1 = { false, false, false, false, false };
		// Posicion particular para esta tabla
		sales_report_table = UIComponentFactory.createTable(columnNames1, columnEditables1, tab_reports);
		sales_report_table.getParent().getParent().setBounds(125, 285, 670, 200);

	}

	private void fillSettingsTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_settings = createAndAddPanelTabContainer(panel_tab_menu_options, "Perfil");
		JPanel Empleados = getNewInternalPanel("Empleados");

		tab_settings.add(Empleados);

		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.ONE, "Identificación:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.TWO, "Nombre completo:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.THREE, "Dirección:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.FOUR, "Teléfono:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.FIVE, "Correo:"));

		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.SIX, "Nueva contraseña:"));
		Empleados.add(UIComponentFactory.createLabel(ComponentPosition.SEVEN, "Repita contraseña:"));

		txt_profile_id = UIComponentFactory.createTextField(ComponentPosition.ONE);
		txt_profile_id.setEditable(false);
		Empleados.add(txt_profile_id);
		txt_profile_fullname = UIComponentFactory.createTextField(ComponentPosition.TWO);
		txt_profile_fullname.setEditable(false);
		Empleados.add(txt_profile_fullname);
		txt_profile_address = UIComponentFactory.createTextField(ComponentPosition.THREE);
		txt_profile_address.setEditable(false);
		Empleados.add(txt_profile_address);
		txt_profile_telephone = UIComponentFactory.createTextField(ComponentPosition.FOUR);
		txt_profile_telephone.setEditable(false);
		Empleados.add(txt_profile_telephone);
		txt_profile_email = UIComponentFactory.createTextField(ComponentPosition.FIVE);
		txt_profile_email.setEditable(false);
		Empleados.add(txt_profile_email);

		txt_profile_password_modify = new JPasswordField();
		txt_profile_password_modify.setFont(buttonFont);
		txt_profile_password_modify.setBounds(530, 27, 180, 20);
		Empleados.add(txt_profile_password_modify);

		txt_profile_modify_confirm = new JPasswordField();
		txt_profile_modify_confirm.setFont(buttonFont);
		txt_profile_modify_confirm.setBounds(530, 77, 180, 20);
		Empleados.add(txt_profile_modify_confirm);

		btn_profile_modify_data = UIComponentFactory.createButton(ButtonPosition.FOURTH, "Modificar");
		Empleados.add(btn_profile_modify_data);
	}

	private JButton getNewCustomButton(int x, int y, int width, int height, String string, Font font) {
		JButton jButton = new JButton(string);
		jButton.setFont(font);
		jButton.setBounds(x, y, width, height);
		return jButton;

	}

	private JButton getNewButton(bPos position, String string) {
		JButton jButton = new JButton(string);
		jButton.setFont(buttonFont);
		if (position.equals(bPos.FIRSTPOSITION)) {
			jButton.setBounds(730, 35, 150, 20);
		}
		if (position.equals(bPos.SECONDPOSITION)) {
			jButton.setBounds(730, 100, 150, 20);
		}
		if (position.equals(bPos.THIRDPOSITION)) {
			jButton.setBounds(730, 165, 150, 20);
		}
		if (position.equals(bPos.FOURTHPOSITION)) {
			jButton.setBounds(730, 230, 150, 20);
		}
		return jButton;
	}

//	private JLabel getNewCustomLabel(int x, int y, int width, int height, String label, Font font, int align) {
//		return getNewCustomLabel(x, y, width, height, label, font, align, null);
//	}

	private JLabel getNewCustomLabel(int x, int y, int width, int height, String label, Font font, int align,
			Color foreground) {
		JLabel jLable = new JLabel(label);
		jLable.setHorizontalAlignment(align);
		if (foreground != null) {
			jLable.setForeground(foreground);
		}
		jLable.setFont(font);
		jLable.setBounds(x, y, width, height);
		return jLable;
	}

//	private JLabel getNewLabel(compPos pos, String label) {
//
//		JLabel jLabel = new JLabel(label);
//		jLabel.setHorizontalAlignment(RIGHT);
//		jLabel.setFont(normalFont);
//		pos.applyLabelBounds(jLabel);
//		return jLabel;
//	}

//	private JTextField getNewTextField(compPos pos) {
//		JTextField jTextField = new JTextField();
//		jTextField.setFont(normalFont);
//		jTextField.setColumns(10);
//		pos.applyTextFieldBounds(jTextField);
//		return jTextField;
//	}

//	private JPanel createGeneralPanelTab(int x, int y, int width, int height, Color bgColor) {
//		return UIComponentFactory.createGeneralPanelTab(frame, x, y, width, height, bgColor);
////		if (width <= 0 || height <= 0) {
////			x = 200;
////			y = 0;
////			width = 990;
////			height = 100;
////			bgColor = UIComponentFactory.getMenuPanelBackground();
////		}
////		JPanel jPanel = new JPanel();
////		jPanel.setBackground(bgColor);
////		jPanel.setBounds(x, y, width, height);
////		frame.getContentPane().add(jPanel);
////		jPanel.setLayout(null);
////		return jPanel;
//	}

	public static JPanel getNewInternalPanel(int x, int y, int width, int height, String string) {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		jPanel.setBorder(new TitledBorder(null, string, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel.setBounds(x, y, width, height);
		return jPanel;
	}

	public static JPanel getNewInternalPanel(String string) {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(null);
		jPanel.setBorder(new TitledBorder(null, string, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel.setBounds(35, 10, 920, 280);
		return jPanel;
	}

	private JPanel createAndAddPanelTabContainer(JTabbedPane panel_tab_menu_options, String string) {
		JPanel jPanel = new JPanel();
		jPanel.setBackground(tabPanelBackgourndColor);
		jPanel.setLayout(null);
		panel_tab_menu_options.addTab(string, null, jPanel, null);
		return jPanel;
	}

	public void setVisible(boolean b) {
		frame.setVisible(true);
	}

	public void dispose() {
		frame.dispose();

	}
}