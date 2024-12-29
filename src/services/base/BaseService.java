package services.base;

import java.util.List;

import exceptions.BusinessException;

public abstract class BaseService {

	public abstract List<?> listAllElements(String text) throws BusinessException;

}
