/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import models.Purchases;
import models.PurchasesDao;

public class Print {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
	}

	private JButton btn_print_purchase;
	private JPanel form_print;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JScrollPane jScrollPane1;
	private JTable purchase_details_table;
	private JTextField txt_invoice;
	private JTextField txt_total;

	public JFrame frame;

	Purchases purchase = new Purchases();
	PurchasesDao purchaseDao = new PurchasesDao();
	DefaultTableModel model = new DefaultTableModel();

	private Color menuPanelBackgourndColor;

	private Color tabPanelBackgourndColor;

	/**
	 * Creates new form Print
	 */
	public Print(int id) {
		initialize();
		frame.setSize(620, 630);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.repaint();

		frame.setTitle("Factura de compra");

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		txt_invoice.setText("" + id);

		listAllPurchasesDetails(id);
		calculatePurchase();
	}

	public void listAllPurchasesDetails(int id) {
		List<Purchases> list = purchaseDao.listPurchaseDetailQuery(id);
		model = (DefaultTableModel) purchase_details_table.getModel();
		Object[] row = new Object[7];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getProduct_name();
			row[1] = list.get(i).getPurchase_amount();
			row[2] = list.get(i).getPurchase_price();
			row[3] = list.get(i).getPurchase_subtotal();
			row[4] = list.get(i).getSupplier_name_product();
			row[5] = list.get(i).getPurchaser();
			row[6] = list.get(i).getCreated();
			model.addRow(row);
		}
		purchase_details_table.setModel(model);
	}

	// Calcular el total
	public void calculatePurchase() {
		double total = 0.00;
		int numRow = purchase_details_table.getRowCount();
		for (int i = 0; i < numRow; i++) {
			total = total + Double.parseDouble(String.valueOf(purchase_details_table.getValueAt(i, 3)));
		}
		txt_total.setText("" + total);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 620, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Colores
		menuPanelBackgourndColor = new Color(18, 45, 61);
		tabPanelBackgourndColor = new Color(69, 193, 241);

		form_print = new JPanel();
		form_print.setBounds(0, 0, 620, 520);
		form_print.setBackground(new java.awt.Color(152, 202, 63));
		form_print.setLayout(null);

		jPanel2 = new JPanel();
		jLabel1 = new JLabel();
		jPanel1 = new JPanel();
		jLabel2 = new JLabel();
		txt_invoice = new JTextField();
		jLabel3 = new JLabel();
		jScrollPane1 = new JScrollPane();
		purchase_details_table = new JTable();
		jLabel4 = new JLabel();
		txt_total = new JTextField();
		btn_print_purchase = new JButton();
		btn_print_purchase.setBounds(0, 0, 0, 0);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		jPanel2.setLayout(null);

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		jLabel1.setIcon(new ImageIcon(getClass().getResource("/images/farmacia.png"))); // NOI18N
		jLabel1.setBounds(0, 0, 100, 70);
		jPanel2.add(jLabel1);
		jPanel2.setBounds(0, 0, 100, 70);
		form_print.add(jPanel2);

		jPanel1.setBackground(new java.awt.Color(18, 45, 61));
		jPanel1.setLayout(null);

		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("VIDA NATURAL");
		jLabel2.setBounds(210, 20, -1, -1);
		jPanel1.add(jLabel2);

		txt_invoice.setEditable(false);
		txt_invoice.setBounds(500, 20, 110, -1);
		jPanel1.add(txt_invoice);
		jPanel1.setBounds(0, 0, 620, 70);
		form_print.add(jPanel1);

		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		jLabel3.setForeground(new java.awt.Color(255, 255, 255));
		jLabel3.setText("DETALLES DE LA COMPRA:");
		jLabel3.setBounds(10, 130, -1, -1);
		form_print.add(jLabel3);

		purchase_details_table = new JTable();
		purchase_details_table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null }, },
				new String[] { "Producto", "Cantidad", "Precio", "Subtotal", "Proveedor", "Comprador por", "Fecha" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		purchase_details_table.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		purchase_details_table.setBounds(29, 352, 920, 136);
		jScrollPane1.setViewportView(purchase_details_table);
		if (purchase_details_table.getColumnModel().getColumnCount() > 0) {
			purchase_details_table.getColumnModel().getColumn(0).setMinWidth(100);
			purchase_details_table.getColumnModel().getColumn(5).setMinWidth(110);
			purchase_details_table.getColumnModel().getColumn(6).setMinWidth(80);
		}
//		getContentPane().setLayout(null);
		jScrollPane1.setBounds(0, 170, 620, 250);
		form_print.add(jScrollPane1);

		jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		jLabel4.setForeground(new java.awt.Color(255, 255, 255));
		jLabel4.setText("Total:");
		jLabel4.setBounds(410, 450, -1, -1);
		form_print.add(jLabel4);
		txt_total.setBounds(470, 450, 140, 30);
		form_print.add(txt_total);

		frame.getContentPane().add(form_print);

		btn_print_purchase.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
		btn_print_purchase.setText("IMPRIMIR");
		btn_print_purchase.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_print_purchaseActionPerformed(evt);
			}
		});
//		getContentPane().add(btn_print_purchase);
		frame.getContentPane().add(btn_print_purchase);
//		pack(); //TODO 
	}
//
//	private void btn_print_purchaseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_print_purchaseActionPerformed
//		Toolkit tk = form_print.getToolkit();
//		PrintJob pj = tk.getPrintJob(this, null, null);
//		Graphics graphics = pj.getGraphics();		
//		form_print.print(graphics);
//		graphics.dispose();
//		pj.end();
//	}

	private void btn_print_purchaseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_print_purchaseActionPerformed
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setJobName("Impresión de Compra");

		// Configurar el componente a imprimir
		printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
			if (pageIndex > 0) {
				return Printable.NO_SUCH_PAGE;
			}

			// Renderiza el formulario en la página de impresión
			form_print.printAll(graphics);
			return Printable.PAGE_EXISTS;
		});

		// Mostrar el cuadro de diálogo de impresión
		if (printerJob.printDialog()) {
			try {
				printerJob.print();
			} catch (PrinterException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al imprimir: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);

			}
		} else {
			JOptionPane.showMessageDialog(null, "Impresión cancelada", "Información", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void dispose() {
		frame.dispose();
	}

	public void setVisible(boolean b) {
		frame.setVisible(true);
	}
}
