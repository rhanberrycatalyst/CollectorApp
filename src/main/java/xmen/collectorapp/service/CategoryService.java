package xmen.collectorapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.dao.CategoryDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Category;
import xmen.collectorapp.util.Errors;
import xmen.collectorapp.util.Validator;

@Service("categoryService")
public class CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public Response findAllCategory() {
		Response response = new Response();
		findAllCategories(response);
		return response;
	}

public Map<String, ArrayList<String> > ValidateFields(Category category) {
		
		Map<String, ArrayList<String> > errors = new HashMap<String, ArrayList<String> >();
		Validator.validateString(errors, "name", category.getName(), 50, java.util.EnumSet.of(Validator.StringValidationOptions.NOT_EMPTY,Validator.StringValidationOptions.LOWER_CASE_ONLY)) ;
		return errors;
	}

	
	@Transactional
	private void findAllCategories(Response response) {
		try {
			response.setResponseObject(categoryDAO.findAllCategory());
			response.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			response.setStatusCode(HttpStatus.NOT_FOUND);
		}
	}
	
	
	public Response create(Category category) {
		Response response = new Response();
		persist(category, response);
		return response;
	}
	
	
	@Transactional
	private void persist(Category category, Response response) {
		try {
			
			Map<String, ArrayList<String> > errors = ValidateFields(category);
			if (errors.size() >0)
			{
				response.setResponseObject(errors);
				response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
				return;
			}
			
			categoryDAO.save(category);
			response.setResponseObject(category);
			response.setStatusCode(HttpStatus.CREATED);
		} catch (PersistenceException e) {
			ArrayList<String> responseErrors = new ArrayList<String>();
			responseErrors.add(Errors.convertDatabaseExceptionToERRMessage(e) );
			
			response.setResponseObject(responseErrors);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	
}
