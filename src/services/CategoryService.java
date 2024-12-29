package services;

import java.util.List;
import java.util.Locale.Category;

import exceptions.BusinessException;
import exceptions.ValidationException;
import models.Customers;
import services.base.BaseService;

public class CategoryService extends BaseService {

	private final CategoryService categoryDao;

	public CategoryService(CategoryService categoryDao) {
		this.categoryDao = categoryDao;
	}

	public void registerCategory(Category category) throws BusinessException {
//		validateCategory(category);
//		// Verificar si ya existe un cliente con el mismo ID
//		if (categoryDao.exists(category.getId())) {
//			throw new BusinessException("Ya existe un cliente con el ID " + category.getId(),
//					ErrorCode.DUPLICATE_ENTITY);
//		}
//		if (!categoryDao.registerCustomerQuery(category)) {
//			throw new BusinessException("Error al registrar el cliente en la base de datos", ErrorCode.DATABASE_ERROR);
//		}
	}

	public void updateCategory(Customers customer) throws BusinessException {
//		validateCategory(customer);
//		if (!categoryDao.updateCustomerQuery(customer)) {
//			throw new BusinessException("Ha ocurrido un error al modificar los datos del cliente",
//					ErrorCode.DATABASE_ERROR);
//		}
	}

	public void deleteCategory(int id) throws BusinessException {
//		if (!categoryDao.deleteCustomerQuery(id)) {
//			throw new BusinessException("Ha ocurrido un error al eliminar al cliente", ErrorCode.DATABASE_ERROR);
//		}
	}

	private void validateCategory(Customers customer) throws ValidationException {
//		List<String> errors = new ArrayList<>();
//		if (customer == null) {
//			throw new ValidationException("El cliente no puede ser nulo");
//		}
//		if (customer.getFull_name() == null || customer.getFull_name().trim().isEmpty()) {
//			errors.add("El nombre completo es requerido");
//		}
//		if (customer.getEmail() != null && !isValidEmail(customer.getEmail())) {
//			errors.add("El formato del email no es válido");
//		}
//		if (!isValidPhone(customer.getTelephone())) {
//			errors.add("El formato del teléfono no es válido");
//		}
//		if (!errors.isEmpty()) {
//			throw new ValidationException(String.join(", ", errors));
//		}

	}

	private boolean isValidEmail(String email) {
		// TODO Implementar validación de email. Podria ir en el baseService
		return email != null && email.contains("@");
	}

	private boolean isValidPhone(String phone) {
		// TODO Implementar validación de teléfono. Podria ir en el baseService
		return phone != null && phone.matches("\\d+");
	}

	@Override
	public List<?> listAllElements(String text) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
