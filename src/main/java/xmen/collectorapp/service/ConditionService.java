package xmen.collectorapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.dao.ConditionDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Condition;
import xmen.collectorapp.util.Errors;
import xmen.collectorapp.util.Validator;

@Service("conditionService")
public class ConditionService {

	@Autowired
	private ConditionDAO conditionDAO;

	public Response findAll() {
		Response response = new Response();
		findAllConditions(response);
		return response;
	}
	
	
	public Map<String, ArrayList<String> > ValidateFields(Condition condition ) {
		
		Map<String, ArrayList<String> > errors = new HashMap<String, ArrayList<String> >();

		Validator.validateString(errors, "name", condition.getName(), 50, java.util.EnumSet.of(Validator.StringValidationOptions.NOT_EMPTY, Validator.StringValidationOptions.LOWER_CASE_ONLY, Validator.StringValidationOptions.LETTERS_ONLY )) ;

		return errors;
	}
	
	public Response create(Condition condition) {
		Response response = new Response();
	
		Map<String, ArrayList<String> > errors = ValidateFields(condition);
		if (errors.size() > 0)
		{
			response.setResponseObject(errors);
			response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
			return response;
		}
		
		persist(condition, response);
		
		return response;
	}

	
	@Transactional
	private void persist(Condition condition, Response response) {
		try {
			conditionDAO.save(condition);
			response.setResponseObject(condition);
			response.setStatusCode(HttpStatus.CREATED);
		} 
		catch (PersistenceException e) {
			ArrayList<String> responseErrors = new ArrayList<String>();
			responseErrors.add(Errors.convertDatabaseExceptionToERRMessage(e) );
			
			response.setResponseObject(responseErrors);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Transactional
	private void findAllConditions(Response response) {
		try {
			response.setResponseObject(conditionDAO.findAllConditions());
			response.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			response.setStatusCode(HttpStatus.NOT_FOUND);
		}
	}

}
