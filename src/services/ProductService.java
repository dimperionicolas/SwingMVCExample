package services;

import java.util.ArrayList;
import java.util.List;

import dao.ProductsDao;
import exceptions.BusinessException;
import exceptions.ErrorCode;
import exceptions.ValidationException;
import models.Products;
import services.base.BaseService;

public class ProductService extends BaseService {

	private final ProductsDao productDao;
	private static volatile ProductService instance;

	private ProductService() {
		this.productDao = new ProductsDao();
	}

	public static ProductService getInstance() {
		if (instance == null) {
			synchronized (ProductService.class) {
				if (instance == null) {
					instance = new ProductService();
				}
			}
		}
		return instance;
	}

	public void registerProduct(Products product) throws BusinessException {
		validateProduct(product);
		if (productDao.exists(product.getId())) {
			throw new BusinessException("Ya existe un producto con el ID " + product.getId(),
					ErrorCode.DUPLICATE_ENTITY);
		}
		if (!productDao.registerProductQuery(product)) {
			throw new BusinessException("Error al registrar el cliente en la base de datos", ErrorCode.DATABASE_ERROR);
		}
	}

	public void updateProduct(Products product) throws BusinessException {
		validateProduct(product);
		if (!productDao.updateProductQuery(product)) {
			throw new BusinessException("Ha ocurrido un error al modificar los datos del producto",
					ErrorCode.DATABASE_ERROR);
		}
	}

	public void deleteProduct(int id) throws BusinessException {
		if (!productDao.deleteProductQuery(id)) {
			throw new BusinessException("Ha ocurrido un error al eliminar el producto", ErrorCode.DATABASE_ERROR);
		}
	}

	@Override
	public List<?> listAllElements(String text) throws BusinessException {
		List<Products> list = productDao.listProductsQuery(text);
		if (list == null) {
			throw new BusinessException("Ha ocurrido un error al listar los productos", ErrorCode.DATABASE_ERROR);
		}
		return list;
	}

	public Products searchProduct(int id) throws BusinessException {
		Products product = productDao.searchProduct(id);
		if (product == null) {
			throw new BusinessException("Ha ocurrido un error al listar los productos", ErrorCode.DATABASE_ERROR);
		}
		return product;

	}

	private void validateProduct(Products product) throws ValidationException {
		List<String> errors = new ArrayList<>();
		if (product == null) {
			throw new ValidationException("El cliente no puede ser nulo");
		}
//		if (product.getFull_name() == null || product.getFull_name().trim().isEmpty()) {
//			errors.add("El nombre completo es requerido");
//		}
//		if (product.getEmail() != null && !isValidEmail(product.getEmail())) {
//			errors.add("El formato del email no es válido");
//		}
//		if (!isValidPhone(product.getTelephone())) {
//			errors.add("El formato del teléfono no es válido");
//		}
//		if (!errors.isEmpty()) {
//			throw new ValidationException(String.join(", ", errors));
//		}

	}

}
