package views;

import static dao.EmployeesDao.full_name_user;
import static dao.EmployeesDao.rol_user;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controllers.CategoriesController;
import controllers.CustomersControllerDeprecated;
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
import views.base.AbstractSystemView;;

public class SystemView extends AbstractSystemView {

	public Color tabPanelBackgourndColor = new Color(69, 193, 241);

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

//	public JButton btn_sale_add_product;
//	public JButton btn_sale_confirm;
//	public JButton btn_sale_remove;
//	public JButton btn_sale_new;
//	public JPanel jpanel_sales;
//	public JLabel jlabel_sales;
	//

	/**
	 * Create the application.
	 */
	public SystemView() {
		initialize();
		frame.setSize(1208, 680);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.repaint();
		//
		tittleInterface();
		SettingsController setting = new SettingsController(this);

		EmployeesController employees_account = new EmployeesController(employee, employeesDao, this);
		employees_account.listAllEmployees();

		CustomersControllerDeprecated customer_account = new CustomersControllerDeprecated(customer, customerDao, this);
		customer_account.listAllCustomers();
		SuppliersController suppliers_account = new SuppliersController(supplier, supplierDao, this);
		suppliers_account.listAllSuppliers();

		CategoriesController categories_account = new CategoriesController(this);
		ProductsController product_section = new ProductsController(this);

		PurchasesController purchase_section = new PurchasesController(purchase, purchaseDao, this);
		purchase_section.listAllPurchases();

		SalesController sale_section = new SalesController(sale, saleDao, this);
		sale_section.listAllSales();
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

		// Creo el panel tab para agregar su contenido.
		panel_tab_menu_options = new JTabbedPane(JTabbedPane.TOP);
		panel_tab_menu_options.setBounds(199, 101, 991, 538);
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
		JPanel panel_title = new JPanel();
		Color panelMenoBackgourndColor = new Color(18, 45, 61);
		panel_title.setBackground(panelMenoBackgourndColor);
		panel_title.setBounds(199, 0, 991, 101);
		frame.getContentPane().add(panel_title);
		panel_title.setLayout(null);

		btn_photo = new JButton("");
		btn_photo.setIcon(new ImageIcon(SystemView.class.getResource("/images/man.png")));
		btn_photo.setBounds(630, 20, 80, 65);
		panel_title.add(btn_photo);

		btn_logout = new JButton("SALIR");
		btn_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == btn_logout) {
					dispose();
					LoginView login = new LoginView();
					login.setVisible(true);

				}
			}
		});
		btn_logout.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btn_logout.setBounds(890, 35, 80, 30);
		panel_title.add(btn_logout);

		JLabel lblNewLabel_1 = new JLabel("FARMACIA VIDA NATURAL");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 24));
		lblNewLabel_1.setBounds(60, 0, 340, 100);
		panel_title.add(lblNewLabel_1);

		lbl_title_name_employee = new JLabel("");
		lbl_title_name_employee.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_title_name_employee.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lbl_title_name_employee.setBounds(720, 20, 139, 33);
		lbl_title_name_employee.setForeground(Color.white);
		panel_title.add(lbl_title_name_employee);

		lbl_title_rol_employee = new JLabel("");
		lbl_title_rol_employee.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_title_rol_employee.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lbl_title_rol_employee.setBounds(720, 67, 139, 33);
		lbl_title_rol_employee.setForeground(Color.white);
		panel_title.add(lbl_title_rol_employee);
	}

	private void loadTopIconPanel() {
		JPanel panel_icono = new JPanel();
		panel_icono.setBackground(new Color(255, 255, 255));
		panel_icono.setBounds(0, 0, 200, 101);
		frame.getContentPane().add(panel_icono);
		panel_icono.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(SystemView.class.getResource("/images/farmacia.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBounds(0, 0, 200, 101);
		panel_icono.add(lblNewLabel);
	}

	private void loadLatMenuPanel() {
		JPanel panel_menu = new JPanel();
		panel_menu.setBackground(new Color(18, 45, 61));
		panel_menu.setBounds(0, 100, 200, 580);
		frame.getContentPane().add(panel_menu);
		panel_menu.setLayout(null);

		jpanel_products = new JPanel();
		jpanel_products.setBounds(0, 10, 200, 35);
		panel_menu.add(jpanel_products);
		jpanel_products.setLayout(null);
		jpanel_products.setBackground(new Color(18, 45, 61));

		jlabel_products = new JLabel(" Productos");
		jlabel_products.setHorizontalAlignment(SwingConstants.CENTER);
		jlabel_products.setIcon(new ImageIcon(SystemView.class.getResource("/images/box.png")));
		jlabel_products.setForeground(new Color(255, 255, 255));
		jlabel_products.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jlabel_products.setBounds(0, 0, 200, 35);
		jpanel_products.add(jlabel_products);

		jpanel_purchases = new JPanel();
		jpanel_purchases.setLayout(null);
		jpanel_purchases.setBackground(new Color(18, 45, 61));
		jpanel_purchases.setBounds(0, 70, 200, 35);
		panel_menu.add(jpanel_purchases);

		// TODO crear ventas y su panel
		jlabel_purchases = new JLabel("      Compras");
		jlabel_purchases.setForeground(new Color(255, 255, 255));
		jlabel_purchases.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jlabel_purchases.setBounds(0, 0, 200, 35);
		jpanel_purchases.add(jlabel_purchases);

		jpanel_customers = new JPanel();
		jpanel_customers.setLayout(null);
		jpanel_customers.setBackground(new Color(18, 45, 61));
		jpanel_customers.setBounds(0, 130, 200, 35);
		panel_menu.add(jpanel_customers);

		jlabel_customers = new JLabel("      Clientes");
		jlabel_customers.setForeground(new Color(255, 255, 255));
		jlabel_customers.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jlabel_customers.setBounds(0, 0, 200, 35);
		jpanel_customers.add(jlabel_customers);

		jpanel_employees = new JPanel();
		jpanel_employees.setLayout(null);
		jpanel_employees.setBackground(new Color(18, 45, 61));
		jpanel_employees.setBounds(0, 190, 200, 35);
		panel_menu.add(jpanel_employees);

		jlabel_employees = new JLabel("      Empleados");
		jlabel_employees.setForeground(new Color(255, 255, 255));
		jlabel_employees.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jlabel_employees.setBounds(0, 0, 200, 35);
		jpanel_employees.add(jlabel_employees);

		jpanel_suppliers = new JPanel();
		jpanel_suppliers.setLayout(null);
		jpanel_suppliers.setBackground(new Color(18, 45, 61));
		jpanel_suppliers.setBounds(0, 250, 200, 35);
		panel_menu.add(jpanel_suppliers);

		jlabel_suppliers = new JLabel("      Proveedores");
		jlabel_suppliers.setForeground(new Color(255, 255, 255));
		jlabel_suppliers.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jlabel_suppliers.setBounds(0, 0, 200, 35);
		jpanel_suppliers.add(jlabel_suppliers);

		jpanel_categories = new JPanel();
		jpanel_categories.setLayout(null);
		jpanel_categories.setBackground(new Color(18, 45, 61));
		jpanel_categories.setBounds(0, 310, 200, 35);
		panel_menu.add(jpanel_categories);

		jlabel_categories = new JLabel("      Categorías");
		jlabel_categories.setForeground(new Color(255, 255, 255));
		jlabel_categories.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jlabel_categories.setBounds(0, 0, 200, 35);
		jpanel_categories.add(jlabel_categories);

		jpanel_reports = new JPanel();
		jpanel_reports.setLayout(null);
		jpanel_reports.setBackground(new Color(18, 45, 61));
		jpanel_reports.setBounds(0, 370, 200, 35);
		panel_menu.add(jpanel_reports);

		jlabel_reports = new JLabel("      Reportes");
		jlabel_reports.setForeground(new Color(255, 255, 255));
		jlabel_reports.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jlabel_reports.setBounds(0, 0, 200, 35);
		jpanel_reports.add(jlabel_reports);

		jpanel_settings = new JPanel();
		jpanel_settings.setLayout(null);
		jpanel_settings.setBackground(new Color(18, 45, 61));
		jpanel_settings.setBounds(0, 430, 200, 35);
		panel_menu.add(jpanel_settings);

		jlabel_settings = new JLabel("      Configuración");
		jlabel_settings.setForeground(new Color(255, 255, 255));
		jlabel_settings.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jlabel_settings.setBounds(0, 0, 200, 35);
		jpanel_settings.add(jlabel_settings);

		jpanel_sales = new JPanel();
		jpanel_sales.setLayout(null);
		jpanel_sales.setBackground(new Color(18, 45, 61));
		jpanel_sales.setBounds(0, 430, 200, 35);
		panel_menu.add(jpanel_sales);

		jlabel_sales = new JLabel("      Ventas");
		jlabel_sales.setForeground(new Color(255, 255, 255));
		jlabel_sales.setFont(new Font("Times New Roman", Font.BOLD, 16));
		jlabel_sales.setBounds(0, 0, 200, 35);
		panel_menu.add(jlabel_sales);

	}

	private void fillProductsTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_products = new JPanel();
		tab_products.setBackground(tabPanelBackgourndColor);
		panel_tab_menu_options.addTab("Productos", null, tab_products, null);
		tab_products.setLayout(null);

		JPanel Productos = new JPanel();
		Productos.setLayout(null);
		Productos.setBorder(new TitledBorder(null, "Productos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Productos.setBounds(35, 11, 920, 270);
		tab_products.add(Productos);

		JLabel lblNewLabel_2_4 = new JLabel("Código: ");
		lblNewLabel_2_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_4.setBounds(30, 30, 139, 33);
		Productos.add(lblNewLabel_2_4);

		JLabel lblNewLabel_2_1_3 = new JLabel("Nombre:");
		lblNewLabel_2_1_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_3.setBounds(30, 100, 139, 33);
		Productos.add(lblNewLabel_2_1_3);

		JLabel lblNewLabel_2_2_2 = new JLabel("Precio de venta: ");
		lblNewLabel_2_2_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_2.setBounds(30, 165, 139, 33);
		Productos.add(lblNewLabel_2_2_2);

		JLabel lblNewLabel_2_3_1 = new JLabel("Descripción: ");
		lblNewLabel_2_3_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_3_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_3_1.setBounds(382, 30, 139, 33);
		Productos.add(lblNewLabel_2_3_1);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("Categoría: ");
		lblNewLabel_2_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_1.setBounds(382, 100, 139, 33);
		Productos.add(lblNewLabel_2_1_1_1);

		JLabel lblNewLabel_2_2_1_1 = new JLabel("Id: ");
		lblNewLabel_2_2_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_1_1.setBounds(382, 165, 139, 33);
		Productos.add(lblNewLabel_2_2_1_1);

		txt_product_code = new JTextField();
		txt_product_code.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_product_code.setColumns(10);
		txt_product_code.setBounds(179, 37, 166, 20);
		Productos.add(txt_product_code);

		txt_product_name = new JTextField();
		txt_product_name.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_product_name.setColumns(10);
		txt_product_name.setBounds(179, 107, 166, 20);
		Productos.add(txt_product_name);

		txt_product_unit_price = new JTextField();
		txt_product_unit_price.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_product_unit_price.setColumns(10);
		txt_product_unit_price.setBounds(179, 172, 166, 20);
		Productos.add(txt_product_unit_price);

		txt_product_description = new JTextField();
		txt_product_description.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_product_description.setColumns(10);
		txt_product_description.setBounds(531, 37, 166, 20);
		Productos.add(txt_product_description);

		txt_product_id = new JTextField();
		txt_product_id.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_product_id.setEnabled(false);
		txt_product_id.setEditable(false);
		txt_product_id.setColumns(10);
		txt_product_id.setBounds(531, 172, 166, 20);
		Productos.add(txt_product_id);

		cmb_product_category = new JComboBox();
		cmb_product_category.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cmb_product_category.setBounds(531, 106, 166, 22);
		Productos.add(cmb_product_category);

		btn_product_register = new JButton("Registrar");
		btn_product_register.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_product_register.setBounds(734, 36, 148, 23);
		Productos.add(btn_product_register);

		btn_product_update = new JButton("Actualizar");
		btn_product_update.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_product_update.setBounds(734, 106, 148, 23);
		Productos.add(btn_product_update);

		btn_product_delete = new JButton("Eliminar");
		btn_product_delete.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_product_delete.setBounds(734, 170, 148, 23);
		Productos.add(btn_product_delete);

		btn_product_cancel = new JButton("Cancelar");
		btn_product_cancel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_product_cancel.setBounds(734, 222, 148, 23);
		Productos.add(btn_product_cancel);

		JLabel lblNewLabel_2_1_2 = new JLabel("BUSCAR");
		lblNewLabel_2_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_2.setBounds(45, 297, 139, 33);
		tab_products.add(lblNewLabel_2_1_2);

		txt_product_search = new JTextField();
		txt_product_search.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_product_search.setColumns(10);
		txt_product_search.setBounds(224, 304, 166, 20);
		tab_products.add(txt_product_search);

		product_table = new JTable();
		product_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		product_table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, "" }, },
				new String[] { "Id", "C\u00F3digo", "Nombre", "Descripci\u00F3n", "Precio de venta", "Cantidad",
						"Categor\u00EDa" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class, Double.class,
					Integer.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		product_table.getColumnModel().getColumn(0).setResizable(false);
		product_table.getColumnModel().getColumn(1).setResizable(false);
		product_table.getColumnModel().getColumn(2).setResizable(false);
		product_table.getColumnModel().getColumn(3).setResizable(false);
		product_table.getColumnModel().getColumn(4).setResizable(false);
		product_table.getColumnModel().getColumn(6).setResizable(false);
		product_table.setBounds(35, 335, 920, 164);
		tab_products.add(product_table);
	}

	private void fillPurchasesTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_purchases = new JPanel();
		tab_purchases.setBackground(tabPanelBackgourndColor);
		panel_tab_menu_options.addTab("Compras", null, tab_purchases, null);
		tab_purchases.setLayout(null);

		JPanel NuevaCompra = new JPanel();
		NuevaCompra.setLayout(null);
		NuevaCompra.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),

				"Nueva compra", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		NuevaCompra.setBounds(35, 11, 920, 310);
		tab_purchases.add(NuevaCompra);

		JLabel lblNewLabel_2 = new JLabel("Código: del producto: ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(10, 30, 164, 33);
		NuevaCompra.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Nombre: del producto: ");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1.setBounds(10, 100, 164, 33);
		NuevaCompra.add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_2 = new JLabel("Cantidad: ");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2.setBounds(10, 165, 164, 33);
		NuevaCompra.add(lblNewLabel_2_2);

		JLabel lblNewLabel_2_3 = new JLabel("Precio de compra: ");
		lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_3.setBounds(382, 30, 139, 33);
		NuevaCompra.add(lblNewLabel_2_3);

		JLabel lblNewLabel_2_1_1 = new JLabel("Subtotal: ");
		lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1.setBounds(382, 100, 139, 33);
		NuevaCompra.add(lblNewLabel_2_1_1);

		JLabel lblNewLabel_2_2_1 = new JLabel("Id: ");
		lblNewLabel_2_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_1.setBounds(382, 165, 139, 33);
		NuevaCompra.add(lblNewLabel_2_2_1);

		txt_purchase_unit_code = new JTextField();
		txt_purchase_unit_code.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_purchase_unit_code.setColumns(10);
		txt_purchase_unit_code.setBounds(179, 37, 166, 20);
		NuevaCompra.add(txt_purchase_unit_code);

		txt_purchase_product_name = new JTextField();
		txt_purchase_product_name.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_purchase_product_name.setEditable(false);
		txt_purchase_product_name.setColumns(10);
		txt_purchase_product_name.setBounds(179, 107, 166, 20);
		NuevaCompra.add(txt_purchase_product_name);

		txt_purchase_amount = new JTextField();
		txt_purchase_amount.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_purchase_amount.setColumns(10);
		txt_purchase_amount.setBounds(179, 172, 166, 20);
		NuevaCompra.add(txt_purchase_amount);

		txt_purchase_unit_price = new JTextField();
		txt_purchase_unit_price.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_purchase_unit_price.setColumns(10);
		txt_purchase_unit_price.setBounds(531, 37, 166, 20);
		NuevaCompra.add(txt_purchase_unit_price);

		btn_purchase_add_to_buy = new JButton("Agregar");
		btn_purchase_add_to_buy.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_purchase_add_to_buy.setBounds(734, 36, 148, 23);
		NuevaCompra.add(btn_purchase_add_to_buy);

		btn_purchase_confirm = new JButton("Comprar");
		btn_purchase_confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_purchase_confirm.setBounds(734, 106, 148, 23);
		NuevaCompra.add(btn_purchase_confirm);

		btn_purchase_remove = new JButton("Eliminar");
		btn_purchase_remove.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_purchase_remove.setBounds(734, 170, 148, 23);
		NuevaCompra.add(btn_purchase_remove);

		btn_purchase_new = new JButton("Nuevo");
		btn_purchase_new.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_purchase_new.setBounds(734, 264, 148, 23);
		NuevaCompra.add(btn_purchase_new);

		purchase_table = new JTable();
		purchase_table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Id", "Nombre del producto", "Cantidad", "Precio", "Subtotal", "Proveedor" }));
		purchase_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		purchase_table.setBounds(35, 335, 920, 164);
		tab_purchases.add(purchase_table);

		cmb_purchase_supplier = new JComboBox();
		cmb_purchase_supplier.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cmb_purchase_supplier.setBounds(179, 232, 166, 22);
		NuevaCompra.add(cmb_purchase_supplier);

		JLabel lblNewLabel_2_2_1_2 = new JLabel("Proveedor: ");
		lblNewLabel_2_2_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_1_2.setBounds(30, 227, 139, 33);
		NuevaCompra.add(lblNewLabel_2_2_1_2);

		txt_purchase_subtotal = new JTextField();
		txt_purchase_subtotal.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_purchase_subtotal.setEditable(false);
		txt_purchase_subtotal.setColumns(10);
		txt_purchase_subtotal.setBounds(531, 107, 166, 20);
		NuevaCompra.add(txt_purchase_subtotal);

		txt_purchase_total = new JTextField();
		txt_purchase_total.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_purchase_total.setEditable(false);
		txt_purchase_total.setColumns(10);
		txt_purchase_total.setBounds(531, 233, 166, 20);
		NuevaCompra.add(txt_purchase_total);

		txt_purchase_id = new JTextField();
		txt_purchase_id.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_purchase_id.setEnabled(false);
		txt_purchase_id.setEditable(false);
		txt_purchase_id.setColumns(10);
		txt_purchase_id.setBounds(531, 172, 166, 20);
		NuevaCompra.add(txt_purchase_id);

		JLabel lblNewLabel_2_2_1_3 = new JLabel("Total a pagar: ");
		lblNewLabel_2_2_1_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_1_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_1_3.setBounds(382, 227, 139, 33);
		NuevaCompra.add(lblNewLabel_2_2_1_3);
	}

	private void fillSalesTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_sales = new JPanel();
		tab_sales.setBackground(tabPanelBackgourndColor);
		panel_tab_menu_options.addTab("Ventas", null, tab_sales, null);
		tab_sales.setLayout(null);

		JPanel Ventas = new JPanel();
		Ventas.setLayout(null);
		Ventas.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Nueva venta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Ventas.setBounds(29, 11, 920, 324);
		tab_sales.add(Ventas);

		JLabel lblNewLabel_2_5 = new JLabel("Código de producto: ");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_5.setBounds(10, 25, 164, 33);
		Ventas.add(lblNewLabel_2_5);

		txt_sale_product_code = new JTextField();
		txt_sale_product_code.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_product_code.setColumns(10);
		txt_sale_product_code.setBounds(179, 32, 76, 20);
		Ventas.add(txt_sale_product_code);

		JLabel lblNewLabel_2_1_4 = new JLabel("Nombre del producto: ");
		lblNewLabel_2_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_4.setBounds(10, 95, 164, 33);
		Ventas.add(lblNewLabel_2_1_4);

		txt_sale_product_name = new JTextField();
		txt_sale_product_name.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_product_name.setColumns(10);
		txt_sale_product_name.setBounds(179, 102, 206, 20);
		Ventas.add(txt_sale_product_name);

		JLabel lblNewLabel_2_2_3 = new JLabel("Cantidad: ");
		lblNewLabel_2_2_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_3.setBounds(10, 160, 164, 33);
		Ventas.add(lblNewLabel_2_2_3);

		txt_sale_quantity = new JTextField();
		txt_sale_quantity.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_quantity.setColumns(10);
		txt_sale_quantity.setBounds(179, 167, 206, 20);
		Ventas.add(txt_sale_quantity);

		JLabel lblNewLabel_2_3_2 = new JLabel("Cédula cliente: ");
		lblNewLabel_2_3_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_3_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_3_2.setBounds(35, 217, 139, 33);
		Ventas.add(lblNewLabel_2_3_2);

		txt_sale_customer_id = new JTextField();
		txt_sale_customer_id.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_customer_id.setColumns(10);
		txt_sale_customer_id.setBounds(179, 223, 206, 20);
		Ventas.add(txt_sale_customer_id);

		JLabel lblNewLabel_2_1_1_2 = new JLabel("Precio:");
		lblNewLabel_2_1_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_2.setBounds(382, 25, 139, 33);
		Ventas.add(lblNewLabel_2_1_1_2);

		txt_sale_price = new JTextField();
		txt_sale_price.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_price.setColumns(10);
		txt_sale_price.setBounds(531, 32, 166, 20);
		Ventas.add(txt_sale_price);

		JLabel lblNewLabel_2_1_1_4 = new JLabel("Id: ");
		lblNewLabel_2_1_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_4.setBounds(149, 25, 139, 33);
		Ventas.add(lblNewLabel_2_1_1_4);

		txt_sale_product_id = new JTextField();
		txt_sale_product_id.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_product_id.setColumns(10);
		txt_sale_product_id.setBounds(298, 32, 87, 20);
		Ventas.add(txt_sale_product_id);

		btn_sale_add_product = new JButton("Agregar");
		btn_sale_add_product.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_sale_add_product.setBounds(734, 31, 148, 23);
		Ventas.add(btn_sale_add_product);

		btn_sale_confirm = new JButton("Vender");
		btn_sale_confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_sale_confirm.setBounds(734, 101, 148, 23);
		Ventas.add(btn_sale_confirm);

		btn_sale_remove = new JButton("Eliminar");
		btn_sale_remove.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_sale_remove.setBounds(734, 165, 148, 23);
		Ventas.add(btn_sale_remove);

		btn_sale_new = new JButton("Nuevo");
		btn_sale_new.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_sale_new.setBounds(734, 227, 148, 23);
		Ventas.add(btn_sale_new);

		JLabel lblNewLabel_2_1_1_2_1 = new JLabel("Subtotal:");
		lblNewLabel_2_1_1_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_2_1.setBounds(382, 89, 139, 33);
		Ventas.add(lblNewLabel_2_1_1_2_1);

		txt_sale_subtotal = new JTextField();
		txt_sale_subtotal.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_subtotal.setColumns(10);
		txt_sale_subtotal.setBounds(531, 96, 166, 20);
		Ventas.add(txt_sale_subtotal);

		JLabel lblNewLabel_2_1_1_2_1_1 = new JLabel("Nombre del cliente: ");
		lblNewLabel_2_1_1_2_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_2_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_2_1_1.setBounds(382, 224, 139, 33);
		Ventas.add(lblNewLabel_2_1_1_2_1_1);

		txt_sale_customer_name = new JTextField();
		txt_sale_customer_name.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_customer_name.setColumns(10);
		txt_sale_customer_name.setBounds(531, 231, 166, 20);
		Ventas.add(txt_sale_customer_name);

		JLabel lblNewLabel_2_1_1_2_2 = new JLabel("Stocl:");
		lblNewLabel_2_1_1_2_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_2_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_2_2.setBounds(382, 160, 139, 33);
		Ventas.add(lblNewLabel_2_1_1_2_2);

		txt_sale_stock = new JTextField();
		txt_sale_stock.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_stock.setColumns(10);
		txt_sale_stock.setBounds(531, 167, 166, 20);
		Ventas.add(txt_sale_stock);

		txt_sale_total_to_pay = new JTextField();
		txt_sale_total_to_pay.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_sale_total_to_pay.setColumns(10);
		txt_sale_total_to_pay.setBounds(179, 275, 206, 20);
		Ventas.add(txt_sale_total_to_pay);

		JLabel lblNewLabel_2_1_1_2_1_1_1 = new JLabel("Total a pagar: ");
		lblNewLabel_2_1_1_2_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_2_1_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_2_1_1_1.setBounds(35, 269, 139, 33);
		Ventas.add(lblNewLabel_2_1_1_2_1_1_1);

		sales_table = new JTable();
		sales_table.setModel(
				new DefaultTableModel(new Object[][] { { null, null, null, null, null, null }, }, new String[] {
						"Id producto", "Nombre", "Cantidad", "Precio de venta", "Subtotal", "Nombre del cliente" }) {
					boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
		sales_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		sales_table.setBounds(27, 357, 920, 136);
		tab_sales.add(sales_table);
	}

	private void fillCustomersTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_customers = new JPanel();
		tab_customers.setBackground(tabPanelBackgourndColor);
		panel_tab_menu_options.addTab("Clientes", null, tab_customers, null);
		tab_customers.setLayout(null);

		// Panel interno cliente
		JPanel Cliente = new JPanel();
		Cliente.setLayout(null);
		Cliente.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Clientes",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Cliente.setBounds(29, 11, 920, 292);
		tab_customers.add(Cliente);

		// Etiquetas
		String labelString = "Identificación: ";
		// TODO Aca puedo hacer como una factorie para crear cada elemento.
		JLabel lblNewLabel_2_5 = new JLabel(labelString);
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		Cliente.add(lblNewLabel_2_5);
		lblNewLabel_2_5.setBounds(10, 30, 164, 33);

		JLabel lblNewLabel_2_1_4 = new JLabel("Nombre: completo: ");
		lblNewLabel_2_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_4.setBounds(10, 100, 164, 33);
		Cliente.add(lblNewLabel_2_1_4);

		JLabel lblNewLabel_2_2_3 = new JLabel("Dirección: ");
		lblNewLabel_2_2_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_3.setBounds(10, 165, 164, 33);
		Cliente.add(lblNewLabel_2_2_3);

		JLabel lblNewLabel_2_3_2 = new JLabel("Teléfono: ");
		lblNewLabel_2_3_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_3_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_3_2.setBounds(382, 30, 139, 33);
		Cliente.add(lblNewLabel_2_3_2);

		JLabel lblNewLabel_2_1_1_2 = new JLabel("Correo: ");
		lblNewLabel_2_1_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_2.setBounds(382, 100, 139, 33);
		Cliente.add(lblNewLabel_2_1_1_2);

		txt_customer_id = new JTextField();
		txt_customer_id.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_customer_id.setColumns(10);
		txt_customer_id.setBounds(179, 37, 166, 20);
		Cliente.add(txt_customer_id);

		txt_customer_address = new JTextField();
		txt_customer_address.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_customer_address.setColumns(10);
		txt_customer_address.setBounds(179, 172, 166, 20);
		Cliente.add(txt_customer_address);

		txt_customer_telephone = new JTextField();
		txt_customer_telephone.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_customer_telephone.setColumns(10);
		txt_customer_telephone.setBounds(531, 37, 166, 20);
		Cliente.add(txt_customer_telephone);

		btn_customer_register = new JButton("Registrar");
		btn_customer_register.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_customer_register.setBounds(734, 36, 148, 23);
		Cliente.add(btn_customer_register);

		btn_customer_update = new JButton("Modificar");
		btn_customer_update.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_customer_update.setBounds(734, 106, 148, 23);
		Cliente.add(btn_customer_update);

		btn_customer_delete = new JButton("Eliminar");
		btn_customer_delete.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_customer_delete.setBounds(734, 170, 148, 23);
		Cliente.add(btn_customer_delete);

		btn_customer_cancel = new JButton("Cancelar");
		btn_customer_cancel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_customer_cancel.setBounds(734, 232, 148, 23);
		Cliente.add(btn_customer_cancel);

		txt_customer_email = new JTextField();
		txt_customer_email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_customer_email.setColumns(10);
		txt_customer_email.setBounds(531, 107, 166, 20);
		Cliente.add(txt_customer_email);

		txt_customer_fullname = new JTextField();
		txt_customer_fullname.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_customer_fullname.setColumns(10);
		txt_customer_fullname.setBounds(179, 107, 166, 20);
		Cliente.add(txt_customer_fullname);

		// Search lbl and txt
		JLabel lblNewLabel_2_1_2_1 = new JLabel("BUSCAR");
		lblNewLabel_2_1_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_2_1.setBounds(39, 314, 139, 33);
		tab_customers.add(lblNewLabel_2_1_2_1);

		txt_customer_search = new JTextField();
		txt_customer_search.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_customer_search.setColumns(10);
		txt_customer_search.setBounds(205, 320, 166, 20);
		tab_customers.add(txt_customer_search);

		// Table
		customers_table = new JTable();
		customers_table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null }, },
				new String[] { "Identificaci\u00F3n", "Nombre", "Direcci\u00F3n", "Tel\u00E9fono", "Correo" }) {
			boolean[] columnEditables = new boolean[] { true, true, true, true, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		customers_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		customers_table.setBounds(29, 352, 920, 136);
		tab_customers.add(customers_table);
	}

	private void fillEmployeesTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_employees = new JPanel();
		tab_employees.setBackground(tabPanelBackgourndColor);
		panel_tab_menu_options.addTab("Empleados", null, tab_employees, null);
		tab_employees.setLayout(null);

		JPanel Empleados = new JPanel();
		Empleados.setLayout(null);
		Empleados.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Empleados",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Empleados.setBounds(29, 11, 920, 292);
		tab_employees.add(Empleados);

		JLabel lblNewLabel_2_5 = new JLabel("Identificación: ");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_5.setBounds(10, 30, 164, 33);
		Empleados.add(lblNewLabel_2_5);

		JLabel lblNewLabel_2_1_4 = new JLabel("Nombre completo: ");
		lblNewLabel_2_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_4.setBounds(10, 100, 164, 33);
		Empleados.add(lblNewLabel_2_1_4);

		JLabel lblNewLabel_2_2_3 = new JLabel("Nombre de usuario: ");
		lblNewLabel_2_2_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_3.setBounds(10, 165, 164, 33);
		Empleados.add(lblNewLabel_2_2_3);

		JLabel lblNewLabel_2_3_2 = new JLabel("Rol: ");
		lblNewLabel_2_3_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_3_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_3_2.setBounds(35, 222, 139, 33);
		Empleados.add(lblNewLabel_2_3_2);

		JLabel lblNewLabel_2_1_1_2 = new JLabel("Dirección: ");
		lblNewLabel_2_1_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_2.setBounds(382, 30, 139, 33);
		Empleados.add(lblNewLabel_2_1_1_2);

		JLabel lblNewLabel_2_1_1_3 = new JLabel("Teléfono: ");
		lblNewLabel_2_1_1_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_3.setBounds(382, 100, 139, 33);
		Empleados.add(lblNewLabel_2_1_1_3);

		JLabel lblNewLabel_2_1_1_4 = new JLabel("Correo: ");
		lblNewLabel_2_1_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_4.setBounds(382, 165, 139, 33);
		Empleados.add(lblNewLabel_2_1_1_4);

		JLabel lblNewLabel_2_1_1_5 = new JLabel("Contraseña: ");
		lblNewLabel_2_1_1_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_5.setBounds(382, 222, 139, 33);
		Empleados.add(lblNewLabel_2_1_1_5);

		txt_employee_id = new JTextField();
		txt_employee_id.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_employee_id.setColumns(10);
		txt_employee_id.setBounds(179, 37, 166, 20);
		Empleados.add(txt_employee_id);

		txt_employee_username = new JTextField();
		txt_employee_username.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_employee_username.setColumns(10);
		txt_employee_username.setBounds(179, 172, 166, 20);
		Empleados.add(txt_employee_username);

		txt_employee_address = new JTextField();
		txt_employee_address.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_employee_address.setColumns(10);
		txt_employee_address.setBounds(531, 37, 166, 20);
		Empleados.add(txt_employee_address);

		btn_employee_register = new JButton("Registrar");
		btn_employee_register.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_employee_register.setBounds(734, 36, 148, 23);
		Empleados.add(btn_employee_register);

		btn_employee_update = new JButton("Modificar");
		btn_employee_update.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_employee_update.setBounds(734, 106, 148, 23);
		Empleados.add(btn_employee_update);

		btn_employee_delete = new JButton("Eliminar");
		btn_employee_delete.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_employee_delete.setBounds(734, 170, 148, 23);
		Empleados.add(btn_employee_delete);

		btn_employee_cancel = new JButton("Cancelar");
		btn_employee_cancel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_employee_cancel.setBounds(734, 232, 148, 23);
		Empleados.add(btn_employee_cancel);

		txt_employee_telephone = new JTextField();
		txt_employee_telephone.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_employee_telephone.setColumns(10);
		txt_employee_telephone.setBounds(531, 107, 166, 20);
		Empleados.add(txt_employee_telephone);

		txt_employee_fullname = new JTextField();
		txt_employee_fullname.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_employee_fullname.setColumns(10);
		txt_employee_fullname.setBounds(179, 107, 166, 20);
		Empleados.add(txt_employee_fullname);

		txt_employee_email = new JTextField();
		txt_employee_email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_employee_email.setColumns(10);
		txt_employee_email.setBounds(531, 172, 166, 20);
		Empleados.add(txt_employee_email);

		txt_employee_password = new JPasswordField();
		txt_employee_password.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txt_employee_password.setBounds(531, 229, 166, 20);
		Empleados.add(txt_employee_password);

		cmb_employee_rol = new JComboBox();
		cmb_employee_rol.setModel(new DefaultComboBoxModel(new String[] { "ADMINISTRADOR", "AUXILIAR" }));
		cmb_employee_rol.setFont(new Font("Times New Roman", Font.BOLD, 16));
		cmb_employee_rol.setBounds(179, 228, 166, 22);
		Empleados.add(cmb_employee_rol);

		JLabel lblNewLabel_2_1_2_1 = new JLabel("BUSCAR");
		lblNewLabel_2_1_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_2_1.setBounds(37, 319, 139, 33);
		tab_employees.add(lblNewLabel_2_1_2_1);

		txt_employee_search = new JTextField();
		txt_employee_search.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_employee_search.setColumns(10);
		txt_employee_search.setBounds(203, 325, 166, 20);
		tab_employees.add(txt_employee_search);

		employees_table = new JTable();
		employees_table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null }, },
				new String[] { "Identificaci\u00F3n", "Nombre", "Nombre de usuario", "Direcci\u00F3n", "Tel\u00E9fono",
						"Correo", "Rol" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		employees_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		employees_table.setBounds(27, 357, 920, 136);
		tab_employees.add(employees_table);

	}

	private void fillSuppliersTab(JTabbedPane panel_tab_menu_options) {

		JPanel tab_suppliers = new JPanel();
		tab_suppliers.setBackground(tabPanelBackgourndColor);
		panel_tab_menu_options.addTab("Proveedores", null, tab_suppliers, null);
		tab_suppliers.setLayout(null);

		JPanel Proveedores = new JPanel();
		Proveedores.setLayout(null);
		Proveedores.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Proveedores", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Proveedores.setBounds(29, 11, 920, 292);
		tab_suppliers.add(Proveedores);

		JLabel lblNewLabel_2_5 = new JLabel("Nombre: ");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_5.setBounds(2, 21, 164, 33);
		Proveedores.add(lblNewLabel_2_5);

		txt_supplier_name = new JTextField();
		txt_supplier_name.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_supplier_name.setColumns(10);
		txt_supplier_name.setBounds(171, 28, 166, 20);
		Proveedores.add(txt_supplier_name);

		JLabel lblNewLabel_2_1_4 = new JLabel("Dirección: ");
		lblNewLabel_2_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_4.setBounds(2, 91, 164, 33);
		Proveedores.add(lblNewLabel_2_1_4);

		txt_supplier_address = new JTextField();
		txt_supplier_address.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_supplier_address.setColumns(10);
		txt_supplier_address.setBounds(171, 98, 166, 20);
		Proveedores.add(txt_supplier_address);

		JLabel lblNewLabel_2_2_3 = new JLabel("Teléfono: ");
		lblNewLabel_2_2_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_3.setBounds(2, 156, 164, 33);
		Proveedores.add(lblNewLabel_2_2_3);

		txt_supplier_telephone = new JTextField();
		txt_supplier_telephone.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_supplier_telephone.setColumns(10);
		txt_supplier_telephone.setBounds(171, 163, 166, 20);
		Proveedores.add(txt_supplier_telephone);

		JLabel lblNewLabel_2_3_2 = new JLabel("Correo: ");
		lblNewLabel_2_3_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_3_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_3_2.setBounds(27, 213, 139, 33);
		Proveedores.add(lblNewLabel_2_3_2);

		cmb_supplier_city = new JComboBox();
		cmb_supplier_city.setModel(new DefaultComboBoxModel(new String[] { "Buenos Aires", "Barcelona" }));
		cmb_supplier_city.setFont(new Font("Times New Roman", Font.BOLD, 16));
		cmb_supplier_city.setBounds(523, 96, 166, 22);
		Proveedores.add(cmb_supplier_city);

		JLabel lblNewLabel_2_1_1_2 = new JLabel("Descripción: ");
		lblNewLabel_2_1_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_2.setBounds(374, 21, 139, 33);
		Proveedores.add(lblNewLabel_2_1_1_2);

		txt_supplier_description = new JTextField();
		txt_supplier_description.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_supplier_description.setColumns(10);
		txt_supplier_description.setBounds(523, 28, 166, 20);
		Proveedores.add(txt_supplier_description);

		btn_supplier_register = new JButton("Registrar");
		btn_supplier_register.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_supplier_register.setBounds(726, 27, 148, 23);
		Proveedores.add(btn_supplier_register);

		btn_supplier_update = new JButton("Modificar");
		btn_supplier_update.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_supplier_update.setBounds(726, 97, 148, 23);
		Proveedores.add(btn_supplier_update);

		txt_supplier_email = new JTextField();
		txt_supplier_email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_supplier_email.setColumns(10);
		txt_supplier_email.setBounds(171, 219, 166, 20);
		Proveedores.add(txt_supplier_email);

		JLabel lblNewLabel_2_1_1_3 = new JLabel("Ciudad: ");
		lblNewLabel_2_1_1_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_3.setBounds(374, 91, 139, 33);
		Proveedores.add(lblNewLabel_2_1_1_3);

		JLabel lblNewLabel_2_1_1_4 = new JLabel("Id: ");
		lblNewLabel_2_1_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_4.setBounds(374, 156, 139, 33);
		Proveedores.add(lblNewLabel_2_1_1_4);

		txt_supplier_id = new JTextField();
		txt_supplier_id.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_supplier_id.setColumns(10);
		txt_supplier_id.setEnabled(false);
		txt_supplier_id.setBounds(523, 163, 166, 20);
		Proveedores.add(txt_supplier_id);

		btn_supplier_delete = new JButton("Eliminar");
		btn_supplier_delete.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_supplier_delete.setBounds(726, 161, 148, 23);
		Proveedores.add(btn_supplier_delete);

		btn_supplier_cancel = new JButton("Cancelar");
		btn_supplier_cancel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_supplier_cancel.setBounds(726, 223, 148, 23);
		Proveedores.add(btn_supplier_cancel);

		suppliers_table = new JTable();
		suppliers_table.setModel(
				new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null }, }, new String[] {
						"Id", "Nombre", "Descripci\u00F3n", "Direcci\u00F3n", "Tel\u00E9fono", "Correo", "Ciudad" }) {
					boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
		suppliers_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		suppliers_table.setBounds(27, 357, 920, 136);
		tab_suppliers.add(suppliers_table);

		JLabel lblNewLabel_2_1_2_1 = new JLabel("BUSCAR");
		lblNewLabel_2_1_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_2_1.setBounds(39, 314, 139, 33);
		tab_suppliers.add(lblNewLabel_2_1_2_1);

		txt_suppliers_search = new JTextField();
		txt_suppliers_search.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_suppliers_search.setColumns(10);
		txt_suppliers_search.setBounds(205, 320, 166, 20);
		tab_suppliers.add(txt_suppliers_search);

	}

	private void fillCategoriesTab(JTabbedPane panel_tab_menu_options) {

		JPanel tab_category = new JPanel();
		tab_category.setBackground(tabPanelBackgourndColor);
		panel_tab_menu_options.addTab("Categorías", null, tab_category, null);
		tab_category.setLayout(null);

		JPanel Categorias = new JPanel();
		Categorias.setLayout(null);
		Categorias.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Categorías", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Categorias.setBounds(29, 11, 460, 305);
		tab_category.add(Categorias);

		JLabel lblNewLabel_2_5 = new JLabel("Id: ");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_5.setBounds(10, 30, 85, 33);
		Categorias.add(lblNewLabel_2_5);

		JLabel lblNewLabel_2_1_4 = new JLabel("Nombre: ");
		lblNewLabel_2_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_4.setBounds(10, 100, 85, 33);
		Categorias.add(lblNewLabel_2_1_4);

		txt_category_id = new JTextField();
		txt_category_id.setEditable(false);
		txt_category_id.setEnabled(false);
		txt_category_id.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_category_id.setColumns(10);
		txt_category_id.setBounds(105, 36, 166, 20);
		Categorias.add(txt_category_id);

		txt_category_name = new JTextField();
		txt_category_name.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_category_name.setColumns(10);
		txt_category_name.setBounds(105, 106, 166, 20);
		Categorias.add(txt_category_name);

		btn_category_register = new JButton("Registrar");
		btn_category_register.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_category_register.setBounds(281, 33, 148, 23);
		Categorias.add(btn_category_register);

		btn_category_update = new JButton("Modificar");
		btn_category_update.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_category_update.setBounds(281, 103, 148, 23);
		Categorias.add(btn_category_update);

		btn_category_delete = new JButton("Eliminar");
		btn_category_delete.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_category_delete.setBounds(281, 167, 148, 23);
		Categorias.add(btn_category_delete);

		btn_category_cancel = new JButton("Cancelar");
		btn_category_cancel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_category_cancel.setBounds(281, 234, 148, 23);
		Categorias.add(btn_category_cancel);

		JLabel lblNewLabel_2_1_2 = new JLabel("BUSCAR");
		lblNewLabel_2_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_2.setBounds(518, 11, 139, 33);
		tab_category.add(lblNewLabel_2_1_2);

		txt_category_search = new JTextField();
		txt_category_search.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_category_search.setColumns(10);
		txt_category_search.setBounds(697, 18, 279, 20);
		tab_category.add(txt_category_search);

		categories_table = new JTable();
		categories_table
				.setModel(new DefaultTableModel(new Object[][] { { null, "" }, }, new String[] { "Id", "Nombre" }));
		categories_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		categories_table.setBounds(518, 66, 458, 250);
		tab_category.add(categories_table);

	}

	private void fillReportsTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_reports = new JPanel();
		tab_reports.setBackground(tabPanelBackgourndColor);

		panel_tab_menu_options.addTab("Reportes", null, tab_reports, null);
		tab_reports.setLayout(null);

		sales_report_table = new JTable();
		sales_report_table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null }, },
				new String[] { "Factura", "Cliente", "Empleado", "Total", "Fecha de venta" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, true, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		sales_report_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		sales_report_table.setBounds(105, 42, 788, 166);
		tab_reports.add(sales_report_table);

		JLabel lblNewLabel_2_1_2 = new JLabel("VENTAS REALIZADAS");
		lblNewLabel_2_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_2.setBounds(171, 11, 667, 33);
		tab_reports.add(lblNewLabel_2_1_2);

		JLabel lblNewLabel_2_1_2_1 = new JLabel("COMPRAS REALIZADAS");
		lblNewLabel_2_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1_2_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_2_1.setBounds(171, 280, 667, 33);
		tab_reports.add(lblNewLabel_2_1_2_1);

		purchase_report_table = new JTable();
		purchase_report_table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
				new String[] { "Factura de compra ", "Proveedor", "Total", "Fecha de venta" }));
		purchase_report_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		purchase_report_table.setBounds(105, 311, 788, 166);
		tab_reports.add(purchase_report_table);

	}

	private void fillSettingsTab(JTabbedPane panel_tab_menu_options) {
		JPanel tab_settings = new JPanel();
		tab_settings.setBackground(tabPanelBackgourndColor);

		panel_tab_menu_options.addTab("Perfil", null, tab_settings, null);
		tab_settings.setLayout(null);

		JPanel Empleados = new JPanel();
		Empleados.setLayout(null);
		Empleados.setBorder(new TitledBorder(

				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Empleados",

				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Empleados.setBounds(23, 23, 920, 380);
		tab_settings.add(Empleados);

		JLabel lblNewLabel_2_5 = new JLabel("Identificación: ");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_5.setBounds(10, 30, 164, 33);
		Empleados.add(lblNewLabel_2_5);

		JLabel lblNewLabel_2_1_4 = new JLabel("Nombre completo: ");
		lblNewLabel_2_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_4.setBounds(10, 100, 164, 33);
		Empleados.add(lblNewLabel_2_1_4);

		JLabel lblNewLabel_2_2_3 = new JLabel("Dirección: ");
		lblNewLabel_2_2_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_2_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_2_3.setBounds(10, 165, 164, 33);
		Empleados.add(lblNewLabel_2_2_3);

		JLabel lblNewLabel_2_3_2 = new JLabel("Teléfono: ");
		lblNewLabel_2_3_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_3_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_3_2.setBounds(35, 222, 139, 33);
		Empleados.add(lblNewLabel_2_3_2);

		JLabel lblNewLabel_2_1_1_2 = new JLabel("Nueva contraseña: ");
		lblNewLabel_2_1_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_2.setBounds(382, 30, 139, 33);
		Empleados.add(lblNewLabel_2_1_1_2);

		JLabel lblNewLabel_2_1_1_4 = new JLabel("Correo: ");
		lblNewLabel_2_1_1_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_4.setBounds(35, 293, 139, 33);
		Empleados.add(lblNewLabel_2_1_1_4);

		JLabel lblNewLabel_2_1_1_5 = new JLabel("Repita contraseña: ");
		lblNewLabel_2_1_1_5.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2_1_1_5.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblNewLabel_2_1_1_5.setBounds(382, 100, 139, 33);
		Empleados.add(lblNewLabel_2_1_1_5);

		txt_profile_id = new JTextField();
		txt_profile_id.setEditable(false);
		txt_profile_id.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_profile_id.setColumns(10);
		txt_profile_id.setBounds(179, 37, 166, 20);
		Empleados.add(txt_profile_id);

		txt_profile_address = new JTextField();
		txt_profile_address.setEditable(false);
		txt_profile_address.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_profile_address.setColumns(10);
		txt_profile_address.setBounds(179, 172, 166, 20);
		Empleados.add(txt_profile_address);

		txt_profile_telephone = new JTextField();
		txt_profile_telephone.setEditable(false);
		txt_profile_telephone.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_profile_telephone.setColumns(10);
		txt_profile_telephone.setBounds(179, 229, 166, 20);
		Empleados.add(txt_profile_telephone);

		btn_profile_modify_data = new JButton("Modificar");
		btn_profile_modify_data.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_profile_modify_data.setBounds(717, 298, 148, 23);
		Empleados.add(btn_profile_modify_data);

		txt_profile_email = new JTextField();
		txt_profile_email.setEditable(false);
		txt_profile_email.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_profile_email.setColumns(10);
		txt_profile_email.setBounds(179, 299, 166, 20);
		Empleados.add(txt_profile_email);

		txt_profile_fullname = new JTextField();
		txt_profile_fullname.setEditable(false);
		txt_profile_fullname.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_profile_fullname.setColumns(10);
		txt_profile_fullname.setBounds(179, 107, 166, 20);
		Empleados.add(txt_profile_fullname);

		txt_profile_password_modify = new JPasswordField();
		txt_profile_password_modify.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txt_profile_password_modify.setBounds(531, 36, 166, 20);
		Empleados.add(txt_profile_password_modify);

		txt_profile_modify_confirm = new JPasswordField();
		txt_profile_modify_confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
		txt_profile_modify_confirm.setBounds(531, 107, 166, 20);
		Empleados.add(txt_profile_modify_confirm);
	}

	public void setVisible(boolean b) {
		frame.setVisible(true);
	}

	public void dispose() {
		frame.dispose();
	}
}