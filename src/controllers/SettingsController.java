package controllers;

import static dao.EmployeesDao.address_user;
import static dao.EmployeesDao.email_user;
import static dao.EmployeesDao.full_name_user;
import static dao.EmployeesDao.id_user;
import static dao.EmployeesDao.telephone_user;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import views.base.AbstractSystemView;

public class SettingsController implements MouseListener {
	private AbstractSystemView views;

	public SettingsController(AbstractSystemView view) {
		this.views = view;
		this.views.jlabel_products.addMouseListener(this);
		this.views.jlabel_purchases.addMouseListener(this);
		this.views.jlabel_customers.addMouseListener(this);
		this.views.jlabel_employees.addMouseListener(this);
		this.views.jlabel_suppliers.addMouseListener(this);
		this.views.jlabel_categories.addMouseListener(this);
		this.views.jlabel_reports.addMouseListener(this);
		this.views.jlabel_settings.addMouseListener(this);
		Profile();
	}

	// Asignar el perfil del usuario
	public void Profile() {
		this.views.txt_profile_id.setText("" + id_user);
		this.views.txt_profile_fullname.setText(full_name_user);
		this.views.txt_profile_address.setText(address_user);
		this.views.txt_profile_telephone.setText(telephone_user);
		this.views.txt_profile_email.setText(email_user);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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

}
