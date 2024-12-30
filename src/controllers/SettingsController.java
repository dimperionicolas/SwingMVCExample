package controllers;

import static dao.EmployeesDao.address_user;
import static dao.EmployeesDao.email_user;
import static dao.EmployeesDao.full_name_user;
import static dao.EmployeesDao.id_user;
import static dao.EmployeesDao.telephone_user;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import controllers.base.BaseController;
import views.base.AbstractSystemView;

public class SettingsController extends BaseController {

	public SettingsController(AbstractSystemView view) {
		super(view);
		profile();
	}

	@Override
	protected void initializeListeners() {
		views.jlabel_products.addMouseListener(this);
		views.jlabel_purchases.addMouseListener(this);
		views.jlabel_customers.addMouseListener(this);
		views.jlabel_employees.addMouseListener(this);
		views.jlabel_suppliers.addMouseListener(this);
		views.jlabel_categories.addMouseListener(this);
		views.jlabel_reports.addMouseListener(this);
		views.jlabel_settings.addMouseListener(this);

	}

	// Asignar el perfil del usuario
	public void profile() {
		this.views.txt_profile_id.setText("" + id_user);
		this.views.txt_profile_fullname.setText(full_name_user);
		this.views.txt_profile_address.setText(address_user);
		this.views.txt_profile_telephone.setText(telephone_user);
		this.views.txt_profile_email.setText(email_user);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object componente = e.getSource();
		if (componente instanceof JLabel) {
			((JLabel) componente).getParent().setBackground(new Color(152, 202, 63));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object componente = e.getSource();
		if (componente instanceof JLabel) {
			((JLabel) componente).getParent().setBackground(new Color(18, 45, 61));

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		throw new UnsupportedOperationException("Sin funcionalidad.");
	}

	@Override
	protected void cleanFields() {
		throw new UnsupportedOperationException("Sin funcionalidad.");
	}

}
