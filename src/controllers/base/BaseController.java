package controllers.base;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import exceptions.BusinessException;
import services.base.BaseService;
import views.AbstractSystemView;

//Base controller con funcionalidad común
public abstract class BaseController implements ActionListener, MouseListener, KeyListener {
	protected AbstractSystemView views;
	protected DefaultTableModel model;

	protected BaseController(AbstractSystemView views) {
		this.views = views;
		this.model = new DefaultTableModel();
		initializeListeners();
	}

	// Template method para inicializar listeners específicos
	protected abstract void initializeListeners();

	// Template method para limpiar campos específicos
	protected abstract void cleanFields();

	// Método común para limpiar tabla
	protected void cleanTable() {
		for (int i = 0; i < model.getRowCount(); i++) {
			model.removeRow(i);
			i = i - 1;
		}
	}

	// Método común para mostrar mensajes
	protected void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	// Método común para confirmaciones
	protected boolean confirmAction(String message) {
		return JOptionPane.showConfirmDialog(null, message) == 0;
	}

	// Método común para validar campos vacíos
	protected boolean validateRequiredFields(String... fields) {
		for (String field : fields) {
			if (field == null || field.trim().isEmpty()) {
				showMessage("Todos los campos son obligatorios");
				return false;
			}
		}
		return true;
	}

	protected boolean validateRequiredRowCells(String... fields) {
		for (String field : fields) {
			if (field == null || field.trim().isEmpty()) {
				showMessage("Todos los campos de las celdas son obligatorios");
				return false;
			}
		}
		return true;
	}

	protected void showValidationError(String message) {
		JOptionPane.showMessageDialog(views, message, "Error de validación", JOptionPane.WARNING_MESSAGE);
	}

	protected void showError(String title, String message) {
		JOptionPane.showMessageDialog(views, message, title, JOptionPane.ERROR_MESSAGE);
	}

	protected void showSuccess(String message) {
		JOptionPane.showMessageDialog(views, message, "Éxito", JOptionPane.INFORMATION_MESSAGE);
	}

	protected void logError(BusinessException e) {
		// TODO esto deberia ser un metodo estatico?

	}

	protected List<?> listAllElements(BaseService service, String text) throws BusinessException {
		return service.listAllElements(text);
	}

	// Implementaciones por defecto de MouseListener
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

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}