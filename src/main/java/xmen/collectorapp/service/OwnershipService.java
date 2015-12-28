package xmen.collectorapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.dao.OwnershipDAO;
import xmen.collectorapp.dto.Response;
import xmen.collectorapp.entity.Ownership;
import xmen.collectorapp.util.Errors;
import xmen.collectorapp.util.Validator;

@Service("ownershipService")
public class OwnershipService {

	@Autowired
	private OwnershipDAO ownershipDAO;

	public Response findAll() {
		Response response = new Response();
		findAllOwnerships(response);
		return response;
	}
	
	public Map<String, ArrayList<String> > ValidateFields(Ownership ownership ) {
		
		Map<String, ArrayList<String> > errors = new HashMap<String, ArrayList<String> >();

		Validator.validateString(errors, "status", ownership.getStatus(), 50, java.util.EnumSet.of(Validator.StringValidationOptions.NOT_EMPTY )) ;
		
		return errors;
	}
	
	public Response create(Ownership ownership) {
		Response response = new Response();
	
		Map<String, ArrayList<String> > errors = ValidateFields(ownership);
		if (errors.size() > 0)
		{
			response.setResponseObject(errors);
			response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
			return response;
		}
		
		persist(ownership, response);
		
		return response;
	}

	
	@Transactional
	private void persist(Ownership ownership, Response response) {
		try {
			ownershipDAO.save(ownership);
			response.setResponseObject(ownership);
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
	private void findAllOwnerships(Response response) {
		try {
			response.setResponseObject(ownershipDAO.findAll());
			response.setStatusCode(HttpStatus.OK);
		} catch (Exception e) {
			response.setStatusCode(HttpStatus.NOT_FOUND);
		}
	}
}
