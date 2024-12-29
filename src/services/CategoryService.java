package services;

import java.util.List;

import dao.CategoriesDao;
import exceptions.BusinessException;
import exceptions.ErrorCode;
import exceptions.ValidationException;
import models.Categories;
import services.base.BaseService;

public class CategoryService extends BaseService {

	private final CategoriesDao categoryDao;

	public CategoryService(CategoriesDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public void registerCategory(Categories category) throws BusinessException {
		validateCategory(category);
		if (categoryDao.exists(category.getId())) {
			throw new BusinessException("Ya existe una categoria con el ID " + category.getId(),
					ErrorCode.DUPLICATE_ENTITY);
		}
		if (!categoryDao.registerCategoryQuery(category)) {
			throw new BusinessException("Error al registrar el cliente en la base de datos", ErrorCode.DATABASE_ERROR);
		}
	}

	public void updateCategory(Categories category) throws BusinessException {
		validateCategory(category);
		if (!categoryDao.updateCategoryQuery(category)) {
			throw new BusinessException("Ha ocurrido un error al modificar los datos del cliente",
					ErrorCode.DATABASE_ERROR);
		}
	}

	public void deleteCategory(int id) throws BusinessException {
		if (!categoryDao.deleteCategoryQuery(id)) {
			throw new BusinessException("Ha ocurrido un error al eliminar al cliente", ErrorCode.DATABASE_ERROR);
		}
	}

	private void validateCategory(Categories category) throws ValidationException {
		if (category == null) {
			throw new ValidationException("La categoría no puede ser nula");
		}
		if (category.getName() == null || category.getName().trim().isEmpty()) {
			throw new ValidationException("El nombre completo es requerido");
		}
	}

	@Override
	public List<?> listAllElements(String text) throws BusinessException {
		List<Categories> list = categoryDao.listCategoriesQuery(text);
		if (list == null) {
			throw new BusinessException("Ha ocurrido un error al listar clientes", ErrorCode.DATABASE_ERROR);
		}
		return list;
	}

}
